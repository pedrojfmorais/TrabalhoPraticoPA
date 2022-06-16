package pt.isec.pa.apoio_poe.model.data.propostas;
/**
 * Classe Autoproposto com as informações das Autopropostas, derivada da classe Proposta
 * @author Maria Abreu e Pedro Morais
 * @version 1.0.0
 */
public class Autoproposto extends Proposta{
    /**
     * Construtor público
     *
     * @param id id da Proposta
     * @param titulo titulo da Proposta
     * @param nAlunoAssociado número do aluno associado a esta proposta
     */
    public Autoproposto(String id, String titulo, long nAlunoAssociado){
        super(id, titulo, nAlunoAssociado);
    }
    /**
     * Retorna o tipo de proposta correspondente a Autoproposta
     * @return "T3" - tipo de proposta
     */
    @Override
    public String tipoProposta() {
        return "T3";
    }
    /**
     * Obter informações da Autoproposta
     * @return String - string com as informações da Autoproposta
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Autoproposto").append(System.lineSeparator());
        sb.append(super.toString());

        return sb.toString();
    }
    /**
     * Obter clone do objeto Autoproposto
     * @return Autoproposto - clone deste objeto Autoproposto
     */
    @Override
    public Autoproposto clone() {
        return (Autoproposto) super.clone();
    }
}
