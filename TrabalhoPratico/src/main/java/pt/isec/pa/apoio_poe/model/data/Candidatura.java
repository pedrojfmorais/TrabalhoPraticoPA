package pt.isec.pa.apoio_poe.model.data;

import pt.isec.pa.apoio_poe.model.errorHandling.ErrorOccurred;
import pt.isec.pa.apoio_poe.model.errorHandling.ErrorsTypes;

import java.io.Serializable;
import java.util.List;
/**
 * Classe Candidatura com as informações das Candidaturas
 * @author Maria Abreu e Pedro Morais
 * @version 1.0.0
 */
public class Candidatura implements Comparable<Candidatura>, Cloneable, Serializable {
    /**
     * número do aluno que fez a candidatura
     */
    private final long nAluno;
    /**
     * propostas a que o aluno se candidatou
     */
    private List<String> idPropostas;
    /**
     * Construtor público
     *
     * @param nAluno número do aluno que fez a candidatura
     * @param idPropostas propostas a que o aluno se candidatou
     */
    public Candidatura(long nAluno, List<String> idPropostas){
        this.nAluno = nAluno;
        this.idPropostas = idPropostas;
    }
    /**
     * Obter número do aluno que fez a candidatura
     * @return nAluno - número do aluno que fez a candidatura
     */
    public long getNAluno() {
        return nAluno;
    }
    /**
     * Obter propostas a que o aluno se candidatou
     * @return idPropostas - propostas a que o aluno se candidatou
     */
    public List<String> getIdPropostas() {
        return idPropostas;
    }
    /**
     * Alterar as propostas a que o aluno se candidatou
     * @param idPropostas novas propostas a que o aluno se candidatou
     */
    public void setIdPropostas(List<String> idPropostas) {
        this.idPropostas = idPropostas;
    }

    /**
     * Adiciona uma proposta a lista de propostas a que o aluno se candidatou
     * @param idProposta nova proposta a que o aluno se candidata
     * @return boolean - retorna se consegui adicionar ou não
     */
    public boolean adicionaProposta(String idProposta){
        return idPropostas.add(idProposta);
    }
    /**
     * Remove uma proposta a lista de propostas a que o aluno se candidatou
     * @param idProposta proposta a remover da lista de propostas a que o aluno se candidata
     * @return boolean - retorna se consegui remover a proposta ou não
     */
    public boolean removeProposta(String idProposta){
        return idPropostas.remove(idProposta);
    }
    /**
     * Obter informações da Candidatura
     * @return String - string com as informações da Candidatura
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Aluno candidato: ").append(nAluno).append(System.lineSeparator());
        sb.append("Propostas: ").append(idPropostas).append(System.lineSeparator());

        return sb.toString();
    }
    /**
     * Compara este objeto Candidatura a outro através do id
     * @param o objeto a comparar
     * @return boolean - retorna qual deles é lexicograficamente superior ao outro
     */
    @Override
    public int compareTo(Candidatura o) {
        return Long.compare(nAluno, o.nAluno);
    }
    /**
     * Obter clone do objeto Candidatura
     * @return Candidatura - clone deste objeto Candidatura
     */
    @Override
    public Candidatura clone() {
        try {
            return (Candidatura) super.clone();
        } catch (CloneNotSupportedException e) {
            ErrorOccurred.getInstance().setError(ErrorsTypes.CLONE_NOT_FOUND);
        }
        return null;
    }
}
