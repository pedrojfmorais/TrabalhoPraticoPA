package pt.isec.pa.apoio_poe.model.fsm.concreteStates;

import pt.isec.pa.apoio_poe.model.data.ApoioPOE;
import pt.isec.pa.apoio_poe.model.data.ApoioPoeManager;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoeContext;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoeState;
import pt.isec.pa.apoio_poe.model.fsm.IApoioPoeState;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

abstract class ApoioPoeAdapter implements IApoioPoeState {

    ApoioPoeContext context;
    ApoioPoeManager data;

    public ApoioPoeAdapter(ApoioPoeContext context, ApoioPoeManager data){
        this.context = context;
        this.data = data;
    }

    public void changeState(ApoioPoeState state){context.changeState(state.createState(context, data));}

    @Override
    public boolean comecarNovo(){
        return false;
    }

    @Override
    public boolean carregarSave(String file){
        return false;
    }

    @Override
    public boolean gerirAlunos(){
        return false;
    }
    @Override
    public boolean gerirDocentes(){
        return false;
    }
    @Override
    public boolean gerirPropostas(){
        return false;
    }

    @Override
    public boolean gerirDados(){
        return false;
    }

    @Override
    public boolean regressarFase(){
        return false;
    }

    @Override
    public boolean terminarAplicacao(String file) {
        return false;
    }

    @Override
    public boolean avancarFase(boolean bloquearFase){
        return false;
    }

    @Override
    public String consultarAlunos(boolean ... filtros) {
        return null;
    }

    @Override
    public String consultarPropostas(boolean... filtros) {
        return null;
    }

    @Override
    public boolean adicionarDados(String... dados) {
        return false;
    }
    @Override
    public boolean editarDados(String... dados) {
        return false;
    }
    @Override
    public boolean removerDados(String... dados) {
        return false;
    }
    @Override
    public String consultarDados(String filtro) {
        return null;
    }

    @Override
    public boolean undo() {
        return false;
    }
    @Override
    public boolean hasUndo() {
        return false;
    }

    @Override
    public boolean redo() {
        return false;
    }
    @Override
    public boolean hasRedo() {
        return false;
    }

    @Override
    public boolean importarDadosFicheiroCsv(String filename) {
        return false;
    }
    @Override
    public boolean exportarDadosFicheiroCsv(String filename) {
        return false;
    }

    @Override
    public String consultarAlunos(String filtro) {
        return null;
    }
    @Override
    public String consultarDocentes(String filtro) {
        return null;
    }
    @Override
    public String consultarPropostas(String filtro) {
        return null;
    }

    @Override
    public boolean exportarAlunosFicheiroCsv(String filename) {
        return false;
    }
    @Override
    public boolean exportarDocentesFicheiroCsv(String filename) {
        return false;
    }
    @Override
    public boolean exportarPropostasFicheiroCsv(String filename) {
        return false;
    }
    @Override
    public boolean exportarCandidaturasFicheiroCsv(String filename) {
        return false;
    }
    @Override
    public boolean exportarPropostasAtribuidasFicheiroCsv(String filename, boolean guardarOrientador) {
        return false;
    }

    @Override
    public String consultarCandidaturas(String filtro) {
        return null;
    }

    @Override
    public boolean atribuicaoAutomaticaPropostasComAluno() {
        return false;
    }

    @Override
    public boolean atribuicaoAutomaticaPropostasDisponiveis() {
        return false;
    }

    @Override
    public boolean associacaoAutomaticaDocentesProponentes() {
        return false;
    }
}
