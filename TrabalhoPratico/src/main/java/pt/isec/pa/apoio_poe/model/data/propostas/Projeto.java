package pt.isec.pa.apoio_poe.model.data.propostas;

public class Projeto extends Proposta{
    private String ramosDestino;
    private String emailDocente;

    public Projeto(String id, String titulo, long nAlunoAssociado, String ramosDestino, String emailDocente) {
        super(id, titulo, nAlunoAssociado);
        this.ramosDestino = ramosDestino;
        this.emailDocente = emailDocente;
    }
    public Projeto(String id, String titulo, String ramosDestino, String emailDocente) {
        super(id, titulo);
        this.ramosDestino = ramosDestino;
        this.emailDocente = emailDocente;
    }

    public String getRamosDestino() {
        return ramosDestino;
    }
    public void setRamosDestino(String ramosDestino) {
        this.ramosDestino = ramosDestino;
    }

    public String getEmailDocente() {
        return emailDocente;
    }
    public void setEmailDocente(String emailDocente) {
        this.emailDocente = emailDocente;
    }

    @Override
    public String tipoProposta() {
        return "T2";
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Projeto").append(System.lineSeparator());
        sb.append(super.toString());
        sb.append("Ramos Destino: ").append(ramosDestino).append(System.lineSeparator());
        sb.append("Docente Proponente: ").append(emailDocente).append(System.lineSeparator());

        return sb.toString();
    }

    @Override
    public Projeto clone() {
        return (Projeto) super.clone();
    }
}
