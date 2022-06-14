package pt.isec.pa.apoio_poe.model.data.pessoas.alunos;

import pt.isec.pa.apoio_poe.model.data.pessoas.Pessoa;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Classe Aluno com as informações dos Alunos
 * @author Maria Abreu e Pedro Morais
 * @version 1.0.0
 *
 */
public class Aluno extends Pessoa implements Comparable<Aluno>{
    /**
     * número de aluno
     */
    private final long nAluno;
    /**
     * sigla do curso
     */
    private String siglaCurso;
    /**
     * sigla do ramo
     */
    private String siglaRamo;
    /**
     * classificação do aluno
     */
    private double classificacao;
    /**
     * aluno tem acesso a estágio
     */
    private boolean acessoEstagio;

    /**
     * vários cursos disponíveis
     */
    public static final ArrayList<String> cursos;
    /**
     * vários ramos disponíveis
     */
    public static final ArrayList<String> ramos;


    static{
        cursos = new ArrayList<>(Arrays.asList("LEI", "LEI-PL"));
        ramos = new ArrayList<>(Arrays.asList("DA", "RAS", "SI"));
    }
    /**
     * Construtor público
     *
     * @param nAluno número de estudante
     * @param nome nome do aluno
     * @param email email do aluno
     * @param siglaCurso sigla do curso do aluno
     * @param siglaRamo sigla do ramo do aluno
     * @param classificacao classificação do aluno (0.0 a 1.0)
     * @param acessoEstagio aluno tem acesso a estágio (sim ou não)
     */
    public Aluno(long nAluno, String nome, String email, String siglaCurso, String siglaRamo, double classificacao, boolean acessoEstagio){
        super(nome, email);             //vai usar o construtor da classe Pessoa
        this.nAluno = nAluno;
        this.siglaCurso = siglaCurso;
        this.siglaRamo = siglaRamo;
        this.classificacao = classificacao;
        this.acessoEstagio = acessoEstagio;
    }

    /**
     * Obter número de estudante
     * @return nAluno - número de estudante
     */
    public long getNAluno() {
        return nAluno;
    }
    /**
     * Obter sigla do curso
     * @return siglaCurso - sigla do curso do aluno
     */
    public String getSiglaCurso() {
        return siglaCurso;
    }
    /**
     * Alterar sigla do curso do aluno
     * @param siglaCurso nova sigla do curso do aluno
     */
    public void setSiglaCurso(String siglaCurso) {
        this.siglaCurso = siglaCurso;
    }
    /**
     * Obter sigla do ramo do aluno
     * @return siglaRamo - sigla do ramo do aluno
     */
    public String getSiglaRamo() {
        return siglaRamo;
    }
    /**
     * Alterar sigla do ramo do aluno
     * @param siglaRamo nova sigla do ramo do aluno
     */
    public void setSiglaRamo(String siglaRamo) {
        this.siglaRamo = siglaRamo;
    }
    /**
     * Obter classificação do aluno
     * @return classificacao - classificação do aluno
     */
    public double getClassificacao() {
        return classificacao;
    }
    /**
     * Alterar classificação do aluno
     * @param classificacao nova classificação do aluno
     */
    public void setClassificacao(double classificacao) {
        this.classificacao = classificacao;
    }
    /**
     * Obter se o aluno tem acesso a estágio ou não
     * @return acessoEstagio - aluno tem acesso a estágio (true/false)
     */
    public boolean isAcessoEstagio() {
        return acessoEstagio;
    }
    /**
     * Alterar acesso a estágio do aluno
     * @param acessoEstagio novo acesso a estágio do aluno
     */
    public void setAcessoEstagio(boolean acessoEstagio) {
        this.acessoEstagio = acessoEstagio;
    }
    /**
     * Comparar Alunos pelo número de estudante e email
     * @param o objeto a comparar
     * @return boolean - retorna se os dois alunos tem o número estudante ou email igual
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Pessoa)) return false;

        Aluno aluno = (Aluno) o;

        return nAluno == aluno.nAluno || getEmail().equals(aluno.getEmail());
    }
    /**
     * Gera o hashCode para os objetos Aluno
     * @return hashCode - retorna um inteiro único que identifica este objeto
     */
    @Override
    public int hashCode() {
        return (int) nAluno + getEmail().hashCode();
    }
    /**
     * Obter informações da Aluno
     * @return sb.toString() - string com as informações do Aluno
     */
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
    /**
     * Compara este objeto Aluno a outro através do número de estudante
     * @param o objeto a comparar
     * @return boolean - retorna se os dois alunos tem o número de estudante igual
     */
    @Override
    public int compareTo(Aluno o) {
        return Long.compare(nAluno, o.nAluno);
    }
    /**
     * Obter clone do objeto Aluno
     * @return Aluno - clone deste objeto Aluno
     */
    @Override
    public Aluno clone() {
        return (Aluno) super.clone();
    }
}
