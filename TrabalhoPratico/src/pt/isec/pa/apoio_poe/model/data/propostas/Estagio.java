package pt.isec.pa.apoio_poe.model.data.propostas;

public class Estagio extends Proposta{
    private String areasDestino;
    private String entidadeAcolhimento;

   public Estagio(String id, String titulo, long nAlunoAssociado, String areasDestino, String entidadeAcolhimento){
       super(id, titulo, nAlunoAssociado);
       this.areasDestino = areasDestino;
       this.entidadeAcolhimento = entidadeAcolhimento;
   }

   public Estagio(String id, String titulo, String areasDestino, String entidadeAcolhimento){
       super(id, titulo);
       this.areasDestino = areasDestino;
       this.entidadeAcolhimento = entidadeAcolhimento;
   }

    public String getAreasDestino() {
        return areasDestino;
    }

    public void setAreasDestino(String areasDestino) {
        this.areasDestino = areasDestino;
    }

    public String getEntidadeAcolhimento() {
        return entidadeAcolhimento;
    }

    public void setEntidadeAcolhimento(String entidadeAcolhimento) {
        this.entidadeAcolhimento = entidadeAcolhimento;
    }

    @Override
    public String tipoProposta() {
        return "T1";
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Estágio").append(System.lineSeparator());
        sb.append(super.toString());
        sb.append("Areas Destino: ").append(areasDestino).append(System.lineSeparator());
        sb.append("Entidade Acolhimento: ").append(entidadeAcolhimento).append(System.lineSeparator());

        return sb.toString();
    }

    @Override
    public Estagio clone() {
        return (Estagio) super.clone();
    }
}
