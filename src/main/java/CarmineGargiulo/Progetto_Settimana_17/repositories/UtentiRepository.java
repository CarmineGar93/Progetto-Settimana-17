package CarmineGargiulo.Progetto_Settimana_17.repositories;

import CarmineGargiulo.Progetto_Settimana_17.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UtentiRepository extends JpaRepository<Utente, UUID> {
    boolean existsByEmailOrUsername(String email, String username);
    Utente findFirstByUsername(String username);
}
