package pt.isec.pa.apoio_poe.model.data.propostas;

import pt.isec.pa.apoio_poe.model.errorHandling.ErrorOccurred;
import pt.isec.pa.apoio_poe.model.errorHandling.ErrorType;

import java.io.Serializable;
/**
 * Classe genérica com as informações das Propostas
 * @author Maria Abreu e Pedro Morais
 * @version 1.0.0
 */
public abstract class Proposta implements Comparable<Proposta>, Cloneable, Serializable {
    /**
     * id da Proposta
     */
    private final String id;
    /**
     * titulo da Proposta
     */
    private String titulo;
    /**
     * número do aluno associado a esta proposta
     */
    private long nAlunoAssociado;

    /**
     * Construtor público
     *
     * @param id id da Proposta
     * @param titulo titulo da Proposta
     * @param nAlunoAssociado número do aluno associado a esta proposta
     */
    protected Proposta(String id, String titulo, long nAlunoAssociado){
        this.id = id;
        this.titulo = titulo;
        this.nAlunoAssociado = nAlunoAssociado;
    }
    /**
     * Construtor protected
     *
     * utilizado pelas classes derivadas quando estas não pretendem inserir um valor para o número de aluno associado
     * @param id id da Proposta
     * @param titulo titulo da Proposta
     */
    protected Proposta(String id, String titulo){  //poderá não indicar o aluno associado
        this.id = id;
        this.titulo = titulo;
    }
    /**
     * Obter o id da proposta
     * @return id - id da proposta
     */
    public String getId() {
        return id;
    }
    /**
     * Obter o titulo da proposta
     * @return titulo - titulo da proposta
     */
    public String getTitulo() {
        return titulo;
    }
    /**
     * Alterar titulo da proposta
     * @param titulo novo titulo da proposta
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    /**
     * Obter número do aluno associado a proposta
     * @return nAlunoAssociado - número do aluno associado a proposta
     */
    public long getNAlunoAssociado() {
        return nAlunoAssociado;
    }
    /**
     * Alterar número do aluno associado a proposta
     * @param nAlunoAssociado novo número do aluno associado a proposta
     */
    public void setNAlunoAssociado(long nAlunoAssociado) {
        this.nAlunoAssociado = nAlunoAssociado;
    }
    /**
     * Comparar Propostas pelo seu id
     * @param o objeto a comparar
     * @return boolean - retorna se as duas propostas tem o id igual
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Proposta proposta)) return false;

        return id.equals(proposta.id);
    }
    /**
     * Função abstrata que permite obter o tipo de proposta (T1, T2, T3)
     */
    public abstract String tipoProposta();
    /**
     * Gera o hashCode para os objetos Proposta
     * @return hashCode - retorna um inteiro único que identifica este objeto
     */
    @Override
    public int hashCode() {
        return id.hashCode();
    }
    /**
     * Obter informações da Proposta
     * @return String - string com as informações da Proposta
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("ID: ").append(id).append(System.lineSeparator());
        sb.append("Titulo: ").append(titulo).append(System.lineSeparator());

        if(nAlunoAssociado != 0)
            sb.append("Aluno Associado: ").append(nAlunoAssociado).append(System.lineSeparator());

        return sb.toString();
    }
    /**
     * Compara este objeto Proposta a outro através do id
     * @param o objeto a comparar
     * @return boolean - retorna qual deles é lexicograficamente superior ao outro
     */
    @Override
    public int compareTo(Proposta o) {
        return id.compareTo(o.id);
    }
    /**
     * Obter clone do objeto Proposta
     * @return Proposta - clone deste objeto Proposta
     */
    @Override
    public Proposta clone() {
        try {
            return (Proposta) super.clone();
        } catch (CloneNotSupportedException e) {
            ErrorOccurred.getInstance().setError(ErrorType.CLONE_NOT_FOUND);
        }
        return null;
    }
}
