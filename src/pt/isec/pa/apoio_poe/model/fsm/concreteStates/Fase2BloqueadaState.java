package pt.isec.pa.apoio_poe.model.fsm.concreteStates;

import pt.isec.pa.apoio_poe.model.data.ApoioPoeManager;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoeContext;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoeState;

public class Fase2BloqueadaState extends ApoioPoeAdapter{

    public Fase2BloqueadaState(ApoioPoeContext context, ApoioPoeManager data) {
        super(context, data);
    }

    @Override
    public boolean avancarFase(boolean bloquearFase) {

        if(data.getFaseBloqueada() < 3)
            changeState(ApoioPoeState.FASE3);
        else
            changeState(ApoioPoeState.FASE3_BLOQUEADA);

        return true;
    }

    @Override
    public boolean regressarFase() {
        changeState(ApoioPoeState.FASE1_BLOQUEADA);
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
    public String consultarCandidaturas(String filtro) {
        return ApoioPoeState.GESTAO_CANDIDATURAS.createState(context, data).consultarDados(filtro);
    }

    @Override
    public String consultarAlunos(boolean... filtros) {
        return ApoioPoeState.FASE2.createState(context, data).consultarAlunos(filtros);
    }

    @Override
    public String consultarPropostas(boolean... filtros) {
        return ApoioPoeState.FASE2.createState(context, data).consultarPropostas(filtros);
    }

    @Override
    public boolean exportarDadosFicheiroCsv(String filename) {
        return data.exportCandidaturasCsv(filename);
    }

    @Override
    public ApoioPoeState getState() {
        return ApoioPoeState.FASE2_BLOQUEADA;
    }
}
