package pt.isec.pa.apoio_poe.model.fsm.concreteStates;

import pt.isec.pa.apoio_poe.model.data.ApoioPoEManager;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEContext;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEState;

import java.util.ArrayList;
import java.util.Collections;

public class Fase1BloqueadaState extends ApoioPoEAdapter {

    public Fase1BloqueadaState(ApoioPoEContext context, ApoioPoEManager data) {
        super(context, data);
    }

    @Override
    public boolean avancarFase(boolean bloquearFase) {
        if(data.getFaseBloqueada() < 2)
            changeState(ApoioPoEState.FASE2);
        else
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
    public String consultarAlunos(String filtro) {
        return ApoioPoEState.GESTAO_ALUNOS.createState(context, data).consultarDados(filtro);
    }
    @Override
    public ArrayList<String> consultarDocentes(String filtro) {
        return new ArrayList<>(Collections.singleton(ApoioPoEState.GESTAO_DOCENTES.createState(context, data).consultarDados(filtro)));
    }
    @Override
    public String consultarPropostas(String filtro) {
        return ApoioPoEState.GESTAO_PROPOSTAS.createState(context, data).consultarDados(filtro);
    }

    @Override
    public boolean exportarAlunosFicheiroCsv(String filename) {
        return ApoioPoEState.GESTAO_ALUNOS.createState(context, data).exportarDadosFicheiroCsv(filename);
    }
    @Override
    public boolean exportarDocentesFicheiroCsv(String filename) {
        return ApoioPoEState.GESTAO_DOCENTES.createState(context, data).exportarDadosFicheiroCsv(filename);
    }
    @Override
    public boolean exportarPropostasFicheiroCsv(String filename) {
        return ApoioPoEState.GESTAO_PROPOSTAS.createState(context, data).exportarDadosFicheiroCsv(filename);
    }

    @Override
    public ApoioPoEState getState() {
        return ApoioPoEState.FASE1_BLOQUEADA;
    }
}
