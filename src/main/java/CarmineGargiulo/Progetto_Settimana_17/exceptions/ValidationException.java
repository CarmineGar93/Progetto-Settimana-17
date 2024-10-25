package CarmineGargiulo.Progetto_Settimana_17.exceptions;

public class ValidationException extends RuntimeException{
    public ValidationException(String message){
        super(message);
    }
}
