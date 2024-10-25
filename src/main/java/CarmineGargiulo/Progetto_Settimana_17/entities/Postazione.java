package CarmineGargiulo.Progetto_Settimana_17.entities;

import CarmineGargiulo.Progetto_Settimana_17.enums.TipoPostazione;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "postazioni")
public class Postazione {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID postazione_id;
    private String descrizione;
    @Column(nullable = false, name = "tipo_postazione")
    @Enumerated(EnumType.STRING)
    private TipoPostazione tipoPostazione;
    @Column(nullable = false, name = "nr_max_occupanti")
    private int nrMaxOccupanti;
    @ManyToOne
    @Setter(AccessLevel.NONE)
    @JoinColumn(name = "edificio_id")
    private Edificio edificio;
    @OneToMany(mappedBy = "postazione")
    @Setter(AccessLevel.NONE)
    private List<Prenotazione> prenotazioniList;

    public Postazione(String descrizione, TipoPostazione tipoPostazione, int nrMaxOccupanti, Edificio edificio) {
        this.descrizione = descrizione;
        this.tipoPostazione = tipoPostazione;
        this.nrMaxOccupanti = nrMaxOccupanti;
        this.edificio = edificio;
    }

    @Override
    public String toString() {
        return "Postazione = edificio: " + edificio +
                ", descrizione: " + descrizione  +
                ", tipo di postazione: " + tipoPostazione +
                ", numero massimo di occupanti: " + nrMaxOccupanti;
    }
}
