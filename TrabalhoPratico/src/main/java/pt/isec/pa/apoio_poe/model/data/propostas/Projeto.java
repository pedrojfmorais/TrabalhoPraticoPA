package pt.isec.pa.apoio_poe.model.data.propostas;
/**
 * Classe Projeto com as informações dos Projeto, derivada da classe Proposta
 * @author Maria Abreu e Pedro Morais
 * @version 1.0.0
 */
public class Projeto extends Proposta{
    /**
     * ramo de destino do projeto
     */
    private String ramosDestino;
    /**
     * email do docente proponente do projeto
     */
    private String emailDocente;
    /**
     * Construtor público
     *
     * @param id id da Proposta
     * @param titulo titulo da Proposta
     * @param nAlunoAssociado número do aluno associado a esta proposta
     * @param ramosDestino ramo de destino do projeto
     * @param emailDocente email do docente proponente do projeto
     */
    public Projeto(String id, String titulo, long nAlunoAssociado, String ramosDestino, String emailDocente) {
        super(id, titulo, nAlunoAssociado);
        this.ramosDestino = ramosDestino;
        this.emailDocente = emailDocente;
    }
    /**
     * Construtor público
     *
     * @param id id da Proposta
     * @param titulo titulo da Proposta
     * @param ramosDestino ramo de destino do projeto
     * @param emailDocente email do docente proponente do projeto
     */
    public Projeto(String id, String titulo, String ramosDestino, String emailDocente) {
        super(id, titulo);
        this.ramosDestino = ramosDestino;
        this.emailDocente = emailDocente;
    }
    /**
     * Obter o ramo de destino do projeto
     * @return ramosDestino - ramo de destino do projeto
     */
    public String getRamosDestino() {
        return ramosDestino;
    }
    /**
     * Alterar o ramo de destino do projeto
     * @param ramosDestino novo ramo de destino do projeto
     */
    public void setRamosDestino(String ramosDestino) {
        this.ramosDestino = ramosDestino;
    }
    /**
     * Obter o email do docente proponente do projeto
     * @return emailDocente - email do docente proponente do projeto
     */
    public String getEmailDocente() {
        return emailDocente;
    }
    /**
     * Alterar o email do docente proponente do projeto
     * @param emailDocente novo email do docente proponente do projeto
     */
    public void setEmailDocente(String emailDocente) {
        this.emailDocente = emailDocente;
    }
    /**
     * Retorna o tipo de proposta correspondente ao Projeto
     * @return "T2" - tipo de proposta
     */
    @Override
    public String tipoProposta() {
        return "T2";
    }
    /**
     * Obter informações do Projeto
     * @return String - string com as informações do Projeto
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Projeto").append(System.lineSeparator());
        sb.append(super.toString());
        sb.append("Ramos Destino: ").append(ramosDestino).append(System.lineSeparator());
        sb.append("Docente Proponente: ").append(emailDocente).append(System.lineSeparator());

        return sb.toString();
    }
    /**
     * Obter clone do objeto Projeto
     * @return Projeto - clone deste objeto Projeto
     */
    @Override
    public Projeto clone() {
        return (Projeto) super.clone();
    }
}
