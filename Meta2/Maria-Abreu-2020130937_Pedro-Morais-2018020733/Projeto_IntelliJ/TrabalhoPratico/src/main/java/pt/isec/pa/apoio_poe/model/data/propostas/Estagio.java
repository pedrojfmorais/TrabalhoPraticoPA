package pt.isec.pa.apoio_poe.model.data.propostas;
/**
 * Classe Estagio com as informações dos Estagios, derivada da classe Proposta
 * @author Maria Abreu e Pedro Morais
 * @version 1.0.0
 */
public class Estagio extends Proposta{
    /**
     * area de destino do Estagio
     */
    private String areasDestino;
    /**
     * entidade de acolhimento do Estagio
     */
    private String entidadeAcolhimento;
    /**
     * Construtor público
     *
     * @param id id da Proposta
     * @param titulo titulo da Proposta
     * @param nAlunoAssociado número do aluno associado a esta proposta
     * @param areasDestino area de destino do Estagio
     * @param entidadeAcolhimento entidade de acolhimento do Estagio
     */
   public Estagio(String id, String titulo, long nAlunoAssociado, String areasDestino, String entidadeAcolhimento){
       super(id, titulo, nAlunoAssociado);
       this.areasDestino = areasDestino;
       this.entidadeAcolhimento = entidadeAcolhimento;
   }
    /**
     * Construtor público
     *
     * @param id id da Proposta
     * @param titulo titulo da Proposta
     * @param areasDestino area de destino do Estagio
     * @param entidadeAcolhimento entidade de acolhimento do Estagio
     */
   public Estagio(String id, String titulo, String areasDestino, String entidadeAcolhimento){
       super(id, titulo);
       this.areasDestino = areasDestino;
       this.entidadeAcolhimento = entidadeAcolhimento;
   }
    /**
     * Obter o area de destino do Estagio
     * @return areasDestino - area de destino do Estagio
     */
    public String getAreasDestino() {
        return areasDestino;
    }
    /**
     * Alterar o area de destino do Estagio
     * @param areasDestino area de destino do Estagio
     */
    public void setAreasDestino(String areasDestino) {
        this.areasDestino = areasDestino;
    }
    /**
     * Obter a entidade de acolhimento do Estagio
     * @return entidadeAcolhimento - entidade de acolhimento do Estagio
     */
    public String getEntidadeAcolhimento() {
        return entidadeAcolhimento;
    }
    /**
     * Alterar a entidade de acolhimento do Estagio
     * @param entidadeAcolhimento entidade de acolhimento do Estagio
     */
    public void setEntidadeAcolhimento(String entidadeAcolhimento) {
        this.entidadeAcolhimento = entidadeAcolhimento;
    }
    /**
     * Retorna o tipo de proposta correspondente ao Estagio
     * @return "T1" - tipo de proposta
     */
    @Override
    public String tipoProposta() {
        return "T1";
    }
    /**
     * Obter informações do Estagio
     * @return String - string com as informações do Estagio
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Estágio").append(System.lineSeparator());
        sb.append(super.toString());
        sb.append("Areas Destino: ").append(areasDestino).append(System.lineSeparator());
        sb.append("Entidade Acolhimento: ").append(entidadeAcolhimento).append(System.lineSeparator());

        return sb.toString();
    }
    /**
     * Obter clone do objeto Estagio
     * @return Estagio - clone deste objeto Estagio
     */
    @Override
    public Estagio clone() {
        return (Estagio) super.clone();
    }
}
