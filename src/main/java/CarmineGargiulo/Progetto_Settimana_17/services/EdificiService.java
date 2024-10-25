package CarmineGargiulo.Progetto_Settimana_17.services;

import CarmineGargiulo.Progetto_Settimana_17.entities.Edificio;
import CarmineGargiulo.Progetto_Settimana_17.exceptions.ValidationException;
import CarmineGargiulo.Progetto_Settimana_17.repositories.EdificiRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class EdificiService {
    @Autowired
    private EdificiRepository edificiRepository;

    public void salvaEdificio(Edificio edificio){
        if(edificiRepository.existsByIndirizzoAndCitta(edificio.getIndirizzo(), edificio.getCitta())) throw new ValidationException("Impossibile salvare l'edificio - indirizzo già in uso");
        edificiRepository.save(edificio);
        log.info("Edificio " + edificio + " salvato con successo");
    }

    public void salvaMoltiEdifici(List<Edificio> edifici){
        for (Edificio utente : edifici) {
            try {
                this.salvaEdificio(utente);
            } catch (ValidationException e){
                log.error(e.getMessage());
            }
        }
    }

    public List<Edificio> trovaTutti(){
        return edificiRepository.findAll();
    }
}
