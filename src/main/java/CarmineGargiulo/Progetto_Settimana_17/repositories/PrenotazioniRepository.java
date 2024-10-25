package CarmineGargiulo.Progetto_Settimana_17.repositories;

import CarmineGargiulo.Progetto_Settimana_17.entities.Postazione;
import CarmineGargiulo.Progetto_Settimana_17.entities.Prenotazione;
import CarmineGargiulo.Progetto_Settimana_17.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.UUID;

@Repository
public interface PrenotazioniRepository extends JpaRepository<Prenotazione, UUID> {
    boolean existsByPostazioneAndDataPrenotazione(Postazione postazione, LocalDate dataPrenotazione);
    boolean existsByUtenteAndDataPrenotazione(Utente utente, LocalDate dataPrenotazione);

}
