package exception;

public class InvalidInputException extends RuntimeException {
    public InvalidInputException(String input) {
        super(input + "값이 올바르지 않습니다.");
    }
}
