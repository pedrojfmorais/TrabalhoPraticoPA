package pt.isec.pa.apoio_poe.model.cmd.gestaoManualAtribuicoes;

import pt.isec.pa.apoio_poe.model.cmd.CommandAdapter;
import pt.isec.pa.apoio_poe.model.data.ApoioPoEManager;

public class AddAtribuicaoAlunoProposta extends CommandAdapter {

    private final long nAluno;
    private final String idProposta;

    public AddAtribuicaoAlunoProposta(ApoioPoEManager apoioPoeManager, long nAluno, String idProposta) {
        super(apoioPoeManager);

        this.nAluno = nAluno;
        this.idProposta = idProposta;
    }

    @Override
    public boolean execute() {
        return apoioPoeManager.atribuirPropostaAluno(idProposta, nAluno);
    }

    @Override
    public boolean undo() {
        return apoioPoeManager.removePropostaAtribuida(idProposta);
    }
}
