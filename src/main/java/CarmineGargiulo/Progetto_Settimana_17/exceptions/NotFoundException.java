package CarmineGargiulo.Progetto_Settimana_17.exceptions;

public class NotFoundException extends RuntimeException{
    public NotFoundException(long id){
        super("La risorsa con id " + id + " non è stata trovata");
    }
}
