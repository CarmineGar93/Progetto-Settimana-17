package CarmineGargiulo.Progetto_Settimana_17.repositories;

import CarmineGargiulo.Progetto_Settimana_17.entities.Postazione;
import CarmineGargiulo.Progetto_Settimana_17.enums.TipoPostazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostazioniRepository extends JpaRepository<Postazione, UUID> {
    @Query("SELECT p FROM Postazione p WHERE p.tipoPostazione = :tipo AND p.edificio.citta = :citta")
    List<Postazione> filtraPerTipoECitta(TipoPostazione tipo, String citta);
}
