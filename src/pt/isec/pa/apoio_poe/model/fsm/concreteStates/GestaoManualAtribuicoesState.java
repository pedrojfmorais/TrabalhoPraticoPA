package pt.isec.pa.apoio_poe.model.fsm.concreteStates;

import pt.isec.pa.apoio_poe.model.data.ApoioPoEManager;
import pt.isec.pa.apoio_poe.model.data.propostas.Autoproposto;
import pt.isec.pa.apoio_poe.model.data.propostas.Projeto;
import pt.isec.pa.apoio_poe.model.data.propostas.Proposta;
import pt.isec.pa.apoio_poe.model.data.propostas.PropostaAtribuida;
import pt.isec.pa.apoio_poe.model.facade.GestaoManualAtribuicoesManager;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEContext;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEState;

public class GestaoManualAtribuicoesState extends ApoioPoEAdapter {

    GestaoManualAtribuicoesManager facade;
    public GestaoManualAtribuicoesState(ApoioPoEContext context, ApoioPoEManager data) {
        super(context, data);
        this.facade = new GestaoManualAtribuicoesManager(data);
    }

    @Override
    public boolean regressarFase() {
        changeState(ApoioPoEState.FASE3);
        return true;
    }

    @Override
    public boolean adicionarDados(String... dados) {
        if(dados.length != 2)
            return false;

        return facade.addAtribuicaoAlunoProposta(Long.parseLong(dados[1]), dados[0]);
    }

    @Override
    public boolean removerDados(String... dados) {
        if(dados.length != 1)
            return false;

        Proposta proposta = data.getProposta(dados[0]);

        if(proposta == null)
            return false;

        if(proposta instanceof Autoproposto)
            return false;

        else if (proposta instanceof Projeto p)
            if(p.getnAlunoAssociado() != 0)
                return false;

        return facade.removeAtribuicaoAlunoProposta(dados[0]);
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

        if(!filtro.isBlank()) {

            PropostaAtribuida propostaAtribuida = data.getPropostaAtribuida(filtro);

            return propostaAtribuida == null ? null : propostaAtribuida.toString();
        }

        StringBuilder sb = new StringBuilder();

        for(var propostaAtribuida : data.getPropostasAtribuidas())
            sb.append(propostaAtribuida).append(System.lineSeparator());

        return sb.toString().isBlank() ? null : sb.toString();
    }

    @Override
    public boolean removerTodosDados() {

        while(data.getPropostasAtribuidas().size() > 0)
            data.removePropostaAtribuida(data.getPropostasAtribuidas().get(0).getId());

        return data.getPropostasAtribuidas().size() == 0;
    }

    @Override
    public ApoioPoEState getState() {
        return ApoioPoEState.GESTAO_MANUAL_ATRIBUICOES;
    }
}
