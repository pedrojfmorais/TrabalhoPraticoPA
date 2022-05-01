package pt.isec.pa.apoio_poe.model.exceptionsHandling;

public class ExceptionOccurred {
    private ExceptionsTypes exception;
    private static ExceptionOccurred instance = null;

    public static ExceptionOccurred getInstance(){
        if(instance == null)
            instance = new ExceptionOccurred();
        return instance;
    }

    private ExceptionOccurred(){
        exception = ExceptionsTypes.NONE;
    }

    public ExceptionsTypes getException() {
        return exception;
    }

    public void setException(ExceptionsTypes exception) {
        this.exception = exception;
    }
}
