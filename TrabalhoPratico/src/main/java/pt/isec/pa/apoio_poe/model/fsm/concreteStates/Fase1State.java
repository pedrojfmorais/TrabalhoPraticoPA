package pt.isec.pa.apoio_poe.model.fsm.concreteStates;

import pt.isec.pa.apoio_poe.model.data.ApoioPoEManager;
import pt.isec.pa.apoio_poe.model.data.pessoas.alunos.Aluno;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEContext;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEState;

public class Fase1State extends ApoioPoEAdapter {

    public Fase1State(ApoioPoEContext context, ApoioPoEManager data) {
        super(context, data);
    }

    @Override
    public boolean gerirAlunos() {
        changeState(ApoioPoEState.GESTAO_ALUNOS);
        return true;
    }

    @Override
    public boolean gerirDocentes() {
        changeState(ApoioPoEState.GESTAO_DOCENTES);
        return true;
    }

    @Override
    public boolean gerirPropostas() {
        changeState(ApoioPoEState.GESTAO_PROPOSTAS);
        return true;
    }

    @Override
    public boolean avancarFase(boolean bloquearFase) {

        if(bloquearFase) {
            int contador = 0;
            for(var ramo : Aluno.ramos)
                if(data.propostasSufecienteParaRamo(ramo))
                    contador++;

            if(contador == Aluno.ramos.size())
                data.setFaseBloqueada(1);
            else
                return false;
        }

        changeState(ApoioPoEState.FASE2);
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
    public ApoioPoEState getState() {
        return ApoioPoEState.FASE1;
    }
}
