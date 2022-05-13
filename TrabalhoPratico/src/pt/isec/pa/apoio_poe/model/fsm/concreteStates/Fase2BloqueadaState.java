package pt.isec.pa.apoio_poe.model.fsm.concreteStates;

import pt.isec.pa.apoio_poe.model.data.ApoioPoEManager;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEContext;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEState;

public class Fase2BloqueadaState extends ApoioPoEAdapter {

    public Fase2BloqueadaState(ApoioPoEContext context, ApoioPoEManager data) {
        super(context, data);
    }

    @Override
    public boolean avancarFase(boolean bloquearFase) {

        if(data.getFaseBloqueada() < 3)
            changeState(ApoioPoEState.FASE3);
        else
            changeState(ApoioPoEState.FASE3_BLOQUEADA);

        return true;
    }

    @Override
    public boolean regressarFase() {
        changeState(ApoioPoEState.FASE1_BLOQUEADA);
        return true;
    }

    @Override
    public boolean terminarAplicacao(String file) {
        if(!file.isBlank())
            context.saveStateInFile(file, context.getState());

        changeState(ApoioPoEState.INICIO);
        return true;
    }

    @Override
    public String consultarCandidaturas(String filtro) {
        return ApoioPoEState.GESTAO_CANDIDATURAS.createState(context, data).consultarDados(filtro);
    }

    @Override
    public String consultarAlunos(boolean... filtros) {
        return ApoioPoEState.FASE2.createState(context, data).consultarAlunos(filtros);
    }

    @Override
    public String consultarPropostas(boolean... filtros) {
        return ApoioPoEState.FASE2.createState(context, data).consultarPropostas(filtros);
    }

    @Override
    public boolean exportarDadosFicheiroCsv(String filename) {
        return data.exportCandidaturasCsv(filename);
    }

    @Override
    public ApoioPoEState getState() {
        return ApoioPoEState.FASE2_BLOQUEADA;
    }
}
