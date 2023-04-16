package pt.isec.pa.apoio_poe.model.cmd.gestaoManualOrientadores;

import pt.isec.pa.apoio_poe.model.cmd.CommandAdapter;
import pt.isec.pa.apoio_poe.model.data.ApoioPoEManager;

public class AddOrientador extends CommandAdapter {

    private final long nAluno;
    private final String novoOrientador;


    public AddOrientador(ApoioPoEManager apoioPoeManager, long nAluno, String novoOrientador) {
        super(apoioPoeManager);
        this.nAluno = nAluno;
        this.novoOrientador = novoOrientador;

    }

    @Override
    public boolean execute() {

        String idPropostaAtribuida = null;

        for(var propostasAtribuidas : apoioPoeManager.getPropostasAtribuidas())
            if(propostasAtribuidas.getNAlunoAssociado() == nAluno)
                idPropostaAtribuida = propostasAtribuidas.getId();

        if(idPropostaAtribuida == null)
            return false;

        return apoioPoeManager.atribuirPropostaDocenteOrientador(idPropostaAtribuida, novoOrientador);
    }

    @Override
    public boolean undo() {

        String idPropostaAtribuida = null;

        for(var propostasAtribuidas : apoioPoeManager.getPropostasAtribuidas())
            if(propostasAtribuidas.getNAlunoAssociado() == nAluno)
                idPropostaAtribuida = propostasAtribuidas.getId();

        if(idPropostaAtribuida == null)
            return false;

        return apoioPoeManager.removeOrientadorPropostaAtribuida(idPropostaAtribuida);
    }
}
