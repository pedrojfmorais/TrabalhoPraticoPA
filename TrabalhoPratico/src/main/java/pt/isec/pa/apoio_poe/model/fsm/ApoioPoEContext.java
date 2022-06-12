package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.ApoioPoE;
import pt.isec.pa.apoio_poe.model.data.ApoioPoEManager;
import pt.isec.pa.apoio_poe.model.data.Candidatura;
import pt.isec.pa.apoio_poe.model.data.pessoas.Docente;
import pt.isec.pa.apoio_poe.model.data.pessoas.alunos.Aluno;
import pt.isec.pa.apoio_poe.model.data.propostas.Proposta;
import pt.isec.pa.apoio_poe.model.data.propostas.PropostaAtribuida;
import pt.isec.pa.apoio_poe.model.errorHandling.ErrorOccurred;
import pt.isec.pa.apoio_poe.model.errorHandling.ErrorsTypes;
import pt.isec.pa.apoio_poe.utils.PAInput;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.*;
import java.util.ArrayList;

public class ApoioPoEContext {

    public static final String PROP_FASE = "_FASE_";
    public static final String PROP_ALUNO = "_ALUNO_";
    public static final String PROP_DOCENTE = "_DOCENTE_";
    public static final String PROP_PROPOSTA = "_PROPOSTA_";
    public static final String PROP_CANDIDATURA = "_CANDIDATURA_";
    public static final String PROP_PROPOSTA_ATRIBUIDA = "_PROPOSTA_ATRIBUIDA_";
    PropertyChangeSupport pcs;
    private ApoioPoEManager data;
    private IApoioPoEState state;

    private static ApoioPoEContext instance = null;

    public static ApoioPoEContext getInstance(){
        if(instance == null)
            instance = new ApoioPoEContext();
        return instance;
    }

    private ApoioPoEContext(){
        init(ApoioPoEState.INICIO);
        pcs = new PropertyChangeSupport(this);
    }

    public void init(ApoioPoEState state){
        this.data = new ApoioPoEManager(new ApoioPoE());
        this.state = state.createState(this, data);

        //debug
        data.adicionaAlunosDeFicheiro("files/alunos.csv");
        data.adicionaDocentesDeFicheiro("files/docentes.csv");
        data.adicionaPropostasDeFicheiro("files/propostas.csv");
        data.adicionaCandidaturaDeFicheiro("files/candidaturas.csv");
    }
    public void addPropertyChangeListener(String property, PropertyChangeListener listener){
        pcs.addPropertyChangeListener(property, listener);
    }

    public void retomarSave(ApoioPoEManager data, ApoioPoEState state){
        this.data = data;
        this.state = state.createState(this, data);
        pcs.firePropertyChange(PROP_FASE, null, null);
    }

    public void changeState(IApoioPoEState state){
        this.state = state;
        pcs.firePropertyChange(PROP_FASE, null, null);
    }

    public ApoioPoEState getState(){
        return state.getState() == null ? null : state.getState();
    }

    public boolean comecarNovo(){
        boolean result = state.comecarNovo();
        pcs.firePropertyChange(PROP_FASE, null, null);
        return result;
    }

    public boolean carregarSave(String file){
        boolean result = state.carregarSave(file);
        pcs.firePropertyChange(PROP_FASE, null, null);
        return result;
    }

    public boolean gerirAlunos(){
        boolean result = state.gerirAlunos();
        pcs.firePropertyChange(PROP_FASE, null, null);
        return result;
    }

    public boolean gerirDocentes(){
        boolean result = state.gerirDocentes();
        pcs.firePropertyChange(PROP_FASE, null, null);
        return result;
    }
    public boolean gerirPropostas(){
        boolean result = state.gerirPropostas();
        pcs.firePropertyChange(PROP_FASE, null, null);
        return result;
    }

    public boolean atribuicaoAutomaticaPropostasComAluno(){return state.atribuicaoAutomaticaPropostasComAluno();}
    public boolean associacaoAutomaticaDocentesProponentes(){return state.associacaoAutomaticaDocentesProponentes();}

    public boolean gerirDados(){
        boolean result = state.gerirDados();
        pcs.firePropertyChange(PROP_FASE, null, null);
        return result;
    }

    public boolean regressarFase(){
        boolean result = state.regressarFase();
        pcs.firePropertyChange(PROP_FASE, null, null);
        return result;
    }
    public boolean avancarFase(boolean bloquearFase){
        boolean result = state.avancarFase(bloquearFase);
        pcs.firePropertyChange(PROP_FASE, null, null);
        return result;
    }

    public boolean terminarAplicacao(String file){
        boolean result = state.terminarAplicacao(file);
        pcs.firePropertyChange(PROP_FASE, null, null);
        return result;
    }

    public ArrayList<Aluno> consultarAlunos(boolean ... filtros){
        return state.consultarAlunos(filtros);
    }

    public ArrayList<Proposta> consultarPropostas(boolean ... filtros){
        return state.consultarPropostas(filtros);
    }

