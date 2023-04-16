package pt.isec.pa.apoio_poe.model.data.propostas;
/**
 * Classe PropostaAtribuida com as informações das Propostas Atribuida, derivada da classe Proposta
 * @author Maria Abreu e Pedro Morais
 * @version 1.0.0
 */
public class PropostaAtribuida extends Proposta{
    /**
     * email do docente orientador da proposta atribuida
     */
    private String emailDocenteOrientador;
    /**
     * ordem de preferencia da proposta na candidatura do aluno
     */
    private final int ordemPreferencia;
    /**
     * Construtor público
     *
     * @param id id da Proposta
     * @param titulo titulo da Proposta
     * @param nAlunoAssociado número do aluno associado a esta proposta
     * @param ordemPreferencia ordem de preferencia da proposta na candidatura do aluno
     */
    public PropostaAtribuida(String id, String titulo, long nAlunoAssociado, int ordemPreferencia) {
        super(id, titulo, nAlunoAssociado);
        this.ordemPreferencia = ordemPreferencia;
    }
    /**
     * Obter o email do docente orientador da proposta atribuida
     * @return emailDocenteOrientador - email do docente orientador da proposta atribuida
     */
    public String getEmailDocenteOrientador() {
        return emailDocenteOrientador;
    }
    /**
     * Alterar o email do docente orientador da proposta atribuida
     * @param emailDocenteOrientador novo email do docente orientador da proposta atribuida
     */
    public void setEmailDocenteOrientador(String emailDocenteOrientador) {
        this.emailDocenteOrientador = emailDocenteOrientador;
    }
    /**
     * Obter o ordem de preferencia da proposta na candidatura do aluno
     * @return ordemPreferencia - ordem de preferencia da proposta na candidatura do aluno
     */
    public int getOrdemPreferencia() {
        return ordemPreferencia;
    }
    /**
     * Retorna o tipo de proposta correspondente a Proposta Atribuida
     * @return null - tipo de proposta
     */
    @Override
    public String tipoProposta() {
        return null;
    }
    /**
     * Obter informações da Proposta Atribuida
     * @return String - string com as informações da Proposta Atribuida
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Proposta Atribuida").append(System.lineSeparator());
        sb.append("Ordem de preferência do aluno: ").append(ordemPreferencia).append(System.lineSeparator());
        sb.append(super.toString());

        if(emailDocenteOrientador != null)
            sb.append("Docente Orientador: ").append(emailDocenteOrientador).append(System.lineSeparator());

        return sb.toString();
    }
    /**
     * Obter clone do objeto Proposta Atribuida
     * @return PropostaAtribuida - clone deste objeto Proposta Atribuida
     */
    @Override
    public PropostaAtribuida clone() {
        return (PropostaAtribuida) super.clone();
    }
}
