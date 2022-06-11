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
    private final ArrayList<Proposta> propostaDisponiveisAluno1;
    private final ArrayList<Proposta> propostaDisponiveisAluno2;

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

                if(data.getCandidatura(alunosSemProposta.get(i).getNAluno()) == null)
                    continue;

                for(var proposta : data.getCandidatura(alunosSemProposta.get(i).getNAluno()).getIdPropostas()) {

                    if (data.getProposta(proposta) instanceof Estagio &&
                            data.getPropostaAtribuida(proposta) == null) { // se for estágio e ainda não estiver atribuido

                        data.atribuirPropostaAluno(proposta, alunosSemProposta.get(i).getNAluno());
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

                if (data.getCandidatura(alunosSemProposta.get(i).getNAluno()) == null)
                    continue;

                for (var proposta : data.getCandidatura(alunosSemProposta.get(i).getNAluno()).getIdPropostas())
                    if (data.getPropostaAtribuida(proposta) == null)
                        data.atribuirPropostaAluno(proposta, alunosSemProposta.get(i).getNAluno());

            }
        }

        return true;
    }

    private boolean verificaConflito(Aluno aluno1, Aluno aluno2){
        if (aluno1.getClassificacao() == aluno2.getClassificacao()) {

            if(data.getCandidatura(aluno1.getNAluno()) != null &&
                    data.getCandidatura(aluno2.getNAluno()) != null) {

                setConflito(aluno1, aluno2);

                return true;
            }
        }
        return false;
    }

    private void setConflito(Aluno aluno1Conflito, Aluno aluno2Conflito){

        this.aluno1Conflito = aluno1Conflito;
        this.aluno2Conflito = aluno2Conflito;

        for(var proposta : data.getCandidatura(aluno1Conflito.getNAluno()).getIdPropostas())
            if(data.getPropostaAtribuida(proposta) == null)
                propostaDisponiveisAluno1.add(data.getProposta(proposta));

        for(var proposta : data.getCandidatura(aluno2Conflito.getNAluno()).getIdPropostas())
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
    public ArrayList<Aluno> consultarAlunos(boolean... filtros) {
        ArrayList<Aluno> alunos = new ArrayList<>();
        alunos.add(aluno1Conflito);
        alunos.add(aluno2Conflito);

        return alunos;
    }

    @Override
    public ArrayList<Proposta> consultarPropostas(boolean... filtros) {
        if (filtros.length != 1)
            return null;

        if (filtros[0])
            return propostaDisponiveisAluno1;
        else
            return propostaDisponiveisAluno2;
    }

    @Override
    public boolean adicionarDados(String... dados) {

        if(dados.length != 2)
            return false;

        if(Long.parseLong(dados[0]) == aluno1Conflito.getNAluno()) {
            if (!propostaDisponiveisAluno1.contains(data.getProposta(dados[1])))
                return false;

        }else if(Long.parseLong(dados[0]) == aluno2Conflito.getNAluno()) {
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
