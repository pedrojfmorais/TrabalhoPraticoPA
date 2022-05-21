package pt.isec.pa.apoio_poe.model.fsm.concreteStates;

import pt.isec.pa.apoio_poe.model.data.ApoioPoEManager;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEContext;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEState;

public class Fase3BloqueadaState extends ApoioPoEAdapter {
    public Fase3BloqueadaState(ApoioPoEContext context, ApoioPoEManager data) {
        super(context, data);
    }

    @Override
    public boolean avancarFase(boolean bloquearFase) {
        changeState(ApoioPoEState.FASE4);
        return true;
    }

    @Override
    public boolean regressarFase() {
        changeState(ApoioPoEState.FASE2_BLOQUEADA);
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
    public String consultarAlunos(boolean... filtros) {
        return ApoioPoEState.FASE3.createState(context, data).consultarAlunos(filtros);
    }

    @Override
    public String consultarPropostas(boolean... filtros) {
        return ApoioPoEState.FASE3.createState(context, data).consultarPropostas(filtros);
    }

    @Override
    public boolean exportarAlunosFicheiroCsv(String filename) {
        return data.exportAlunosCsv(filename);
    }
    @Override
    public boolean exportarDocentesFicheiroCsv(String filename) {
        return data.exportDocentesCsv(filename);
    }
    @Override
    public boolean exportarPropostasFicheiroCsv(String filename) {
        return data.exportPropostasCsv(filename);
    }
    @Override
    public boolean exportarCandidaturasFicheiroCsv(String filename) {
        return data.exportCandidaturasCsv(filename);
    }
    @Override
    public boolean exportarPropostasAtribuidasFicheiroCsv(String filename, boolean guardarOrientador) {
        return data.exportPropostasAtribuidasCsv(filename, guardarOrientador);
    }


    @Override
    public ApoioPoEState getState() {
        return ApoioPoEState.FASE3_BLOQUEADA;
    }
}
