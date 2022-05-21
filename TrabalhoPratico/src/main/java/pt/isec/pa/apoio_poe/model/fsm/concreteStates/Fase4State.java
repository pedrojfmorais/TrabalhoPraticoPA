package pt.isec.pa.apoio_poe.model.fsm.concreteStates;

import pt.isec.pa.apoio_poe.model.data.ApoioPoEManager;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEContext;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEState;

public class Fase4State extends ApoioPoEAdapter {
    public Fase4State(ApoioPoEContext context, ApoioPoEManager data) {
        super(context, data);
    }

    @Override
    public boolean gerirDados() {
        changeState(ApoioPoEState.GESTAO_MANUAL_ORIENTADORES);
        return true;
    }

    @Override
    public boolean avancarFase(boolean bloquearFase) {

        data.setFaseBloqueada(4);

        changeState(ApoioPoEState.FASE5);
        return true;
    }

    @Override
    public boolean regressarFase() {

        if(data.getFaseBloqueada() < 2)
            changeState(ApoioPoEState.Fase3MasFase2AbertaState);

        else if(data.getFaseBloqueada() == 2)
            changeState(ApoioPoEState.FASE3);

        else if(data.getFaseBloqueada() == 3)
            changeState(ApoioPoEState.FASE3_BLOQUEADA);

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
    public boolean associacaoAutomaticaDocentesProponentes() {
        return data.associacaoAutomaticaDocentesProponentes();
    }

    @Override
    public String consultarAlunos(boolean... filtros) {

        if(filtros.length != 1)
            return null;

        return data.consultarAlunos(filtros[0]);
    }

    @Override
    public String consultarDocentes(String filtro) {
        return data.consultarDocentes(filtro);
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
        return ApoioPoEState.FASE4;
    }
}
