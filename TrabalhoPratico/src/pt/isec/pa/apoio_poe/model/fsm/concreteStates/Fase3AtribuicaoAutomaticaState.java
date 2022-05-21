package pt.isec.pa.apoio_poe.model.fsm.concreteStates;

import pt.isec.pa.apoio_poe.model.data.ApoioPoEManager;
import pt.isec.pa.apoio_poe.model.data.pessoas.alunos.Aluno;
import pt.isec.pa.apoio_poe.model.data.pessoas.alunos.AlunoClassificacaoComparator;
import pt.isec.pa.apoio_poe.model.data.propostas.Estagio;
import pt.isec.pa.apoio_poe.model.data.propostas.Proposta;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEContext;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEState;

import java.util.ArrayList;
import java.util.Collections;

public class Fase3AtribuicaoAutomaticaState extends ApoioPoEAdapter {

    private Aluno aluno1Conflito;
    private Aluno aluno2Conflito;
    private ArrayList<Proposta> propostaDisponiveisAluno1;
    private ArrayList<Proposta> propostaDisponiveisAluno2;

    public Fase3AtribuicaoAutomaticaState(ApoioPoEContext context, ApoioPoEManager data) {
        super(context, data);

        propostaDisponiveisAluno1 = new ArrayList<>();
        propostaDisponiveisAluno2 = new ArrayList<>();

        removeConflito();
    }

    @Override
    public boolean regressarFase() {
        changeState(ApoioPoEState.FASE3);
        return true;
    }

    @Override
    public boolean atribuicaoAutomaticaPropostasDisponiveis() {

        removeConflito();

        //primeiro verificamos os alunos que tem acesso a estágio, dando-lhe apenas estágios
        ArrayList<Aluno> alunosSemProposta = data.getAlunosSemPropostaAtribuida(true);

        if(!alunosSemProposta.isEmpty()) {

            alunosSemProposta.sort(Collections.reverseOrder(new AlunoClassificacaoComparator()));

            for (int i = 0; i < alunosSemProposta.size(); i++) {

                if(i < alunosSemProposta.size()-1){

                    if(verificaConflito(alunosSemProposta.get(i), alunosSemProposta.get(i+1)))
                        return false;
                }

                if(data.getCandidatura(alunosSemProposta.get(i).getnAluno()) == null)
                    continue;

                for(var proposta : data.getCandidatura(alunosSemProposta.get(i).getnAluno()).getIdPropostas()) {

                    if (data.getProposta(proposta) instanceof Estagio &&
                            data.getPropostaAtribuida(proposta) == null) { // se for estágio e ainda não estiver atribuido

                        data.atribuirPropostaAluno(proposta, alunosSemProposta.get(i).getnAluno());
                    }
                }
            }
        }

        //atribuição das propostas a todos os alunos
        alunosSemProposta = data.getAlunosSemPropostaAtribuida(false);
        if(!alunosSemProposta.isEmpty()) {

            alunosSemProposta.sort(Collections.reverseOrder(new AlunoClassificacaoComparator()));

            for (int i = 0; i < alunosSemProposta.size(); i++) {

                if (i < alunosSemProposta.size() - 1) {

                    if (verificaConflito(alunosSemProposta.get(i), alunosSemProposta.get(i + 1)))
                        return false;
                }

                if (data.getCandidatura(alunosSemProposta.get(i).getnAluno()) == null)
                    continue;

                for (var proposta : data.getCandidatura(alunosSemProposta.get(i).getnAluno()).getIdPropostas())
                    if (data.getPropostaAtribuida(proposta) == null)
                        data.atribuirPropostaAluno(proposta, alunosSemProposta.get(i).getnAluno());

            }
        }

        return true;
    }

    private boolean verificaConflito(Aluno aluno1, Aluno aluno2){
        if (aluno1.getClassificacao() == aluno2.getClassificacao()) {

            if(data.getCandidatura(aluno1.getnAluno()) != null &&
                    data.getCandidatura(aluno2.getnAluno()) != null) {

                setConflito(aluno1, aluno2);

                return true;
            }
        }
        return false;
    }

    private void setConflito(Aluno aluno1Conflito, Aluno aluno2Conflito){

        this.aluno1Conflito = aluno1Conflito;
        this.aluno2Conflito = aluno2Conflito;

        for(var proposta : data.getCandidatura(aluno1Conflito.getnAluno()).getIdPropostas())
            if(data.getPropostaAtribuida(proposta) == null)
                propostaDisponiveisAluno1.add(data.getProposta(proposta));

        for(var proposta : data.getCandidatura(aluno2Conflito.getnAluno()).getIdPropostas())
            if(data.getPropostaAtribuida(proposta) == null)
                propostaDisponiveisAluno2.add(data.getProposta(proposta));
    }

    private void removeConflito(){
        this.aluno1Conflito = null;
        this.aluno2Conflito = null;
        this.propostaDisponiveisAluno1.clear();
        this.propostaDisponiveisAluno2.clear();
    }

    @Override
    public String consultarAlunos(boolean... filtros) {
        StringBuilder sb = new StringBuilder();

        sb.append("Aluno 1:").append(System.lineSeparator()).append(aluno1Conflito).append(System.lineSeparator());
        sb.append("Aluno 2:").append(System.lineSeparator()).append(aluno2Conflito).append(System.lineSeparator());

        return sb.toString();
    }

    @Override
    public String consultarPropostas(boolean... filtros) {
        if(filtros.length != 1)
            return null;

        StringBuilder sb = new StringBuilder();

        if(filtros[0]) {
            sb.append("Propostas disponiveis para o aluno 1:").append(System.lineSeparator());
            for (var proposta : propostaDisponiveisAluno1)
                sb.append(proposta).append(System.lineSeparator());
        }
        else {
            sb.append("Propostas disponiveis para o aluno 2:").append(System.lineSeparator());
            for (var proposta : propostaDisponiveisAluno2)
                sb.append(proposta).append(System.lineSeparator());
        }

        return sb.toString();
    }

    @Override
    public boolean adicionarDados(String... dados) {

        if(dados.length != 2)
            return false;

        if(Long.parseLong(dados[0]) == aluno1Conflito.getnAluno()) {
            if (!propostaDisponiveisAluno1.contains(data.getProposta(dados[1])))
                return false;

        }else if(Long.parseLong(dados[0]) == aluno2Conflito.getnAluno()) {
                if (!propostaDisponiveisAluno2.contains(data.getProposta(dados[1])))
                    return false;
        }else
            return false;

        return data.atribuirPropostaAluno(dados[1], Long.parseLong(dados[0]));
    }

    @Override
    public ApoioPoEState getState() {
        return ApoioPoEState.Fase3AtribuicaoAutomatica;
    }
}
