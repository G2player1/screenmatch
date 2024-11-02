package Enos.projetoSpring.screenmatch.Exceptions;

public class WrongTypeDataException extends RuntimeException {

    private final String message;

    public WrongTypeDataException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
