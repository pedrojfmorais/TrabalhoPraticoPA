package pt.isec.pa.apoio_poe.model.fsm.concreteStates;

import pt.isec.pa.apoio_poe.model.data.ApoioPoeManager;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoeContext;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoeState;


public class Fase3MasFase2AbertaState extends ApoioPoeAdapter{
    public Fase3MasFase2AbertaState(ApoioPoeContext context, ApoioPoeManager data) {
        super(context, data);
    }

    @Override
    public boolean regressarFase() {
        changeState(ApoioPoeState.FASE2);
        return true;
    }

    @Override
    public boolean avancarFase(boolean bloquearFase) {
        changeState(ApoioPoeState.FASE4);
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
    public boolean atribuicaoAutomaticaPropostasComAluno() {
        return ApoioPoeState.FASE3.createState(context, data).atribuicaoAutomaticaPropostasComAluno();
    }

    @Override
    public String consultarAlunos(boolean... filtros) {
        return ApoioPoeState.FASE3.createState(context, data).consultarAlunos(filtros);
    }

    @Override
    public String consultarPropostas(boolean... filtros) {
        return ApoioPoeState.FASE3.createState(context, data).consultarPropostas(filtros);
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
    public ApoioPoeState getState() {
        return ApoioPoeState.Fase3MasFase2AbertaState;
    }
}
