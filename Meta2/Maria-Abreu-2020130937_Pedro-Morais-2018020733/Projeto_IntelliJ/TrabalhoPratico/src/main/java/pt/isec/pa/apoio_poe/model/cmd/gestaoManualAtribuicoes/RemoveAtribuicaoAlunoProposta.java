package pt.isec.pa.apoio_poe.model.cmd.gestaoManualAtribuicoes;

import pt.isec.pa.apoio_poe.model.cmd.CommandAdapter;
import pt.isec.pa.apoio_poe.model.data.ApoioPoEManager;

public class RemoveAtribuicaoAlunoProposta extends CommandAdapter {

    private long nAluno;
    private final String idProposta;

    public RemoveAtribuicaoAlunoProposta(ApoioPoEManager apoioPoeManager, String idProposta) {
        super(apoioPoeManager);
        this.idProposta = idProposta;

        if(apoioPoeManager.getPropostaAtribuida(idProposta) != null)
            this.nAluno = apoioPoeManager.getPropostaAtribuida(idProposta).getNAlunoAssociado();
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
