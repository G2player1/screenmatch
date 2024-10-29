package Enos.projetoSpring.screenmatch.Exceptions;

public class ResultNotFoundException extends RuntimeException {

    private final String message;

    public ResultNotFoundException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
