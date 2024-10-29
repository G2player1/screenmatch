package Enos.projetoSpring.screenmatch.Exceptions;

public class CantGetDataException extends RuntimeException {

    private final String message;

    public CantGetDataException(String message) {
      this.message = message;
    }

    @Override
    public String getMessage() {
      return message;
    }
}
