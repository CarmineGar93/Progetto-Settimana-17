package CarmineGargiulo.Progetto_Settimana_17.runners;

import CarmineGargiulo.Progetto_Settimana_17.entities.Edificio;
import CarmineGargiulo.Progetto_Settimana_17.entities.Postazione;
import CarmineGargiulo.Progetto_Settimana_17.entities.Prenotazione;
import CarmineGargiulo.Progetto_Settimana_17.entities.Utente;
import CarmineGargiulo.Progetto_Settimana_17.enums.TipoPostazione;
import CarmineGargiulo.Progetto_Settimana_17.exceptions.EmptyListException;
import CarmineGargiulo.Progetto_Settimana_17.exceptions.ValidationException;
import CarmineGargiulo.Progetto_Settimana_17.repositories.PostazioniRepository;
import CarmineGargiulo.Progetto_Settimana_17.services.EdificiService;
import CarmineGargiulo.Progetto_Settimana_17.services.PostazioniService;
import CarmineGargiulo.Progetto_Settimana_17.services.PrenotazioniService;
import CarmineGargiulo.Progetto_Settimana_17.services.UtentiService;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class InizializzaDbRunner implements CommandLineRunner {
    @Autowired
    private UtentiService utentiService;
    @Autowired
    private EdificiService edificiService;
    @Autowired
    private PostazioniService postazioniService;
    @Autowired
    private PrenotazioniService prenotazioniService;
    @Autowired
    private Faker faker;
    @Override
    public void run(String... args) throws Exception {
        List<Utente> utentiList = utentiService.trovaTutti();
        if(utentiList.isEmpty()){
            List<Utente> utentiPerDb = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                String name = faker.name().firstName();
                String surname = faker.name().lastName();
                String nomeCognome = name.toLowerCase() + "." + surname.toLowerCase();
                String[] domini = new String[]{"@gmail.it", "@yahoo.it", "@outlook.it"};
                Utente utente = new Utente(nomeCognome, name + " " + surname, nomeCognome + domini[faker.random().nextInt(domini.length)]);
                utentiPerDb.add(utente);
            }
            try {
                utentiService.salvaMoltiUtenti(utentiPerDb);
            } catch (ValidationException e){
                log.error(e.getMessage());
            }

        }
        List<Edificio> edificiList = edificiService.trovaTutti();
        if(edificiList.isEmpty()){
            List<Edificio> edificiPerDb = new ArrayList<>();
            for (int i = 0; i < 30; i++) {
                String[] citta = new String[]{"Milano", "Roma", "Napoli", "Genova", "Torino", "Palermo", "Firenze"};
                Edificio edificio = new Edificio(faker.company().name(), faker.address().streetName(), citta[faker.random().nextInt(citta.length)]);
                edificiPerDb.add(edificio);
            }
            try {
                edificiService.salvaMoltiEdifici(edificiPerDb);
            } catch (ValidationException e){
                log.error(e.getMessage());
            }

        }
        List<Postazione> postazioniList = postazioniService.trovaTutti();
        if(postazioniList.isEmpty()){
            List<Postazione> postazioniPerDb = new ArrayList<>();
            edificiList.forEach(edificio -> {
                for (int i = 0; i < faker.random().nextInt(1, 4); i++) {
                    String description = faker.howIMetYourMother().quote();
                    if (description.length() > 100) description = description.substring(0,100) + "...";
                    TipoPostazione tipoPostazioneRandom = TipoPostazione.values()[faker.random().nextInt(TipoPostazione.values().length)];
                    Postazione postazione = new Postazione(description, tipoPostazioneRandom,
                            faker.random().nextInt(20, 500), edificio);
                    postazioniPerDb.add(postazione);
                }
            });
            postazioniService.salvaMoltePostazioni(postazioniPerDb);
        }
        List<Prenotazione> prenotazioniList = prenotazioniService.trovaTutte();
        if(prenotazioniList.isEmpty()){
            List<Prenotazione> prenotazioniPerDb = new ArrayList<>();
            utentiList.forEach(utente -> {
                for (int i = 0; i < 2; i++) {
                    Prenotazione prenotazione = new Prenotazione(LocalDate.now().plusDays(faker.random().nextInt(1, 200)),
                            postazioniList.get(faker.random().nextInt(postazioniList.size())), utente);
                    prenotazioniPerDb.add(prenotazione);
                }

            });
            try{
                prenotazioniService.salvaMoltePrenotazioni(prenotazioniPerDb);
            } catch (ValidationException e){
                log.error(e.getMessage());
            }
        }
        //Prova per vedere se mi genera un eccezione se provo a prenotare una postazione che ha già una prenotazione
        try {
            Prenotazione prenotazioneFromDb = prenotazioniList.get(3);
            Prenotazione prenotazione = new Prenotazione(prenotazioneFromDb.getDataPrenotazione(), prenotazioneFromDb.getPostazione(), utentiList.get(5));
            prenotazioniService.salvaPrenotazione(prenotazione);
        } catch (ValidationException e){
            log.error(e.getMessage());
        }
        //Prova per vedere se mi genera un eccezione se provo a prenotare con un utente che ha già una prenotazione per quella data
        try {
            Prenotazione prenotazioneFromDb = prenotazioniList.get(5);
            Prenotazione prenotazione = new Prenotazione(prenotazioneFromDb.getDataPrenotazione(), postazioniList.get(50), prenotazioneFromDb.getUtente());
            prenotazioniService.salvaPrenotazione(prenotazione);
        } catch (ValidationException e){
            log.error(e.getMessage());
        }
        //Filtro lista postazioni per tipo e città
        System.out.println("--------Lista openspace a Milano---------");
        try {
            postazioniService.trovaPerTipoECitta(TipoPostazione.OPENSPACE, "Milano").forEach(System.out::println);
        } catch (EmptyListException e){
            log.error(e.getMessage());
        }


    }
}
