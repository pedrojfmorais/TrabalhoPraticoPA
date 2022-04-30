package pt.isec.pa.apoio_poe.model.fsm.concreteStates;

import pt.isec.pa.apoio_poe.model.data.ApoioPoEManager;
import pt.isec.pa.apoio_poe.model.data.propostas.Proposta;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoeContext;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoeState;

public class GestaoPropostasState extends ApoioPoeAdapter{
    public GestaoPropostasState(ApoioPoeContext context, ApoioPoEManager data) {
        super(context, data);
    }

    @Override
    public boolean regressarFase() {
        changeState(ApoioPoeState.FASE1);
        return true;
    }

    //TODO: A implemetar com o memento
    @Override
    public boolean adicionarDados(String... dados) {
        return super.adicionarDados(dados);
    }
    @Override
    public boolean editarDados(String... dados) {
        return super.editarDados(dados);
    }
    @Override
    public boolean removerDados(String... dados) {
        return super.removerDados(dados);
    }

    @Override
    public String consultarDados(String filtro) {

        if(!filtro.isBlank()) {
            Proposta proposta = data.getProposta(filtro);

            return proposta == null ? null : proposta.toString();
        }

        StringBuilder sb = new StringBuilder();

        for(var proposta : data.getPropostas())
            sb.append(proposta).append(System.lineSeparator());

        return sb.toString().isBlank() ? null : sb.toString();
    }

    @Override
    public boolean importarDadosFicheiroCsv(String filename) {
        return data.adicionaPropostasDeFicheiro(filename);
    }

    @Override
    public boolean exportarDadosFicheiroCsv(String filename) {
        return data.exportPropostasCsv(filename);
    }

    @Override
    public boolean removerTodosDados() {

        while(data.getPropostas().size() > 0)
            data.removeProposta(data.getPropostas().get(0).getId());

        return data.getPropostas().size() == 0;
    }

    @Override
    public ApoioPoeState getState() {
        return ApoioPoeState.GESTAO_PROPOSTAS;
    }
}