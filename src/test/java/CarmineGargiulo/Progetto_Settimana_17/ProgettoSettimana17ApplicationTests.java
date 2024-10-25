package CarmineGargiulo.Progetto_Settimana_17;

import CarmineGargiulo.Progetto_Settimana_17.entities.Postazione;
import CarmineGargiulo.Progetto_Settimana_17.entities.Prenotazione;
import CarmineGargiulo.Progetto_Settimana_17.entities.Utente;
import CarmineGargiulo.Progetto_Settimana_17.exceptions.ValidationException;
import CarmineGargiulo.Progetto_Settimana_17.services.PostazioniService;
import CarmineGargiulo.Progetto_Settimana_17.services.PrenotazioniService;
import CarmineGargiulo.Progetto_Settimana_17.services.UtentiService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

@SpringBootTest
class ProgettoSettimana17ApplicationTests {
	@Autowired
	private PrenotazioniService prenotazioniService;
	@Autowired
	private UtentiService utentiService;
	@Autowired
	private PostazioniService postazioniService;

	@Test
	void contextLoads() {
	}

	@Test
	void eccezionePrenotazionePostazioneOccupata(){
		List<Utente> utentiList = utentiService.trovaTutti();
		List<Prenotazione> prenotazioniList = prenotazioniService.trovaTutte();
		Prenotazione prenotazioneFromDb = prenotazioniList.get(3);
		Prenotazione prenotazione = new Prenotazione(prenotazioneFromDb.getDataPrenotazione(), prenotazioneFromDb.getPostazione(), utentiList.get(5));
		assertThrows(ValidationException.class, ()-> prenotazioniService.salvaPrenotazione(prenotazione));
	}

	@Test
	void eccezionePrenotazioneUtenteOccupato(){
		List<Postazione> postazioniList = postazioniService.trovaTutti();
		List<Prenotazione> prenotazioniList = prenotazioniService.trovaTutte();
		Prenotazione prenotazioneFromDb = prenotazioniList.get(5);
		Prenotazione prenotazione = new Prenotazione(prenotazioneFromDb.getDataPrenotazione(), postazioniList.get(50), prenotazioneFromDb.getUtente());
		assertThrows(ValidationException.class, ()-> prenotazioniService.salvaPrenotazione(prenotazione));
	}

}
