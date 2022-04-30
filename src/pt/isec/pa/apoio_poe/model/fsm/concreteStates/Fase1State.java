package pt.isec.pa.apoio_poe.model.fsm.concreteStates;

import pt.isec.pa.apoio_poe.model.data.ApoioPoeManager;
import pt.isec.pa.apoio_poe.model.data.pessoas.alunos.Aluno;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoeContext;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoeState;

public class Fase1State extends ApoioPoeAdapter{

    public Fase1State(ApoioPoeContext context, ApoioPoeManager data) {
        super(context, data);
    }

    @Override
    public boolean gerirAlunos() {
        changeState(ApoioPoeState.GESTAO_ALUNOS);
        return true;
    }

    @Override
    public boolean gerirDocentes() {
        changeState(ApoioPoeState.GESTAO_DOCENTES);
        return true;
    }

    @Override
    public boolean gerirPropostas() {
        changeState(ApoioPoeState.GESTAO_PROPOSTAS);
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

        changeState(ApoioPoeState.FASE2);
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
    public ApoioPoeState getState() {
        return ApoioPoeState.FASE1;
    }
}
