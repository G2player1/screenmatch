package Enos.projetoSpring.screenmatch.Exceptions;

public class WrongEpisodeDataException extends RuntimeException {

    private final String message;

    public WrongEpisodeDataException(String message) {
        this.message = message;
    }

    public String getMessage(){
        return this.message;
    }
}
