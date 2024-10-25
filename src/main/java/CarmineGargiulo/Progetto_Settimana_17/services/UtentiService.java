package CarmineGargiulo.Progetto_Settimana_17.services;

import CarmineGargiulo.Progetto_Settimana_17.entities.Utente;
import CarmineGargiulo.Progetto_Settimana_17.exceptions.ValidationException;
import CarmineGargiulo.Progetto_Settimana_17.repositories.UtentiRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UtentiService {
    @Autowired
    private UtentiRepository utentiRepository;

    public void salvaUtente(Utente utente){
        if(utentiRepository.existsByEmailOrNominativo(utente.getEmail(), utente.getNominativo())) throw new ValidationException("Username o email già in uso");
        if(utente.getNominativo().length() < 2) throw new ValidationException("Username non valido");
        utentiRepository.save(utente);
        log.info("Utente " + utente + " salvato con successo");
    }

    public void salvaMoltiUtenti(List<Utente> utenti){
        for (Utente utente : utenti) {
            try {
                this.salvaUtente(utente);
            } catch (ValidationException e){
                log.error(e.getMessage());
            }
        }
    }

    public List<Utente> trovaTutti(){
        return utentiRepository.findAll();
    }
}
