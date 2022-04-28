package pt.isec.pa.apoio_poe.model.fsm.concreteStates;

import pt.isec.pa.apoio_poe.model.data.ApoioPoeManager;
import pt.isec.pa.apoio_poe.model.data.propostas.PropostaAtribuida;
import pt.isec.pa.apoio_poe.model.facade.GestaoManualOrientadoresManager;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoeContext;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoeState;

import java.util.ArrayList;

public class GestaoManualOrientadoresState extends ApoioPoeAdapter{

    GestaoManualOrientadoresManager facade;
    public GestaoManualOrientadoresState(ApoioPoeContext context, ApoioPoeManager data) {
        super(context, data);
        facade = new GestaoManualOrientadoresManager(data);
    }

    @Override
    public boolean regressarFase() {
        changeState(ApoioPoeState.FASE4);
        return true;
    }

    @Override
    public boolean adicionarDados(String... dados) {
        if(dados.length != 2)
            return false;

        if(data.getAluno(Long.parseLong(dados[0])) == null)
            return false;

        for(var propostasAtribuidas : data.getPropostasAtribuidas())
            if(propostasAtribuidas.getnAlunoAssociado() == Long.parseLong(dados[0]) &&
                propostasAtribuidas.getEmailDocenteOrientador() != null)
                return false;

        return facade.addOrientador(Long.parseLong(dados[0]), dados[1]);
    }

    @Override
    public boolean editarDados(String... dados) {
        if(dados.length != 2)
            return false;

        if(data.getAluno(Long.parseLong(dados[0])) == null)
            return false;

        for(var propostasAtribuidas : data.getPropostasAtribuidas())
            if(propostasAtribuidas.getnAlunoAssociado() == Long.parseLong(dados[0]) &&
                    propostasAtribuidas.getEmailDocenteOrientador() == null)
                return false;

        return facade.changeOrientador(Long.parseLong(dados[0]), dados[1]);
    }
    @Override
    public boolean removerDados(String... dados) {
        if(dados.length != 1)
            return false;

        if(data.getAluno(Long.parseLong(dados[0])) == null)
            return false;

        for(var propostasAtribuidas : data.getPropostasAtribuidas())
            if(propostasAtribuidas.getnAlunoAssociado() == Long.parseLong(dados[0]) &&
                    propostasAtribuidas.getEmailDocenteOrientador() == null)
                return false;

        return facade.removeOrientador(Long.parseLong(dados[0]));
    }

    @Override
    public boolean undo() {
        return facade.undo();
    }
    @Override
    public boolean hasUndo() {
        return facade.hasUndo();
    }

    @Override
    public boolean redo() {
        return facade.redo();
    }
    @Override
    public boolean hasRedo() {
        return facade.hasRedo();
    }

    @Override
    public String consultarDados(String filtro) {

        ArrayList<PropostaAtribuida> propostaAtribuidasFinal = new ArrayList<>();

        if(!filtro.isBlank()) {
            String idPropostaAtribuida = null;

            for(var propostasAtribuidas : data.getPropostasAtribuidas()) {

                if(propostasAtribuidas.getEmailDocenteOrientador() == null)
                    continue;

                if (propostasAtribuidas.getEmailDocenteOrientador().equalsIgnoreCase(filtro))
                    propostaAtribuidasFinal.add(propostasAtribuidas);
            }

            if(propostaAtribuidasFinal.isEmpty())
                return null;
        }else
            propostaAtribuidasFinal = data.getPropostasAtribuidas();

        StringBuilder sb = new StringBuilder();

        for(var propostaAtribuida : propostaAtribuidasFinal)
            sb.append(propostaAtribuida).append(System.lineSeparator());

        return sb.toString().isBlank() ? null : sb.toString();
    }

    @Override
    public ApoioPoeState getState() {
        return ApoioPoeState.GESTAO_MANUAL_ORIENTADORES;
    }
}
