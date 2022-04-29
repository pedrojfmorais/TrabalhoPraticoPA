package pt.isec.pa.apoio_poe.model.exceptionsHandling;

public class ExceptionOccurred {
    private static ExceptionsTypes exception;

    static {
        exception = ExceptionsTypes.NONE;
    }

    public static ExceptionsTypes getException() {
        return exception;
    }

    public static void setException(ExceptionsTypes exception) {
        ExceptionOccurred.exception = exception;
    }
}
