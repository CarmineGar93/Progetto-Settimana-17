package CarmineGargiulo.Progetto_Settimana_17.repositories;

import CarmineGargiulo.Progetto_Settimana_17.entities.Postazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PostazioniRepository extends JpaRepository<Postazione, UUID> {
}
