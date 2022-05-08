package pt.isec.pa.apoio_poe.model.fsm.concreteStates;

import pt.isec.pa.apoio_poe.model.data.ApoioPoEManager;
import pt.isec.pa.apoio_poe.model.data.propostas.Proposta;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEContext;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEState;
import pt.isec.pa.apoio_poe.model.memento.CareTaker;

public class GestaoPropostasState extends ApoioPoEAdapter {

    CareTaker careTaker;

    public GestaoPropostasState(ApoioPoEContext context, ApoioPoEManager data) {
        super(context, data);
        careTaker = new CareTaker(data);
    }

    @Override
    public boolean regressarFase() {
        changeState(ApoioPoEState.FASE1);
        return true;
    }

    @Override
    public boolean adicionarDados(String... dados) {

        if(dados.length < 4 || dados.length > 6)
            return false;

        careTaker.save();
        boolean inseriu;

        if(dados.length == 4)
            inseriu = data.adicionaProposta(dados[0], dados[1], dados[2], Long.parseLong(dados[3]));
        else if (dados.length == 5 || Long.parseLong(dados[5]) == 0)
            inseriu = data.adicionaProposta(dados[0], dados[1], dados[2], dados[3], dados[4]);
        else
            inseriu = data.adicionaProposta(dados[0], dados[1], dados[2], Long.parseLong(dados[5]), dados[3], dados[4]);

        if(inseriu)
            return true;

        careTaker.removeLastSave();

        return false;
    }
    @Override
    public boolean editarDados(String... dados) {

        //TODO: A implemetar com o memento
        return super.editarDados(dados);
    }
    @Override
    public boolean removerDados(String... dados) {

        if(dados.length != 1)
            return false;

        careTaker.save();

        if(data.removeProposta(dados[0]))
            return true;

        careTaker.removeLastSave();
        return false;
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

        careTaker.save();

        return data.adicionaPropostasDeFicheiro(filename);
    }

    @Override
    public boolean exportarDadosFicheiroCsv(String filename) {
        return data.exportPropostasCsv(filename);
    }
    @Override
    public boolean undo() {

        if(!careTaker.hasUndo())
            return false;

        careTaker.undo();
        return true;
    }

    @Override
    public boolean redo() {
        if(!careTaker.hasRedo())
            return false;

        careTaker.redo();
        return true;
    }

    @Override
    public boolean hasUndo() {
        return careTaker.hasUndo();
    }

    @Override
    public boolean hasRedo() {
        return careTaker.hasRedo();
    }

    @Override
    public boolean removerTodosDados() {

        careTaker.save();

        while(data.getPropostas().size() > 0)
            data.removeProposta(data.getPropostas().get(0).getId());

        return data.getPropostas().size() == 0;
    }

    @Override
    public ApoioPoEState getState() {
        return ApoioPoEState.GESTAO_PROPOSTAS;
    }
}