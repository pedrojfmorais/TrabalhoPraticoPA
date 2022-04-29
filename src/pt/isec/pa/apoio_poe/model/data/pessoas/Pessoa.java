package pt.isec.pa.apoio_poe.model.data.pessoas;

import pt.isec.pa.apoio_poe.model.exceptionsHandling.ExceptionOccurred;
import pt.isec.pa.apoio_poe.model.exceptionsHandling.ExceptionsTypes;

import java.io.Serializable;

public class Pessoa implements Cloneable, Serializable {
    private String nome;
    private final String email;

    protected Pessoa(String nome, String email){
       this.nome = nome;
       this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {    //pode ser alterado o nome, not o email
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
            ExceptionOccurred.setException(ExceptionsTypes.CloneNotFound);
        }
        return null;
    }
}
