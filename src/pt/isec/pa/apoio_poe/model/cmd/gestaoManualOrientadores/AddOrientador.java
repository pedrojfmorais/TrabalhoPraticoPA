package pt.isec.pa.apoio_poe.model.cmd.gestaoManualOrientadores;

import pt.isec.pa.apoio_poe.model.cmd.CommandAdapter;
import pt.isec.pa.apoio_poe.model.data.ApoioPoeManager;

public class AddOrientador extends CommandAdapter {

    private long nAluno;
    private String novoOrientador;


    public AddOrientador(ApoioPoeManager apoioPoeManager, long nAluno, String novoOrientador) {
        super(apoioPoeManager);
        this.nAluno = nAluno;
        this.novoOrientador = novoOrientador;

    }

    @Override
    public boolean execute() {

        String idPropostaAtribuida = null;

        for(var propostasAtribuidas : apoioPoeManager.getPropostasAtribuidas())
            if(propostasAtribuidas.getnAlunoAssociado() == nAluno)
                idPropostaAtribuida = propostasAtribuidas.getId();

        if(idPropostaAtribuida == null)
            return false;

        return apoioPoeManager.atribuirPropostaDocenteOrientador(idPropostaAtribuida, novoOrientador);
    }

    @Override
    public boolean undo() {

        String idPropostaAtribuida = null;

        for(var propostasAtribuidas : apoioPoeManager.getPropostasAtribuidas())
            if(propostasAtribuidas.getnAlunoAssociado() == nAluno)
                idPropostaAtribuida = propostasAtribuidas.getId();

        if(idPropostaAtribuida == null)
            return false;

        return apoioPoeManager.removeOrientadorPropostaAtribuida(idPropostaAtribuida);
    }
}
