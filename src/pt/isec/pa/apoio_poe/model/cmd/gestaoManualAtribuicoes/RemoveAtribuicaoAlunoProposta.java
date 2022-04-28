package pt.isec.pa.apoio_poe.model.cmd.gestaoManualAtribuicoes;

import pt.isec.pa.apoio_poe.model.cmd.CommandAdapter;
import pt.isec.pa.apoio_poe.model.data.ApoioPoeManager;

public class RemoveAtribuicaoAlunoProposta extends CommandAdapter {

    private long nAluno;
    private String idProposta;

    public RemoveAtribuicaoAlunoProposta(ApoioPoeManager apoioPoeManager, String idProposta) {
        super(apoioPoeManager);
        this.idProposta = idProposta;

        if(apoioPoeManager.getPropostaAtribuida(idProposta) != null)
            this.nAluno = apoioPoeManager.getPropostaAtribuida(idProposta).getnAlunoAssociado();
    }

    @Override
    public boolean execute() {
        return apoioPoeManager.removePropostaAtribuida(idProposta);
    }

    @Override
    public boolean undo() {
        return apoioPoeManager.atribuirPropostaAluno(idProposta, nAluno);
    }
}
