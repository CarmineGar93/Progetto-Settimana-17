package CarmineGargiulo.Progetto_Settimana_17.repositories;

import CarmineGargiulo.Progetto_Settimana_17.entities.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PrenotazioniRepository extends JpaRepository<Prenotazione, UUID> {
}
