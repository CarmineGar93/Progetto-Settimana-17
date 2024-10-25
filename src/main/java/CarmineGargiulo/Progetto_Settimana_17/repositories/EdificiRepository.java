package CarmineGargiulo.Progetto_Settimana_17.repositories;

import CarmineGargiulo.Progetto_Settimana_17.entities.Edificio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EdificiRepository extends JpaRepository<Edificio, UUID> {
}
