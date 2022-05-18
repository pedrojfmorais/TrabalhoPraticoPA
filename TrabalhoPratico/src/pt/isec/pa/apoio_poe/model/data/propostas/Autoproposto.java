package pt.isec.pa.apoio_poe.model.data.propostas;

public class Autoproposto extends Proposta{
    public Autoproposto(String id, String titulo, long nAlunoAssociado){
        super(id, titulo, nAlunoAssociado);
    }

    @Override
    public String tipoProposta() {
        return "T3";
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Autoproposto").append(System.lineSeparator());
        sb.append(super.toString());

        return sb.toString();
    }

    @Override
    public Autoproposto clone() {
        return (Autoproposto) super.clone();
    }
}
