package pt.isec.pa.apoio_poe.model.data.pessoas;

import pt.isec.pa.apoio_poe.model.errorHandling.ErrorOccurred;
import pt.isec.pa.apoio_poe.model.errorHandling.ErrorType;

import java.io.Serializable;
/**
 * Classe genérica com as informações das Pessoas
 * @author Maria Abreu e Pedro Morais
 * @version 1.0.0
 *
 */
public abstract class Pessoa implements Cloneable, Serializable {
    /**
     * nome da Pessoa
     */
    private String nome;
    /**
     * email da Pessoa
     */
    private final String email;

    /**
     * Construtor público
     *
     * @param nome nome da Pessoa
     * @param email email da Pessoa
     */
    protected Pessoa(String nome, String email){
       this.nome = nome;
       this.email = email;
    }

    /**
     * Obter nome da Pessoa
     * @return nome - nome da Pessoa
     */
    public String getNome() {
        return nome;
    }

    /**
     * Alterar nome da Pessoa
     * @param nome novo nome para a Pessoa
     */
    public void setNome(String nome) {    //pode ser alterado o nome, não o email
        this.nome = nome;
    }

    /**
     * Obter email da Pessoa
     * @return email - email da Pessoa
     */
    public String getEmail() {
        return email;
    }

    /**
     * Obter informações da Pessoa
     * @return String - string com as informações da Pessoa
     */
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Nome: ").append(nome).append(System.lineSeparator());
        sb.append("Email: ").append(email).append(System.lineSeparator());
        return sb.toString();
    }

    /**
     * Obter clone do objeto Pessoa
     * @return Pessoa - clone deste objeto Pessoa
     */
    @Override
    public Pessoa clone() {
        try {
            return (Pessoa) super.clone();

        } catch (CloneNotSupportedException e) {
            ErrorOccurred.getInstance().setError(ErrorType.CLONE_NOT_FOUND);
        }
        return null;
    }
}
