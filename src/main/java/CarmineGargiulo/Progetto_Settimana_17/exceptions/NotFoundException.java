package CarmineGargiulo.Progetto_Settimana_17.exceptions;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String tipoRisorsa){
        super(tipoRisorsa + " non trovato/a");
    }
}
