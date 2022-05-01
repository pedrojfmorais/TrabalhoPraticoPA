package pt.isec.pa.apoio_poe.model.data.propostas;

public class PropostaAtribuida extends Proposta{

    private String emailDocenteOrientador;
    private final int ordemPreferencia;

    public PropostaAtribuida(String id, String titulo, long nAlunoAssociado, int ordemPreferencia) {
        super(id, titulo, nAlunoAssociado);
        this.ordemPreferencia = ordemPreferencia;
    }

    public String getEmailDocenteOrientador() {
        return emailDocenteOrientador;
    }
    public void setEmailDocenteOrientador(String emailDocenteOrientador) {
        this.emailDocenteOrientador = emailDocenteOrientador;
    }

    public int getOrdemPreferencia() {
        return ordemPreferencia;
    }

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

    @Override
    public PropostaAtribuida clone() {
        return (PropostaAtribuida) super.clone();
    }
}
