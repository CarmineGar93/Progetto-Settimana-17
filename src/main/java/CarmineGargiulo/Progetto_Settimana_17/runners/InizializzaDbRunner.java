package CarmineGargiulo.Progetto_Settimana_17.runners;

import CarmineGargiulo.Progetto_Settimana_17.entities.Edificio;
import CarmineGargiulo.Progetto_Settimana_17.entities.Utente;
import CarmineGargiulo.Progetto_Settimana_17.exceptions.ValidationException;
import CarmineGargiulo.Progetto_Settimana_17.services.EdificiService;
import CarmineGargiulo.Progetto_Settimana_17.services.UtentiService;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

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
    private Faker faker;
    @Override
    public void run(String... args) throws Exception {
        if(utentiService.trovaTutti().isEmpty()){
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
        if(edificiService.trovaTutti().isEmpty()){
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


    }
}
