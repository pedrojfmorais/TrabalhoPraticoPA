package pt.isec.pa.apoio_poe.model.fsm.concreteStates;

import pt.isec.pa.apoio_poe.model.data.ApoioPoEManager;
import pt.isec.pa.apoio_poe.model.data.Candidatura;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoeContext;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoeState;

public class GestaoCandidaturasState extends ApoioPoeAdapter{
    public GestaoCandidaturasState(ApoioPoeContext context, ApoioPoEManager data) {
        super(context, data);
    }

    @Override
    public boolean regressarFase() {
        changeState(ApoioPoeState.FASE2);
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
            Candidatura candidatura = data.getCandidatura(Long.parseLong(filtro));

            return candidatura == null ? null : candidatura.toString();
        }

        StringBuilder sb = new StringBuilder();

        for(var candidatura : data.getCandidaturas())
            sb.append(candidatura).append(System.lineSeparator());

        return sb.toString().isBlank() ? null : sb.toString();
    }

    @Override
    public boolean importarDadosFicheiroCsv(String filename) {
        return data.adicionaCandidaturaDeFicheiro(filename);
    }

    @Override
    public boolean exportarDadosFicheiroCsv(String filename) {
        return data.exportCandidaturasCsv(filename);
    }

    @Override
    public boolean removerTodosDados() {

        while(data.getCandidaturas().size() > 0)
            data.removeCandidatura(data.getCandidaturas().get(0).getnAluno());

        return data.getCandidaturas().size() == 0;
    }

    @Override
    public ApoioPoeState getState() {
        return ApoioPoeState.GESTAO_CANDIDATURAS;
    }
}
