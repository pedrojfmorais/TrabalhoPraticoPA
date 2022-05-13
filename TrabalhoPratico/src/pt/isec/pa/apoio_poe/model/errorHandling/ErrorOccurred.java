package pt.isec.pa.apoio_poe.model.errorHandling;

public class ErrorOccurred {
    private ErrorsTypes error;
    private static ErrorOccurred instance = null;

    public static ErrorOccurred getInstance(){
        if(instance == null)
            instance = new ErrorOccurred();
        return instance;
    }

    private ErrorOccurred(){
        error = ErrorsTypes.NONE;
    }

    public ErrorsTypes getError() {
        return error;
    }

    public void setError(ErrorsTypes error) {
        this.error = error;
    }
}
