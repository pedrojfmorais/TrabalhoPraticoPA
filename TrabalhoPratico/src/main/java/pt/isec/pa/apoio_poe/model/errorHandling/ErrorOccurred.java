package pt.isec.pa.apoio_poe.model.errorHandling;

/**
 * Classe Singleton ErrorOccurred para obtenção do último erro
 * @author Maria Abreu e Pedro Morais
 * @version 1.0.0
 */
public class ErrorOccurred {
    /**
     * Último erro que ocorreu
     */
    private ErrorsTypes error;

    /**
     * Instância estática da classe ErrorOccurred
     */
    private static ErrorOccurred instance = null;

    /**
     * Método que permite obter a única instância da classe de forma estática
     * @return instance - instância única da classe
     */
    public static ErrorOccurred getInstance(){
        if(instance == null)
            instance = new ErrorOccurred();
        return instance;
    }

    /**
     * Construtor privado que permite criar uma nova instância de ErrorOccurred, utilizado apenas uma vez
     */
    private ErrorOccurred(){
        error = ErrorsTypes.NONE;
    }

    /**
     * Método que permite obter o enumerável do último erro que ocorreu
     * @return error - último erro que ocorreu
     */
    public ErrorsTypes getLastError() {
        return error;
    }

    /**
     * Método que permite definir o tipo de erro
     * @param error constante do enumerável que representa um erro
     */
    public void setError(ErrorsTypes error) {
        this.error = error;
    }
}
