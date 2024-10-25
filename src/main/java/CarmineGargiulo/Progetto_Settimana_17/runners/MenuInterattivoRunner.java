package CarmineGargiulo.Progetto_Settimana_17.runners;

import CarmineGargiulo.Progetto_Settimana_17.entities.Postazione;
import CarmineGargiulo.Progetto_Settimana_17.entities.Prenotazione;
import CarmineGargiulo.Progetto_Settimana_17.entities.Utente;
import CarmineGargiulo.Progetto_Settimana_17.enums.TipoPostazione;
import CarmineGargiulo.Progetto_Settimana_17.exceptions.EmptyListException;
import CarmineGargiulo.Progetto_Settimana_17.exceptions.NotFoundException;
import CarmineGargiulo.Progetto_Settimana_17.exceptions.ValidationException;
import CarmineGargiulo.Progetto_Settimana_17.services.EdificiService;
import CarmineGargiulo.Progetto_Settimana_17.services.PostazioniService;
import CarmineGargiulo.Progetto_Settimana_17.services.PrenotazioniService;
import CarmineGargiulo.Progetto_Settimana_17.services.UtentiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@Component
@Order(2)
public class MenuInterattivoRunner implements CommandLineRunner {
    @Autowired
    private UtentiService utentiService;
    @Autowired
    private EdificiService edificiService;
    @Autowired
    private PostazioniService postazioniService;
    @Autowired
    private PrenotazioniService prenotazioniService;
    @Autowired
    private Scanner scanner;
    @Override
    public void run(String... args) throws Exception {
        System.out.println("Benvenuto nella nostra app di prenotazioni postazioni");
        outerloop:
        while(true) {
            System.out.println("Sei un utente registrato ?");
            System.out.println("1 - SI");
            System.out.println("2 - NO");
            int scelta;
            while (true) {
                scelta = verifyInput();
                if (scelta <= 0 || scelta > 2) System.out.println("Devi inserire 1 o 2");
                else break;
            }
            Utente utente = null;
            if(scelta == 1){
                System.out.println("Prego fornire username");
                String username = scanner.nextLine();
                try {
                    utente = utentiService.trovaUtentePerUsername(username);
                } catch (NotFoundException e){
                    System.out.println(e.getMessage());
                    System.out.println("Riprova");
                    continue outerloop;
                }
            }
            if(scelta == 2){
                System.out.println("Registrati");
                System.out.println("Inserisci nome e cognome");
                String nominativo = scanner.nextLine();
                System.out.println("Inserisci username");
                String username = scanner.nextLine();
                System.out.println("Inserisci email");
                String email = scanner.nextLine();
                try {
                    utentiService.salvaUtente(new Utente(username, nominativo, email));
                } catch (ValidationException e){
                    System.out.println(e.getMessage());
                    System.out.println("Riprova");
                    continue outerloop;
                }
                utente = utentiService.trovaUtentePerUsername(username);
            }
            System.out.println("Benvenuto " + utente.getNominativo());
            while (true) {
                System.out.println("Che cosa vuoi fare? ");
                System.out.println("1 - Prenota postazione");
                System.out.println("2 - Controlla prenotazioni effettuate");
                System.out.println("0 - Esci");
                int scelta2;
                while (true) {
                    scelta2 = verifyInput();
                    if (scelta2 < 0 || scelta2 > 2) System.out.println("Devi inserire un numero tra 0 e 2");
                    else break;
                }
                switch (scelta2){
                    case 0: {
                        System.out.println("A presto");
                        break outerloop;
                    }
                    case 1: {
                        prenotaPostazione(utente);
                        break;
                    }
                    case 2: {
                        mostraListaPrenotazioniEffettuate(utente);
                    }
                }
            }

        }

    }

    private void prenotaPostazione(Utente utente){
        System.out.println("Seleziona il tipo di postazione che vuoi prenotare");
        List<TipoPostazione> tipiPostazione = Arrays.stream(TipoPostazione.values()).toList();
        tipiPostazione.forEach(tipoPostazione -> System.out.println((tipiPostazione.indexOf(tipoPostazione) + 1) + " - " + tipoPostazione));
        int scelta;
        while (true) {
            scelta = verifyInput();
            if (scelta <= 0 || scelta > tipiPostazione.size()) System.out.println("Devi inserire un numero tra 1 e " + tipiPostazione.size());
            else break;
        }
        TipoPostazione tipoScelto = tipiPostazione.get(scelta - 1);
        System.out.println("Seleziona la città");
        String citta = scanner.nextLine();
        try {
            List<Postazione> postazioniList = postazioniService.trovaPerTipoECitta(tipoScelto, citta);
            System.out.println("Scegli dall'elenco delle postazioni");
            postazioniList.forEach(postazione -> System.out.println((postazioniList.indexOf(postazione) + 1) + " - " + postazione));
            int scelta2;
            while (true) {
                scelta2 = verifyInput();
                if (scelta2 <= 0 || scelta2 > postazioniList.size()) System.out.println("Devi inserire un numero tra 1 e " + tipiPostazione.size());
                else break;
            }
            Postazione postazioneScelta = postazioniList.get(scelta2 - 1);
            LocalDate date;
            System.out.println("Inserisci una data in cui vorresti prenotare la postazione scelta ");
            System.out.println("Formato yyyy-mm-dd");
            while (true) {
                try {
                    date = LocalDate.parse(scanner.nextLine());
                    break;
                } catch (RuntimeException e){
                    System.out.println("Formattazione data non valido");
                    System.out.println("Riprova");
                }
            }
            System.out.println("Sai quante persone saranno invitate all'evento ?");
            System.out.println("1 - SI");
            System.out.println("2 - NO");
            int scelta3;
            while (true) {
                scelta3 = verifyInput();
                if (scelta3 <= 0 || scelta3 > 2) System.out.println("Devi inserire 1 o 2");
                else break;
            }
            if (scelta3 == 1){
                System.out.println("Inserisci il numero di partecipanti ");
                int nrPartecipanti;
                while (true) {
                    nrPartecipanti = verifyInput();
                    if (nrPartecipanti <= 0 ) System.out.println("Devi inserire un numero maggiore di zero");
                    else break;
                }
                try {
                    Prenotazione prenotazione = new Prenotazione(date, postazioneScelta, utente);
                    prenotazione.setNrPartecipanti(nrPartecipanti);
                    prenotazioniService.salvaPrenotazione(prenotazione);
                } catch (ValidationException e){
                    System.out.println(e.getMessage());
                }

            } else {
                try {
                    prenotazioniService.salvaPrenotazione(new Prenotazione(date, postazioneScelta, utente));
                } catch (ValidationException e){
                    System.out.println(e.getMessage());
                }
            }
        } catch (EmptyListException e){
            System.out.println(e.getMessage());
            System.out.println("Riprova");
        }
    }

    private void mostraListaPrenotazioniEffettuate(Utente utente){
        try {
            prenotazioniService.cercaPerUtente(utente).forEach(System.out::println);
        } catch (EmptyListException e){
            System.out.println(e.getMessage());
        }
    }


    private int verifyInput() {
        int number;
        while (true) {
            String input = scanner.nextLine();
            try {
                number = Integer.parseInt(input);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Devi inserire un numero");
            }
        }
        return number;
    }
}
