package pt.isec.pa.apoio_poe.model.fsm.concreteStates;

import pt.isec.pa.apoio_poe.model.data.ApoioPoEManager;
import pt.isec.pa.apoio_poe.model.data.pessoas.alunos.Aluno;
import pt.isec.pa.apoio_poe.model.data.propostas.Autoproposto;
import pt.isec.pa.apoio_poe.model.data.propostas.Projeto;
import pt.isec.pa.apoio_poe.model.data.propostas.Proposta;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoeContext;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoeState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class Fase3State extends ApoioPoeAdapter{
    public Fase3State(ApoioPoeContext context, ApoioPoEManager data) {
        super(context, data);
    }

    @Override
    public boolean gerirDados() {
        changeState(ApoioPoeState.GESTAO_MANUAL_ATRIBUICOES);
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

        changeState(ApoioPoeState.FASE4);
        return true;
    }

    @Override
    public boolean regressarFase() {

        changeState(ApoioPoeState.FASE2_BLOQUEADA);
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
        return data.atribuicaoAutomaticaPropostasComAluno();
    }

    @Override
    public boolean atribuicaoAutomaticaPropostasDisponiveis() {
        changeState(ApoioPoeState.Fase3AtribuicaoAutomatica);
        return true;
    }

    @Override
    public String consultarAlunos(boolean... filtros) {

        if(filtros.length != 4)
            return null;

        boolean autoproposta = filtros[0];
        boolean comCandidatura = filtros[1];
        boolean comPropostaAtribuida = filtros[2];
        boolean semPropostaAtribuida = filtros[3];

        HashSet<Aluno> resultado = new HashSet<>();
        HashSet<Aluno> resultadoComPropostaAtribuida = new HashSet<>();
        StringBuilder sb = new StringBuilder();

        if(!autoproposta && !comCandidatura && !comPropostaAtribuida && !semPropostaAtribuida)
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

        if(comPropostaAtribuida) {
            for (var propostasAtribuidas : data.getPropostasAtribuidas()) {
                resultado.add(data.getAluno(propostasAtribuidas.getnAlunoAssociado()));
                resultadoComPropostaAtribuida.add(data.getAluno(propostasAtribuidas.getnAlunoAssociado()));
            }
        }

        if(semPropostaAtribuida){
            HashSet<Long> alunosComProposta = new HashSet<>();

            for(var propostasAtribuidas : data.getPropostasAtribuidas())
                alunosComProposta.add(propostasAtribuidas.getnAlunoAssociado());

            for(var aluno : data.getAlunos())
                if(!alunosComProposta.contains(aluno.getnAluno()))
                    resultado.add(aluno);
        }

        ArrayList<Aluno> resultadoOrdenado = new ArrayList<>(resultado);
        Collections.sort(resultadoOrdenado);

        for(var aluno : resultadoOrdenado) {

            if(resultadoComPropostaAtribuida.contains(aluno)) {

                sb.append("Ordem da preferência: ");

                for(var propostaAtrib : data.getPropostasAtribuidas())
                    if(propostaAtrib.getnAlunoAssociado() == aluno.getnAluno())
                        sb.append(propostaAtrib.getOrdemPreferencia()).append(System.lineSeparator());
            }

            sb.append(aluno).append(System.lineSeparator());
        }

        return sb.toString();
    }

    @Override
    public String consultarPropostas(boolean... filtros) {
        if(filtros.length != 4)
            return null;

        boolean autopropostasAlunos = filtros[0];
        boolean propostasDocentes = filtros[1];
        boolean propostasDisponiveis = filtros[2];
        boolean propostasAtribuidas = filtros[3];

        HashSet<Proposta> resultado = new HashSet<>();
        StringBuilder sb = new StringBuilder();

        if(!autopropostasAlunos && !propostasDocentes && !propostasDisponiveis && !propostasAtribuidas)
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

        if(propostasDisponiveis){

            HashSet<Proposta> propostasAtribuidaHS = new HashSet<>();

            for(var proposta : data.getPropostas())
                for(var propostasAtribuida : data.getPropostasAtribuidas())
                    if(propostasAtribuida.getId().equals(proposta.getId())) {
                        propostasAtribuidaHS.add(proposta);
                        break;
                    }

            for(var proposta : data.getPropostas())
                if(!propostasAtribuidaHS.contains(proposta))
                    resultado.add(proposta);

        }

        if(propostasAtribuidas){
            for(var proposta : data.getPropostas())
                for(var propostasAtribuida : data.getPropostasAtribuidas())
                    if(propostasAtribuida.getId().equals(proposta.getId())) {
                        resultado.add(proposta);
                        break;
                    }
        }

        ArrayList<Proposta> resultadoOrdenado = new ArrayList<>(resultado);
        Collections.sort(resultadoOrdenado);

        for(var proposta : resultadoOrdenado)
            sb.append(proposta).append(System.lineSeparator());

        return sb.toString();
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
        return ApoioPoeState.FASE3;
    }
}
