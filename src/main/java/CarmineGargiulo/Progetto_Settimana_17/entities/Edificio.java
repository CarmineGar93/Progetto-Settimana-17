package CarmineGargiulo.Progetto_Settimana_17.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "edifici")
public class Edificio {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID edificio_id;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private String indirizzo;
    @Column(nullable = false)
    @Setter(AccessLevel.NONE)
    private String citta;

    public Edificio(String nome, String indirizzo, String citta) {
        this.nome = nome;
        this.indirizzo = indirizzo;
        this.citta = citta;
    }

    @Override
    public String toString() {
        return "Edificio "  + nome +
                ", indirizzo: " + indirizzo +
                ", " + citta;
    }
}
