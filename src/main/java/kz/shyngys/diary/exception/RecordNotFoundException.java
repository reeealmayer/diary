package kz.shyngys.diary.exception;

//TODO: ExceptionHandler сделать
public class RecordNotFoundException extends RuntimeException {
    public RecordNotFoundException(String message) {
        super(message);
    }
}
