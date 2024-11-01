package Enos.projetoSpring.screenmatch.Exceptions;

public class DontHaveSeasonsException extends RuntimeException {

    private final String message;
    public DontHaveSeasonsException(String message) {
        this.message = message;
    }

  @Override
  public String getMessage() {
    return message;
  }
}
