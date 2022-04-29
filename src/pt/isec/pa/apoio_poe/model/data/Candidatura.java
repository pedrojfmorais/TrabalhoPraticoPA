package pt.isec.pa.apoio_poe.model.data;

import pt.isec.pa.apoio_poe.model.exceptionsHandling.ExceptionOccurred;
import pt.isec.pa.apoio_poe.model.exceptionsHandling.ExceptionsTypes;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;

public class Candidatura implements Comparable<Candidatura>, Cloneable, Serializable {
    private final long nAluno;
    private List<String> idPropostas;

    Candidatura(long nAluno, List<String> idPropostas){
        this.nAluno = nAluno;
        this.idPropostas = idPropostas;
    }

    public long getnAluno() {
        return nAluno;
    }

    public List<String> getIdPropostas() {
        return idPropostas;
    }
    public void setIdPropostas(List<String> idPropostas) {
        this.idPropostas = idPropostas;
    }

    public boolean adicionaProposta(String idProposta){
        return idPropostas.add(idProposta);
    }
    public boolean removeProposta(String idProposta){
        return idPropostas.remove(idProposta);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Aluno candidato: ").append(nAluno).append(System.lineSeparator());
        sb.append("Propostas: ").append(idPropostas).append(System.lineSeparator());

        return sb.toString();
    }

    @Override
    public int compareTo(Candidatura o) {
        return Long.compare(nAluno, o.nAluno);
    }

    @Override
    public Candidatura clone() {
        try {
            return (Candidatura) super.clone();
        } catch (CloneNotSupportedException e) {
            ExceptionOccurred.setException(ExceptionsTypes.CloneNotFound);
        }
        return null;
    }
}
