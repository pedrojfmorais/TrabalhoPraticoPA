package pt.isec.pa.apoio_poe.model.data.pessoas.alunos;

import pt.isec.pa.apoio_poe.model.data.pessoas.Pessoa;

import java.util.ArrayList;
import java.util.Arrays;

public class Aluno extends Pessoa implements Comparable<Aluno>{
    private final long nAluno;
    private String siglaCurso;
    private String siglaRamo;
    private double classificacao;
    private boolean acessoEstagio;

    public static final ArrayList<String> cursos;
    public static final ArrayList<String> ramos;

    static{
        cursos = new ArrayList<>(Arrays.asList("LEI", "LEI-PL"));
        ramos = new ArrayList<>(Arrays.asList("DA", "RAS", "SI"));
    }

    public Aluno(long nAluno, String nome, String email, String siglaCurso, String siglaRamo, double classificacao, boolean acessoEstagio){
        super(nome, email);             //vai usar o construtor da classe Pessoa
        this.nAluno = nAluno;
        this.siglaCurso = siglaCurso;
        this.siglaRamo = siglaRamo;
        this.classificacao = classificacao;
        this.acessoEstagio = acessoEstagio;
    }

    public long getNAluno() {
        return nAluno;
    }

    public String getSiglaCurso() {
        return siglaCurso;
    }
    public void setSiglaCurso(String siglaCurso) {
        this.siglaCurso = siglaCurso;
    }

    public String getSiglaRamo() {
        return siglaRamo;
    }
    public void setSiglaRamo(String siglaRamo) {
        this.siglaRamo = siglaRamo;
    }

    public double getClassificacao() {
        return classificacao;
    }
    public void setClassificacao(double classificacao) {
        this.classificacao = classificacao;
    }

    public boolean isAcessoEstagio() {
        return acessoEstagio;
    }
    public void setAcessoEstagio(boolean acessoEstagio) {
        this.acessoEstagio = acessoEstagio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Pessoa)) return false;

        Aluno aluno = (Aluno) o;

        return nAluno == aluno.nAluno || getEmail().equals(aluno.getEmail());
    }

    @Override
    public int hashCode() {
        return (int) nAluno + getEmail().hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Número aluno: ").append(nAluno).append(System.lineSeparator());
        sb.append(super.toString());
        sb.append("Curso: ").append(siglaCurso).append(System.lineSeparator());
        sb.append("Ramo: ").append(siglaRamo).append(System.lineSeparator());
        sb.append("Classificação: ").append(classificacao).append(System.lineSeparator());
        sb.append("Tem acesso a estágio: ").append(acessoEstagio ? "Sim" : "Não").append(System.lineSeparator());

        return sb.toString();
    }

    @Override
    public int compareTo(Aluno o) {
        return Long.compare(nAluno, o.nAluno);
    }

    @Override
    public Aluno clone() {
        return (Aluno) super.clone();
    }
}
