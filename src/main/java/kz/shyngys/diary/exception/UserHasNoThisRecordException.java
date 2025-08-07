package kz.shyngys.diary.exception;

public class UserHasNoThisRecordException extends RuntimeException {
    public UserHasNoThisRecordException(String message) {
        super(message);
    }
}
