package pt.isec.pa.apoio_poe.model.fsm.concreteStates;

import pt.isec.pa.apoio_poe.model.data.ApoioPoEManager;
import pt.isec.pa.apoio_poe.model.data.pessoas.alunos.Aluno;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEContext;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class Fase4State extends ApoioPoEAdapter {
    public Fase4State(ApoioPoEContext context, ApoioPoEManager data) {
        super(context, data);
    }

    @Override
    public boolean gerirDados() {
        changeState(ApoioPoEState.GESTAO_MANUAL_ORIENTADORES);
        return true;
    }

    @Override
    public boolean avancarFase(boolean bloquearFase) {

        data.setFaseBloqueada(4);

        changeState(ApoioPoEState.FASE5);
        return true;
    }

    @Override
    public boolean regressarFase() {

        if(data.getFaseBloqueada() < 2)
            changeState(ApoioPoEState.Fase3MasFase2AbertaState);

        else if(data.getFaseBloqueada() == 2)
            changeState(ApoioPoEState.FASE3);

        else if(data.getFaseBloqueada() == 3)
            changeState(ApoioPoEState.FASE3_BLOQUEADA);

        return true;
    }

    @Override
    public boolean terminarAplicacao(String file) {
        if(!file.isBlank())
            data.saveStateInFile(file, context.getState());

        changeState(ApoioPoEState.INICIO);
        return true;
    }

    @Override
    public boolean associacaoAutomaticaDocentesProponentes() {
        return data.associacaoAutomaticaDocentesProponentes();
    }

    @Override
    public String consultarAlunos(boolean... filtros) {

        if(filtros.length != 1)
            return null;

        boolean comOrientadorAssociado = filtros[0];

        HashSet<Aluno> resultado = new HashSet<>();
        StringBuilder sb = new StringBuilder();

        if(comOrientadorAssociado){
            for(var propostasAtribuidas : data.getPropostasAtribuidas())
                if(propostasAtribuidas.getEmailDocenteOrientador() != null)
                    resultado.add(data.getAluno(propostasAtribuidas.getnAlunoAssociado()));
        } else
            for(var propostasAtribuidas : data.getPropostasAtribuidas())
                if(propostasAtribuidas.getEmailDocenteOrientador() == null)
                    resultado.add(data.getAluno(propostasAtribuidas.getnAlunoAssociado()));

        ArrayList<Aluno> resultadoOrdenado = new ArrayList<>(resultado);
        Collections.sort(resultadoOrdenado);

        for(var aluno : resultadoOrdenado)
            sb.append(aluno).append(System.lineSeparator());

        return sb.toString();
    }

    @Override
    public String consultarDocentes(String filtro) {

        StringBuilder sb = new StringBuilder();

        if(!filtro.isBlank()) {

            if(data.getDocente(filtro) == null)
                return null;

            sb.append("Docente: ").append(data.getDocente(filtro).getNome()).append(System.lineSeparator());
            sb.append("Número de orientações: ").append(data.calculaNumeroOrientacoesDocente(filtro));

        } else{

            int max = 0, min = 0;
            double media = 0;

            for(var docente : data.getDocentes()){
                int nOrientacoes = data.calculaNumeroOrientacoesDocente(docente.getEmail());

                if(nOrientacoes < min)
                    min = nOrientacoes;

                if(nOrientacoes > max)
                    max = nOrientacoes;

                media += nOrientacoes;
            }

            media /= data.getDocentes().size();

            if(media == 0.0)
                return null;

            sb.append("Máximo de orientações de um docente: ").append(max).append(System.lineSeparator());
            sb.append("Minimo de orientações de um docente: ").append(min).append(System.lineSeparator());
            sb.append("Média de orientações de um docente: ").append(media);
        }

        sb.append(System.lineSeparator());

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
    public ApoioPoEState getState() {
        return ApoioPoEState.FASE4;
    }
}
