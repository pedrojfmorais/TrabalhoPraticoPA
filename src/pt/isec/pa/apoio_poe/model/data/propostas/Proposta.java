package pt.isec.pa.apoio_poe.model.data.propostas;

import java.io.Serializable;

public class Proposta implements Comparable<Proposta>, Cloneable, Serializable {
    private String id;
    private String titulo;
    private long nAlunoAssociado;

    protected Proposta(String id, String titulo, long nAlunoAssociado){
        this.id = id;
        this.titulo = titulo;
        this.nAlunoAssociado = nAlunoAssociado;
    }
    protected Proposta(String id, String titulo){  //poderá não indicar o aluno associado
        this.id = id;
        this.titulo = titulo;
    }

    public String getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public long getnAlunoAssociado() {
        return nAlunoAssociado;
    }
    public void setnAlunoAssociado(long nAlunoAssociado) {
        this.nAlunoAssociado = nAlunoAssociado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Proposta)) return false;

        Proposta proposta = (Proposta) o;

        return id.equals(proposta.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("ID: ").append(id).append(System.lineSeparator());
        sb.append("Titulo: ").append(titulo).append(System.lineSeparator());

        if(nAlunoAssociado != 0)
            sb.append("Aluno Associado: ").append(nAlunoAssociado).append(System.lineSeparator());

        return sb.toString();
    }

    @Override
    public int compareTo(Proposta o) {
        return id.compareTo(o.id);
    }

    @Override
    public Proposta clone() {
        try {
            return (Proposta) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
