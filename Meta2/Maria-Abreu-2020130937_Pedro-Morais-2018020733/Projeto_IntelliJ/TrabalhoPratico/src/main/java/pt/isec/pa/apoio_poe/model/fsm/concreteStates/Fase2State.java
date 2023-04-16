package pt.isec.pa.apoio_poe.model.fsm.concreteStates;

import pt.isec.pa.apoio_poe.model.data.ApoioPoEManager;
import pt.isec.pa.apoio_poe.model.data.pessoas.alunos.Aluno;
import pt.isec.pa.apoio_poe.model.data.propostas.Proposta;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEContext;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEState;

import java.util.ArrayList;

public class Fase2State extends ApoioPoEAdapter {
    public Fase2State(ApoioPoEContext context, ApoioPoEManager data) {
        super(context, data);
    }

    @Override
    public boolean gerirDados() {
        changeState(ApoioPoEState.GESTAO_CANDIDATURAS);
        return true;
    }

    @Override
    public boolean avancarFase(boolean bloquearFase) {

        if(bloquearFase) {
            if (data.getFaseBloqueada() == 1)
                data.setFaseBloqueada(2);
            else
                return false;
        }

        if(data.getFaseBloqueada() == 2)
            changeState(ApoioPoEState.FASE3);
        else if(data.getFaseBloqueada() < 2)
            changeState(ApoioPoEState.FASE3_MAS_FASE2_ABERTA);
        return true;
    }

    @Override
    public boolean regressarFase() {
        if(data.getFaseBloqueada() == 1)
            changeState(ApoioPoEState.FASE1_BLOQUEADA);
        else
            changeState(ApoioPoEState.FASE1);
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
    public ArrayList<Aluno> consultarAlunos(boolean ... filtros) {

        if(filtros.length != 3)
            return null;

        return data.consultarAlunos(filtros[0], filtros[1], filtros[2]);
    }

    @Override
    public ArrayList<Proposta> consultarPropostas(boolean ... filtros) {

        if(filtros.length != 4)
            return null;

        return data.consultarPropostas(filtros[0], filtros[1], filtros[2], filtros[3]);
    }

    @Override
    public ApoioPoEState getState() {
        return ApoioPoEState.FASE2;
    }
}
