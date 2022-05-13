package pt.isec.pa.apoio_poe.model.fsm.concreteStates;

import pt.isec.pa.apoio_poe.model.data.ApoioPoEManager;
import pt.isec.pa.apoio_poe.model.data.pessoas.alunos.Aluno;
import pt.isec.pa.apoio_poe.model.data.propostas.Proposta;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEContext;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

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
    public String consultarAlunos(boolean... filtros) {

        if(filtros.length != 1)
            return null;

        boolean comPropostaAtribuida = filtros[0];

        HashSet<Aluno> resultado = new HashSet<>();
        StringBuilder sb = new StringBuilder();

        if(comPropostaAtribuida)

            for (var propostasAtribuidas : data.getPropostasAtribuidas())
                resultado.add(data.getAluno(propostasAtribuidas.getnAlunoAssociado()));

        else{
            HashSet<Long> alunosComProposta = new HashSet<>();

            for(var propostasAtribuidas : data.getPropostasAtribuidas())
                alunosComProposta.add(propostasAtribuidas.getnAlunoAssociado());

            for(var aluno : data.getAlunos())
                if(!alunosComProposta.contains(aluno.getnAluno()) && data.getCandidatura(aluno.getnAluno()) != null)
                    resultado.add(aluno);
        }

        ArrayList<Aluno> resultadoOrdenado = new ArrayList<>(resultado);
        Collections.sort(resultadoOrdenado);

        for(var aluno : resultadoOrdenado) {

            if (comPropostaAtribuida) {

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

        if(filtros.length != 1)
            return null;

        boolean propostasAtribuidas = filtros[0];

        HashSet<String> resultado = new HashSet<>();
        StringBuilder sb = new StringBuilder();

        if(propostasAtribuidas){
            for(var proposta : data.getPropostas())
                for(var propostasAtribuida : data.getPropostasAtribuidas())
                    if(propostasAtribuida.getId().equals(proposta.getId())) {
                        resultado.add(proposta.getId());
                        break;
                    }
        }else {

            HashSet<Proposta> propostasAtribuidaHS = new HashSet<>();

            for(var proposta : data.getPropostas())
                for(var propostasAtribuida : data.getPropostasAtribuidas())
                    if(propostasAtribuida.getId().equals(proposta.getId())) {
                        propostasAtribuidaHS.add(proposta);
                        break;
                    }

            for(var proposta : data.getPropostas())
                if(!propostasAtribuidaHS.contains(proposta))
                    resultado.add(proposta.getId());

        }

        ArrayList<String> resultadoOrdenado = new ArrayList<>(resultado);
        Collections.sort(resultadoOrdenado);

        for(var proposta : resultadoOrdenado) {
            if (propostasAtribuidas)
                sb.append(data.getPropostaAtribuida(proposta));
            else
                sb.append(data.getProposta(proposta));
            sb.append(System.lineSeparator());
        }

        return sb.toString();
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
