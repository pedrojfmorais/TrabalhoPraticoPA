package pt.isec.pa.apoio_poe.model.fsm.concreteStates;

import pt.isec.pa.apoio_poe.model.data.ApoioPoeManager;
import pt.isec.pa.apoio_poe.model.data.pessoas.alunos.Aluno;
import pt.isec.pa.apoio_poe.model.data.propostas.Autoproposto;
import pt.isec.pa.apoio_poe.model.data.propostas.Projeto;
import pt.isec.pa.apoio_poe.model.data.propostas.Proposta;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoeContext;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoeState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class Fase2State extends ApoioPoeAdapter{
    public Fase2State(ApoioPoeContext context, ApoioPoeManager data) {
        super(context, data);
    }

    @Override
    public boolean gerirDados() {
        changeState(ApoioPoeState.GESTAO_CANDIDATURAS);
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
            changeState(ApoioPoeState.FASE3);
        else if(data.getFaseBloqueada() < 2)
            changeState(ApoioPoeState.Fase3MasFase2AbertaState);
        return true;
    }

    @Override
    public boolean regressarFase() {
        if(data.getFaseBloqueada() == 1)
            changeState(ApoioPoeState.FASE1_BLOQUEADA);
        else
            changeState(ApoioPoeState.FASE1);
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
    public String consultarAlunos(boolean ... filtros) {

        if(filtros.length != 3)
            return null;

        boolean autoproposta = filtros[0];
        boolean comCandidatura = filtros[1];
        boolean semCandidatura = filtros[2];

        HashSet<Aluno> resultado = new HashSet<>();
        StringBuilder sb = new StringBuilder();

        if(!autoproposta && !comCandidatura && !semCandidatura)
            resultado = new HashSet<>(data.getAlunos());

        if(autoproposta){
            for (var proposta : data.getPropostas())
                if(proposta instanceof Autoproposto)
                    resultado.add(data.getAluno(proposta.getnAlunoAssociado()));
        }

        if(comCandidatura){
            for (var candidatura : data.getCandidaturas())
                resultado.add(data.getAluno(candidatura.getnAluno()));
        }

        if(semCandidatura){
            HashSet<Long> alunosComCandidatura = new HashSet<>();

            for(var candidatura : data.getCandidaturas())
                alunosComCandidatura.add(candidatura.getnAluno());

            for(var aluno : data.getAlunos())
                if(!alunosComCandidatura.contains(aluno.getnAluno()))
                    resultado.add(aluno);
        }

        ArrayList<Aluno> resultadoOrdenado = new ArrayList<>(resultado);
        Collections.sort(resultadoOrdenado);

        for(var aluno : resultadoOrdenado)
            sb.append(aluno).append(System.lineSeparator());

        return sb.toString();
    }

    @Override
    public String consultarPropostas(boolean ... filtros) {

        if(filtros.length != 4)
            return null;

        boolean autopropostasAlunos = filtros[0];
        boolean propostasDocentes = filtros[1];
        boolean comCandidatura = filtros[2];
        boolean semCandidatura = filtros[3];

        HashSet<Proposta> resultado = new HashSet<>();
        StringBuilder sb = new StringBuilder();

        if(!autopropostasAlunos && !propostasDocentes && !comCandidatura && !semCandidatura)
            resultado = new HashSet<>(data.getPropostas());

        if(autopropostasAlunos){
            for(var proposta : data.getPropostas())
                if(proposta instanceof Autoproposto)
                    resultado.add(proposta);
        }

        if(propostasDocentes){
            for(var proposta : data.getPropostas())
                if(proposta instanceof Projeto)
                    resultado.add(proposta);
        }

        if(comCandidatura){
            for(var proposta : data.getPropostas())
                for(var candidatura : data.getCandidaturas())
                    if (candidatura.getIdPropostas().contains(proposta.getId())) {
                        resultado.add(proposta);
                        break;
                    }
        }

        if(semCandidatura){
            HashSet<String> propostasComCandidatura = new HashSet<>();

            for(var proposta : data.getPropostas())
                for(var candidatura : data.getCandidaturas())
                    if (candidatura.getIdPropostas().contains(proposta.getId())) {
                        propostasComCandidatura.add(proposta.getId());
                        break;
                    }

            for (var proposta : data.getPropostas())
                if(!propostasComCandidatura.contains(proposta.getId()))
                    resultado.add(proposta);
        }

        ArrayList<Proposta> resultadoOrdenado = new ArrayList<>(resultado);
        Collections.sort(resultadoOrdenado);

        for(var proposta : resultadoOrdenado)
            sb.append(proposta).append(System.lineSeparator());

        return sb.toString();
    }

    @Override
    public ApoioPoeState getState() {
        return ApoioPoeState.FASE2;
    }
}
