package Enos.projetoSpring.screenmatch.Exceptions;

public class DontHaveEpisodesException extends RuntimeException {

    private final String message;

    public DontHaveEpisodesException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
