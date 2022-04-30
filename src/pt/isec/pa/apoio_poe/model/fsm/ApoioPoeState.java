package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.ApoioPoEManager;
import pt.isec.pa.apoio_poe.model.fsm.concreteStates.*;

public enum ApoioPoeState {

    INICIO,
    FASE1, FASE1_BLOQUEADA, GESTAO_ALUNOS, GESTAO_DOCENTES, GESTAO_PROPOSTAS,
    FASE2, FASE2_BLOQUEADA, GESTAO_CANDIDATURAS,
    FASE3, Fase3MasFase2AbertaState, FASE3_BLOQUEADA, GESTAO_MANUAL_ATRIBUICOES, Fase3AtribuicaoAutomatica,
    FASE4, GESTAO_MANUAL_ORIENTADORES,
    FASE5;

    public IApoioPoeState createState(ApoioPoeContext context, ApoioPoEManager data){
        return switch (this){
            case INICIO -> new InicioState(context, data);
            case FASE1 -> new Fase1State(context, data);
            case FASE1_BLOQUEADA -> new Fase1BloqueadaState(context, data);
            case GESTAO_ALUNOS -> new GestaoAlunosState(context, data);
            case GESTAO_DOCENTES -> new GestaoDocentesState(context, data);
            case GESTAO_PROPOSTAS -> new GestaoPropostasState(context, data);
            case FASE2 -> new Fase2State(context, data);
            case FASE2_BLOQUEADA -> new Fase2BloqueadaState(context, data);
            case GESTAO_CANDIDATURAS -> new GestaoCandidaturasState(context, data);
            case FASE3 -> new Fase3State(context, data);
            case Fase3MasFase2AbertaState -> new Fase3MasFase2AbertaState(context, data);
            case FASE3_BLOQUEADA -> new Fase3BloqueadaState(context, data);
            case GESTAO_MANUAL_ATRIBUICOES -> new GestaoManualAtribuicoesState(context, data);
            case Fase3AtribuicaoAutomatica -> new Fase3AtribuicaoAutomaticaState(context, data);
            case FASE4 -> new Fase4State(context, data);
            case GESTAO_MANUAL_ORIENTADORES -> new GestaoManualOrientadoresState(context, data);
            case FASE5 -> new Fase5State(context, data);
        };
    }
}
