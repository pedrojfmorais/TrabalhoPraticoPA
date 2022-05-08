package pt.isec.pa.apoio_poe.model.fsm.concreteStates;

import pt.isec.pa.apoio_poe.model.data.ApoioPoEManager;
import pt.isec.pa.apoio_poe.model.data.pessoas.Docente;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEContext;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEState;
import pt.isec.pa.apoio_poe.model.memento.CareTaker;

public class GestaoDocentesState extends ApoioPoEAdapter {

    CareTaker careTaker;
    public GestaoDocentesState(ApoioPoEContext context, ApoioPoEManager data) {
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

        if(dados.length != 2)
            return false;

        careTaker.save();

        if(data.adicionaDocente(dados[0], dados[1]))
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

        if(data.removeDocente(dados[0]))
            return true;

        careTaker.removeLastSave();
        return false;
    }

    @Override
    public String consultarDados(String filtro) {

        if(!filtro.isBlank()) {
            Docente docente = data.getDocente(filtro);

            return docente == null ? null : docente.toString();
        }

        StringBuilder sb = new StringBuilder();

        for(var docente : data.getDocentes())
            sb.append(docente).append(System.lineSeparator());

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

        return data.adicionaDocentesDeFicheiro(filename);
    }

    @Override
    public boolean exportarDadosFicheiroCsv(String filename) {
        return data.exportDocentesCsv(filename);
    }

    @Override
    public boolean removerTodosDados() {

        careTaker.save();

        while(data.getDocentes().size() > 0)
            data.removeDocente(data.getDocentes().get(0).getEmail());

        return data.getDocentes().size() == 0;
    }

    @Override
    public ApoioPoEState getState() {
        return ApoioPoEState.GESTAO_DOCENTES;
    }
}