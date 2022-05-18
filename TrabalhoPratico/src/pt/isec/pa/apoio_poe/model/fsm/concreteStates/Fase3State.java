package pt.isec.pa.apoio_poe.model.fsm.concreteStates;

import pt.isec.pa.apoio_poe.model.data.ApoioPoEManager;
import pt.isec.pa.apoio_poe.model.data.pessoas.alunos.Aluno;
import pt.isec.pa.apoio_poe.model.data.propostas.Autoproposto;
import pt.isec.pa.apoio_poe.model.data.propostas.Projeto;
import pt.isec.pa.apoio_poe.model.data.propostas.Proposta;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEContext;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class Fase3State extends ApoioPoEAdapter {
    public Fase3State(ApoioPoEContext context, ApoioPoEManager data) {
        super(context, data);
    }

    @Override
    public boolean gerirDados() {
        changeState(ApoioPoEState.GESTAO_MANUAL_ATRIBUICOES);
        return true;
    }

    @Override
    public boolean avancarFase(boolean bloquearFase) {

        if(bloquearFase) {
            if(data.todasCandidaturasComPropostaAtribuida())
                data.setFaseBloqueada(3);
            else
                return false;
        }

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
    public boolean atribuicaoAutomaticaPropostasComAluno() {
        return data.atribuicaoAutomaticaPropostasComAluno();
    }

    @Override
    public boolean atribuicaoAutomaticaPropostasDisponiveis() {
        changeState(ApoioPoEState.Fase3AtribuicaoAutomatica);
        return true;
    }

    @Override
    public String consultarAlunos(boolean... filtros) {

        if(filtros.length != 4)
            return null;

        return data.consultarAlunos(filtros[0], filtros[1], filtros[2], filtros[3]);
    }

    @Override
    public String consultarPropostas(boolean... filtros) {
        if(filtros.length != 4)
            return null;

        return data.consultarPropostasFase3(filtros[0], filtros[1], filtros[2], filtros[3]);
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
        return ApoioPoEState.FASE3;
    }
}
