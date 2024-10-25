package CarmineGargiulo.Progetto_Settimana_17.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "prenotazioni")
public class Prenotazione {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID prenotazione_id;
    @Column(nullable = false, name = "data_prenotazione")
    private LocalDate dataPrenotazione;
    @Column(name = "nr_partecipanti")
    private int nrPartecipanti;
    @ManyToOne
    @JoinColumn(name = "postazione_id")
    private Postazione postazione;
    @ManyToOne
    @JoinColumn(name = "utente_id")
    @Setter(AccessLevel.NONE)
    private Utente utente;

    public Prenotazione(LocalDate dataPrenotazione, Postazione postazione, Utente utente) {
        this.dataPrenotazione = dataPrenotazione;
        this.postazione = postazione;
        this.utente = utente;
    }

    @Override
    public String toString() {
        return "Prenotazione numero " + prenotazione_id +
                ", data di prenotazione: " + dataPrenotazione +
                ", numero partecipanti: " + (nrPartecipanti != 0 ? nrPartecipanti : "N/A") +
                ", postazione: " + postazione +
                ", prenotazione effettuata da: " + utente;
    }
}
