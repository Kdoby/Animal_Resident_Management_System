package exception;

public class AnimalNotFoundException extends RuntimeException {
    public AnimalNotFoundException() {
        super("존재하지 않는 동물입니다.");
    }
}
