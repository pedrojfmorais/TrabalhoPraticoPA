package pt.isec.pa.apoio_poe.model.fsm.concreteStates;

import pt.isec.pa.apoio_poe.model.data.ApoioPOE;
import pt.isec.pa.apoio_poe.model.data.ApoioPoeManager;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoeContext;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoeState;

public class Fase1BloqueadaState extends ApoioPoeAdapter{

    public Fase1BloqueadaState(ApoioPoeContext context, ApoioPoeManager data) {
        super(context, data);
    }

    @Override
    public boolean avancarFase(boolean bloquearFase) {
        if(data.getFaseBloqueada() < 2)
            changeState(ApoioPoeState.FASE2);
        else
            changeState(ApoioPoeState.FASE2_BLOQUEADA);
        return true;
    }

    @Override
    public boolean terminarAplicacao(String file) {
        if(!file.isBlank())
            data.saveStateInFile(file, context.getState());

        changeState(ApoioPoeState.INICIO);
        return true;
    }

    @Override
    public String consultarAlunos(String filtro) {
        return ApoioPoeState.GESTAO_ALUNOS.createState(context, data).consultarDados(filtro);
    }
    @Override
    public String consultarDocentes(String filtro) {
        return ApoioPoeState.GESTAO_DOCENTES.createState(context, data).consultarDados(filtro);
    }
    @Override
    public String consultarPropostas(String filtro) {
        return ApoioPoeState.GESTAO_PROPOSTAS.createState(context, data).consultarDados(filtro);
    }

    @Override
    public boolean exportarAlunosFicheiroCsv(String filename) {
        return ApoioPoeState.GESTAO_ALUNOS.createState(context, data).exportarDadosFicheiroCsv(filename);
    }
    @Override
    public boolean exportarDocentesFicheiroCsv(String filename) {
        return ApoioPoeState.GESTAO_DOCENTES.createState(context, data).exportarDadosFicheiroCsv(filename);
    }
    @Override
    public boolean exportarPropostasFicheiroCsv(String filename) {
        return ApoioPoeState.GESTAO_PROPOSTAS.createState(context, data).exportarDadosFicheiroCsv(filename);
    }

    @Override
    public ApoioPoeState getState() {
        return ApoioPoeState.FASE1_BLOQUEADA;
    }
}
