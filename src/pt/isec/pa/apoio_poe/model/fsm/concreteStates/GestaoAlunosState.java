package pt.isec.pa.apoio_poe.model.fsm.concreteStates;

import pt.isec.pa.apoio_poe.model.data.ApoioPoEManager;
import pt.isec.pa.apoio_poe.model.data.pessoas.alunos.Aluno;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEContext;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEState;

public class GestaoAlunosState extends ApoioPoEAdapter {
    public GestaoAlunosState(ApoioPoEContext context, ApoioPoEManager data) {
        super(context, data);
    }

    @Override
    public boolean regressarFase() {
        changeState(ApoioPoEState.FASE1);
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
            Aluno aluno = data.getAluno(Long.parseLong(filtro));

            return aluno == null ? null : aluno.toString();
        }

        StringBuilder sb = new StringBuilder();

        for(var aluno : data.getAlunos())
            sb.append(aluno).append(System.lineSeparator());

        return sb.toString().isBlank() ? null : sb.toString();
    }

    @Override
    public boolean importarDadosFicheiroCsv(String filename) {
        return data.adicionaAlunosDeFicheiro(filename);
    }

    @Override
    public boolean exportarDadosFicheiroCsv(String filename) {
        return data.exportAlunosCsv(filename);
    }

    @Override
    public boolean removerTodosDados() {

        while(data.getAlunos().size() > 0)
            data.removeAluno(data.getAlunos().get(0).getnAluno());

        return data.getAlunos().size() == 0;
    }

    @Override
    public ApoioPoEState getState() {
        return ApoioPoEState.GESTAO_ALUNOS;
    }
}
