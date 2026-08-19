package exception;

public class AnimalCapacityExceedException extends RuntimeException {
    public AnimalCapacityExceedException() {
        super("등록할 수 있는 최대 주민 수는 10마리입니다.");
    }
}
