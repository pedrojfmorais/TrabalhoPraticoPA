package pt.isec.pa.apoio_poe.model.data.pessoas;

import pt.isec.pa.apoio_poe.model.errorHandling.ErrorOccurred;
import pt.isec.pa.apoio_poe.model.errorHandling.ErrorsTypes;

import java.io.Serializable;

public abstract class Pessoa implements Cloneable, Serializable {
    private String nome;
    private final String email;

    protected Pessoa(String nome, String email){
       this.nome = nome;
       this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {    //pode ser alterado o nome, não o email
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Nome: ").append(nome).append(System.lineSeparator());
        sb.append("Email: ").append(email).append(System.lineSeparator());
        return sb.toString();
    }

    @Override
    public Pessoa clone() {
        try {
            return (Pessoa) super.clone();

        } catch (CloneNotSupportedException e) {
            ErrorOccurred.getInstance().setError(ErrorsTypes.CLONE_NOT_FOUND);
        }
        return null;
    }
}
