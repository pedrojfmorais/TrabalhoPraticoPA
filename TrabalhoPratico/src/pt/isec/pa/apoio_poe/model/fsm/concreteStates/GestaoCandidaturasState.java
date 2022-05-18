package pt.isec.pa.apoio_poe.model.fsm.concreteStates;

import pt.isec.pa.apoio_poe.model.data.ApoioPoEManager;
import pt.isec.pa.apoio_poe.model.data.Candidatura;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEContext;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEState;
import pt.isec.pa.apoio_poe.model.memento.CareTaker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GestaoCandidaturasState extends ApoioPoEAdapter {

    CareTaker careTaker;
    public GestaoCandidaturasState(ApoioPoEContext context, ApoioPoEManager data) {
        super(context, data);
        careTaker = new CareTaker(data);
    }

    @Override
    public boolean regressarFase() {
        changeState(ApoioPoEState.FASE2);
        return true;
    }

    @Override
    public boolean adicionarDados(String... dados) {
        if(dados.length <= 1)
            return false;

        careTaker.save();

        if(data.adicionaCandidatura(Long.parseLong(dados[0]), new ArrayList<>(List.of(Arrays.copyOfRange(dados, 1, dados.length)))))
            return true;

        careTaker.removeLastSave();
        return false;

    }
    @Override
    public boolean editarDados(String... dados) {

        if(dados.length <= 1)
            return false;

        careTaker.save();

        if(data.editaCandidatura(Long.parseLong(dados[0]), new ArrayList<>(List.of(Arrays.copyOfRange(dados, 1, dados.length)))))
            return true;

        careTaker.removeLastSave();

        return false;
    }
    @Override
    public boolean removerDados(String... dados) {

        if(dados.length != 1)
            return false;

        careTaker.save();

        if(data.removeCandidatura(Long.parseLong(dados[0])))
            return true;

        careTaker.removeLastSave();
        return false;
    }

    @Override
    public String consultarDados(String filtro) {

        if(!filtro.isBlank()) {
            Candidatura candidatura = data.getCandidatura(Long.parseLong(filtro));

            return candidatura == null ? null : candidatura.toString();
        }

        StringBuilder sb = new StringBuilder();

        for(var candidatura : data.getCandidaturas())
            sb.append(candidatura).append(System.lineSeparator());

        return sb.toString().isBlank() ? null : sb.toString();
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
    public boolean importarDadosFicheiroCsv(String filename) {

        careTaker.save();

        return data.adicionaCandidaturaDeFicheiro(filename);
    }

    @Override
    public boolean exportarDadosFicheiroCsv(String filename) {
        return data.exportCandidaturasCsv(filename);
    }

    @Override
    public boolean removerTodosDados() {

        careTaker.save();

        while(data.getCandidaturas().size() > 0)
            data.removeCandidatura(data.getCandidaturas().get(0).getnAluno());

        return data.getCandidaturas().size() == 0;
    }

    @Override
    public ApoioPoEState getState() {
        return ApoioPoEState.GESTAO_CANDIDATURAS;
    }
}
