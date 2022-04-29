package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.ApoioPOE;
import pt.isec.pa.apoio_poe.model.data.ApoioPoeManager;
import pt.isec.pa.apoio_poe.model.exceptionsHandling.ExceptionOccurred;
import pt.isec.pa.apoio_poe.model.exceptionsHandling.ExceptionsTypes;

public class ApoioPoeContext{

    private ApoioPoeManager data;
    private IApoioPoeState state;

    public ApoioPoeContext(){
        init(ApoioPoeState.INICIO);
    }

    public void init(ApoioPoeState state){
        this.data = new ApoioPoeManager(new ApoioPOE());

        //TODO: apenas para testar
        data.adicionaAlunosDeFicheiro("files/alunos.csv");
        data.adicionaDocentesDeFicheiro("files/docentes.csv");
        data.adicionaPropostasDeFicheiro("files/propostas.csv");
        data.adicionaCandidaturaDeFicheiro("files/candidaturas.csv");

        this.state = state.createState(this, data);
    }

    public void retomarSave(ApoioPoeManager data, ApoioPoeState state){
        this.data = data;
        this.state = state.createState(this, data);
    }

    public void changeState(IApoioPoeState state){this.state = state;}

    public ApoioPoeState getState(){
        return state.getState() == null ? null : state.getState();
    }

    public boolean comecarNovo(){return state.comecarNovo();}

    public boolean carregarSave(String file){return state.carregarSave(file);}

    public boolean gerirAlunos(){return state.gerirAlunos();}

    public boolean gerirDocentes(){return state.gerirDocentes();}
    public boolean gerirPropostas(){return state.gerirPropostas();}

    public boolean atribuicaoAutomaticaPropostasComAluno(){return state.atribuicaoAutomaticaPropostasComAluno();}
    public boolean associacaoAutomaticaDocentesProponentes(){return state.associacaoAutomaticaDocentesProponentes();}

    public boolean gerirDados(){return state.gerirDados();}

    public boolean regressarFase(){return state.regressarFase();}
    public boolean avancarFase(boolean bloquearFase){return state.avancarFase(bloquearFase);}

    public boolean terminarAplicacao(String file){return state.terminarAplicacao(file);}

    public String consultarAlunos(boolean ... filtros){
        return state.consultarAlunos(filtros);
    }

    public String consultarPropostas(boolean ... filtros){
        return state.consultarPropostas(filtros);
    }

    public boolean adicionarDados(String... dados) {
        return state.adicionarDados(dados);
    }
    public boolean editarDados(String... dados) {
        return state.editarDados(dados);
    }
    public boolean removerDados(String... dados) {
        return state.removerDados(dados);
    }
    public String consultarDados(String filtro) {
        return state.consultarDados(filtro);
    }

    public boolean atribuicaoAutomaticaPropostasDisponiveis(){return state.atribuicaoAutomaticaPropostasDisponiveis();}

    public boolean importarDadosFicheiroCsv(String filename) {
        return state.importarDadosFicheiroCsv(filename);
    }
    public boolean exportarDadosFicheiroCsv(String filename) {
        return state.exportarDadosFicheiroCsv(filename);
    }

    public String consultarAlunos(String filtro) {
        return state.consultarAlunos(filtro);
    }
    public String consultarDocentes(String filtro) {
        return state.consultarDocentes(filtro);
    }
    public String consultarPropostas(String filtro) {
        return state.consultarPropostas(filtro);
    }
    public String consultarCandidaturas(String filtro) {
        return state.consultarCandidaturas(filtro);
    }

    public boolean exportarAlunosFicheiroCsv(String filename) {
        return state.exportarAlunosFicheiroCsv(filename);
    }
    public boolean exportarDocentesFicheiroCsv(String filename) {
        return state.exportarDocentesFicheiroCsv(filename);
    }
    public boolean exportarPropostasFicheiroCsv(String filename) {
        return state.exportarPropostasFicheiroCsv(filename);
    }
    public boolean exportarCandidaturasFicheiroCsv(String filename){
        return state.exportarCandidaturasFicheiroCsv(filename);
    }
    public boolean exportarPropostasAtribuidasFicheiroCsv(String filename, boolean guardarOrientador){
        return state.exportarPropostasAtribuidasFicheiroCsv(filename, guardarOrientador);
    }

    public boolean undo(){return state.undo();}
    public boolean hasUndo(){return state.hasUndo();}

    public boolean redo(){return state.redo();}
    public boolean hasRedo(){return state.hasRedo();}
}
