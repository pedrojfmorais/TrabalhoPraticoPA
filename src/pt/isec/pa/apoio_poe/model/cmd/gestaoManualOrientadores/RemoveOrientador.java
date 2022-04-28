package pt.isec.pa.apoio_poe.model.cmd.gestaoManualOrientadores;

import pt.isec.pa.apoio_poe.model.cmd.CommandAdapter;
import pt.isec.pa.apoio_poe.model.data.ApoioPoeManager;

public class RemoveOrientador extends CommandAdapter {

    private final long nAluno;
    private String antigoOrientador;


    public RemoveOrientador(ApoioPoeManager apoioPoeManager, long nAluno) {

        super(apoioPoeManager);
        this.nAluno = nAluno;

        for(var propostasAtribuidas : apoioPoeManager.getPropostasAtribuidas())
            if(propostasAtribuidas.getnAlunoAssociado() == nAluno)
                this.antigoOrientador = propostasAtribuidas.getEmailDocenteOrientador();
    }

    @Override
    public boolean execute() {

        String idPropostaAtribuida = null;

        for(var propostasAtribuidas : apoioPoeManager.getPropostasAtribuidas())
            if(propostasAtribuidas.getnAlunoAssociado() == nAluno)
                idPropostaAtribuida = propostasAtribuidas.getId();

        if(idPropostaAtribuida == null)
            return false;

        return apoioPoeManager.removeOrientadorPropostaAtribuida(idPropostaAtribuida);
    }

    @Override
    public boolean undo() {
        String idPropostaAtribuida = null;

        for(var propostasAtribuidas : apoioPoeManager.getPropostasAtribuidas())
            if(propostasAtribuidas.getnAlunoAssociado() == nAluno)
                idPropostaAtribuida = propostasAtribuidas.getId();

        if(idPropostaAtribuida == null)
            return false;

        return apoioPoeManager.atribuirPropostaDocenteOrientador(idPropostaAtribuida, antigoOrientador);
    }
}
