package CarmineGargiulo.Progetto_Settimana_17.services;

import CarmineGargiulo.Progetto_Settimana_17.entities.Prenotazione;
import CarmineGargiulo.Progetto_Settimana_17.entities.Utente;
import CarmineGargiulo.Progetto_Settimana_17.exceptions.EmptyListException;
import CarmineGargiulo.Progetto_Settimana_17.exceptions.ValidationException;
import CarmineGargiulo.Progetto_Settimana_17.repositories.PrenotazioniRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class PrenotazioniService {
    @Autowired
    private PrenotazioniRepository prenotazioniRepository;

    public void salvaPrenotazione(Prenotazione prenotazione){
        if(prenotazioniRepository.existsByPostazioneAndDataPrenotazione(prenotazione.getPostazione(), prenotazione.getDataPrenotazione()))
            throw new ValidationException("Impossibile salvare la prenotazione - Postazione occupata per la data fornita");
        if(prenotazioniRepository.existsByUtenteAndDataPrenotazione(prenotazione.getUtente(), prenotazione.getDataPrenotazione()))
            throw new ValidationException("Impossibile salvare la prenotazione - Hai già una prenotazione per la data selezionata");
        if(prenotazione.getNrPartecipanti() > prenotazione.getPostazione().getNrMaxOccupanti())
            throw new ValidationException("Impossibile salvare la prenotazione - Numero di partecipanti maggiori della capienza della postazione scelta");
        if(prenotazione.getDataPrenotazione().isBefore(LocalDate.now()))
            throw new ValidationException("Impossibile salvare la prenotazione - La data deve essere superiore alla data di oggi");
        prenotazioniRepository.save(prenotazione);
        System.out.println("Prenotazione " + prenotazione + " effettuata con successo ");
    }

    public void salvaMoltePrenotazioni(List<Prenotazione> prenotazioni){
        for (Prenotazione prenotazione : prenotazioni) {
            try {
                this.salvaPrenotazione(prenotazione);
            } catch (ValidationException e){
                log.error(e.getMessage());
            }
        }
    }

    public List<Prenotazione> trovaTutte(){
        return prenotazioniRepository.findAll();
    }

    public List<Prenotazione> cercaPerUtente(Utente utente){
        List<Prenotazione> prenotazioniList = prenotazioniRepository.findByUtente(utente);
        if(prenotazioniList.isEmpty()) throw new EmptyListException();
        return prenotazioniList;
    }

    public void cancellaPrenotazione(Prenotazione prenotazione){
        if(prenotazione.getDataPrenotazione().isBefore(LocalDate.now()))
            throw new ValidationException("Impossibile cancellare una prenotazione già avvenuta");
        prenotazioniRepository.delete(prenotazione);
        System.out.println("Prenotazione " +  prenotazione + " cancellata con successo");
    }

}
