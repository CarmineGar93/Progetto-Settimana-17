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
@Table(name = "utenti")
public class Utente {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID utente_id;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String nominativo;
    @Column(nullable = false)
    private String email;

    public Utente(String username, String nominativo, String email) {
        this.username = username;
        this.nominativo = nominativo;
        this.email = email;
    }

    @Override
    public String toString() {
        return "Utente = username: " + username  +
                ", nominativo: " + nominativo +
                ", email: " + email;
    }
}
