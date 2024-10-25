package CarmineGargiulo.Progetto_Settimana_17.exceptions;

public class EmptyListException extends RuntimeException{
    public EmptyListException() {
        super("La ricerca non ha prodotto alcun risultato");
    }
}
