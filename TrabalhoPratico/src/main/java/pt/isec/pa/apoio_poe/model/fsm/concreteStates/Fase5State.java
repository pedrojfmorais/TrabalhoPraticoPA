package pt.isec.pa.apoio_poe.model.fsm.concreteStates;

import pt.isec.pa.apoio_poe.model.data.ApoioPoEManager;
import pt.isec.pa.apoio_poe.model.data.pessoas.alunos.Aluno;
import pt.isec.pa.apoio_poe.model.data.propostas.Proposta;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEContext;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEState;

import java.util.ArrayList;

public class Fase5State extends ApoioPoEAdapter {
    public Fase5State(ApoioPoEContext context, ApoioPoEManager data) {
        super(context, data);
    }

    @Override
    public boolean terminarAplicacao(String file) {
        if(!file.isBlank())
            context.saveStateInFile(file, context.getState());

        changeState(ApoioPoEState.INICIO);
        return true;
    }

    @Override
    public ArrayList<Aluno> consultarAlunos(boolean... filtros) {

        if(filtros.length != 1)
            return null;

        return data.consultarAlunosFase5(filtros[0]);
    }

    @Override
    public ArrayList<Proposta> consultarPropostas(boolean... filtros) {

        if(filtros.length != 1)
            return null;

        return data.consultarPropostas(filtros[0]);
    }

    @Override
    public String consultarDocentes(String filtro) {
        return ApoioPoEState.FASE4.createState(context, data).consultarDocentes(filtro);
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
        return ApoioPoEState.FASE5;
    }
}
