package pt.isec.pa.apoio_poe.model.cmd.gestaoManualAtribuicoes;

import pt.isec.pa.apoio_poe.model.cmd.CommandAdapter;
import pt.isec.pa.apoio_poe.model.data.ApoioPoEManager;

import java.util.ArrayList;

public class RemoveTodasAtribuicoesAlunoProposta extends CommandAdapter {

    private final ArrayList<Long> nAlunos;
    private final ArrayList<String> idPropostas;

    public RemoveTodasAtribuicoesAlunoProposta(ApoioPoEManager apoioPoeManager) {
        super(apoioPoeManager);

        nAlunos = new ArrayList<>();
        idPropostas = new ArrayList<>();

        for(var propostaAtribuida : apoioPoeManager.getPropostasAtribuidas()) {
            idPropostas.add(propostaAtribuida.getId());
            nAlunos.add(propostaAtribuida.getNAlunoAssociado());
        }
    }

    @Override
    public boolean execute() {

        for(var idProposta : idPropostas)
            apoioPoeManager.removePropostaAtribuida(idProposta);

        return apoioPoeManager.getPropostasAtribuidas().size() == 0;
    }

    @Override
    public boolean undo() {

        for (int i = 0; i < idPropostas.size(); i++)
            apoioPoeManager.atribuirPropostaAluno(idPropostas.get(i), nAlunos.get(i));

        return apoioPoeManager.getPropostasAtribuidas().size() != 0;
    }
}