    public boolean adicionarDados(String... dados) {
        boolean result = state.adicionarDados(dados);
        pcs.firePropertyChange(PROP_ALUNO, null, null);
        pcs.firePropertyChange(PROP_DOCENTE, null, null);
        pcs.firePropertyChange(PROP_PROPOSTA, null, null);
        pcs.firePropertyChange(PROP_CANDIDATURA, null, null);
        pcs.firePropertyChange(PROP_PROPOSTA_ATRIBUIDA, null, null);
        return result;
    }
    public boolean editarDados(String... dados) {
        boolean result = state.editarDados(dados);
        pcs.firePropertyChange(PROP_ALUNO, null, null);
        pcs.firePropertyChange(PROP_DOCENTE, null, null);
        pcs.firePropertyChange(PROP_PROPOSTA, null, null);
        pcs.firePropertyChange(PROP_CANDIDATURA, null, null);
        return result;
    }
    public boolean removerDados(String... dados) {
        boolean result = state.removerDados(dados);
        pcs.firePropertyChange(PROP_ALUNO, null, null);
        pcs.firePropertyChange(PROP_DOCENTE, null, null);
        pcs.firePropertyChange(PROP_PROPOSTA, null, null);
        pcs.firePropertyChange(PROP_CANDIDATURA, null, null);
        pcs.firePropertyChange(PROP_PROPOSTA_ATRIBUIDA, null, null);
        return result;
    }
    public String consultarDados(String filtro) {
        return state.consultarDados(filtro);
    }

    public boolean atribuicaoAutomaticaPropostasDisponiveis(){return state.atribuicaoAutomaticaPropostasDisponiveis();}

    public boolean importarDadosFicheiroCsv(String filename) {
        boolean result = state.importarDadosFicheiroCsv(filename);
        pcs.firePropertyChange(PROP_ALUNO, null, null);
        pcs.firePropertyChange(PROP_DOCENTE, null, null);
        pcs.firePropertyChange(PROP_PROPOSTA, null, null);
        pcs.firePropertyChange(PROP_CANDIDATURA, null, null);
        return result;
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

    public boolean removerTodosDados(){
        boolean result = state.removerTodosDados();
        pcs.firePropertyChange(PROP_ALUNO, null, null);
        pcs.firePropertyChange(PROP_DOCENTE, null, null);
        pcs.firePropertyChange(PROP_PROPOSTA, null, null);
        pcs.firePropertyChange(PROP_CANDIDATURA, null, null);
        pcs.firePropertyChange(PROP_PROPOSTA_ATRIBUIDA, null, null);
        return result;
    }

    public boolean undo(){
        boolean result = state.undo();
        pcs.firePropertyChange(PROP_ALUNO, null, null);
        pcs.firePropertyChange(PROP_DOCENTE, null, null);
        pcs.firePropertyChange(PROP_PROPOSTA, null, null);
        pcs.firePropertyChange(PROP_CANDIDATURA, null, null);
        pcs.firePropertyChange(PROP_PROPOSTA_ATRIBUIDA, null, null);
        return result;
    }
    public boolean hasUndo(){return state.hasUndo();}

    public boolean redo(){
        boolean result = state.redo();
        pcs.firePropertyChange(PROP_ALUNO, null, null);
        pcs.firePropertyChange(PROP_DOCENTE, null, null);
        pcs.firePropertyChange(PROP_PROPOSTA, null, null);
        pcs.firePropertyChange(PROP_CANDIDATURA, null, null);
        pcs.firePropertyChange(PROP_PROPOSTA_ATRIBUIDA, null, null);
        return result;
    }

    public boolean hasRedo(){return state.hasRedo();}

    public String getTipoProposta(String id){return data.getTipoProposta(id);}

    public boolean loadStateInFile(String file){
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))){

            retomarSave((ApoioPoEManager) ois.readObject(), (ApoioPoEState) ois.readObject());

        } catch (FileNotFoundException e) {
            ErrorOccurred.getInstance().setError(ErrorsTypes.FILE_NOT_FOUND);
        } catch (IOException e) {
            ErrorOccurred.getInstance().setError(ErrorsTypes.IO_EXCEPTION);
        } catch (ClassNotFoundException e) {
            ErrorOccurred.getInstance().setError(ErrorsTypes.CLASS_NOT_FOUND);
        }
        return true;
    }

    public boolean saveStateInFile(String file, ApoioPoEState state){
        try(ObjectOutputStream oos = new ObjectOutputStream( new FileOutputStream(file))){

            oos.writeObject(data);
            oos.writeObject(state);

        } catch (FileNotFoundException e) {
            ErrorOccurred.getInstance().setError(ErrorsTypes.FILE_NOT_FOUND);
        } catch (IOException e) {
            ErrorOccurred.getInstance().setError(ErrorsTypes.IO_EXCEPTION);
        }

        return true;
    }

    public ArrayList<Aluno> getAlunos(){return data.getAlunos();}
    public Aluno getAluno(long nAluno){return data.getAluno(nAluno);}
    public ArrayList<Docente> getDocentes(){return data.getDocentes();}
    public Docente getDocente(String email){return data.getDocente(email);}
    public ArrayList<Proposta> getPropostas(){return data.getPropostas();}
    public Proposta getProposta(String id){return data.getProposta(id);}
    public ArrayList<Candidatura> getCandidaturas(){return data.getCandidaturas();}
    public Candidatura getCandidatura(long nAluno){return data.getCandidatura(nAluno);}
    public ArrayList<PropostaAtribuida> getPropostasAtribuidas(){return data.getPropostasAtribuidas();}
    public PropostaAtribuida getPropostaAtribuida(String id){return data.getPropostaAtribuida(id);}

    public int nAlunosPorRamo(String ramo){return data.nAlunosPorRamo(ramo);}
    public int propostasPorRamo(String ramo){return data.propostasPorRamo(ramo);}
}
