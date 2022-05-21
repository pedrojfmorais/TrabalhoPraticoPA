package pt.isec.pa.apoio_poe.model.fsm.concreteStates;

import pt.isec.pa.apoio_poe.model.data.ApoioPoEManager;
import pt.isec.pa.apoio_poe.model.data.pessoas.alunos.Aluno;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEContext;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEState;
import pt.isec.pa.apoio_poe.model.memento.CareTaker;

public class GestaoAlunosState extends ApoioPoEAdapter {
    CareTaker careTaker;
    public GestaoAlunosState(ApoioPoEContext context, ApoioPoEManager data) {
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

        if(dados.length != 7)
            return false;

        careTaker.save();

        if(data.adicionaAluno(Long.parseLong(dados[0]), dados[1], dados[2], dados[3], dados[4],
                Double.parseDouble(dados[5]), Boolean.parseBoolean(dados[6])))
            return true;

        careTaker.removeLastSave();

        return false;
    }
    @Override
    public boolean editarDados(String... dados) {
        if(dados.length != 6)
            return false;

        careTaker.save();

        if(data.editaAluno(Long.parseLong(dados[0]), dados[1], dados[2], dados[3], dados[4],
                dados[5]))
            return true;

        careTaker.removeLastSave();

        return false;
    }
    @Override
    public boolean removerDados(String... dados) {
        if(dados.length != 1)
            return false;

        careTaker.save();

        if(data.removeAluno(Long.parseLong(dados[0])))
            return true;

        careTaker.removeLastSave();
        return false;

    }

    @Override
    public String consultarDados(String filtro) {

        if(!filtro.isBlank()) {
            Aluno aluno = data.getAluno(Long.parseLong(filtro));

            return aluno == null ? null : aluno.toString();
        }

        StringBuilder sb = new StringBuilder();

        for(var aluno : data.getAlunos())
            sb.append(aluno).append(System.lineSeparator());

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
        return data.adicionaAlunosDeFicheiro(filename);
    }

    @Override
    public boolean exportarDadosFicheiroCsv(String filename) {
        return data.exportAlunosCsv(filename);
    }

    @Override
    public boolean removerTodosDados() {

        if(data.getAlunos().size() == 0)
            return false;

        careTaker.save();

        while(data.getAlunos().size() > 0)
            data.removeAluno(data.getAlunos().get(0).getnAluno());

        return data.getAlunos().size() == 0;
    }

    @Override
    public ApoioPoEState getState() {
        return ApoioPoEState.GESTAO_ALUNOS;
    }
}
