package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.ApoioPoE;
import pt.isec.pa.apoio_poe.model.data.ApoioPoEManager;
import pt.isec.pa.apoio_poe.model.data.Candidatura;
import pt.isec.pa.apoio_poe.model.data.pessoas.Docente;
import pt.isec.pa.apoio_poe.model.data.pessoas.alunos.Aluno;
import pt.isec.pa.apoio_poe.model.data.propostas.Proposta;
import pt.isec.pa.apoio_poe.model.data.propostas.PropostaAtribuida;
import pt.isec.pa.apoio_poe.model.errorHandling.ErrorOccurred;
import pt.isec.pa.apoio_poe.model.errorHandling.ErrorType;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Classe ApoioPoEContext utilizada nas interfaces com o utilizador, para alterar os dados
 * @author Maria Abreu e Pedro Morais
 * @version 1.0.0
 */
public class ApoioPoEContext {

    /**
     * constante identificativa para alteração ao estado atual
     */
    public static final String PROP_FASE = "_FASE_";
    /**
     * constante identificativa para alteração aos dados dos alunos
     */
    public static final String PROP_ALUNO = "_ALUNO_";
    /**
     * constante identificativa para alteração aos dados dos docentes
     */
    public static final String PROP_DOCENTE = "_DOCENTE_";
    /**
     * constante identificativa para alteração aos dados das propostas
     */
    public static final String PROP_PROPOSTA = "_PROPOSTA_";
    /**
     * constante identificativa para alteração aos dados das candidaturas
     */
    public static final String PROP_CANDIDATURA = "_CANDIDATURA_";
    /**
     * constante identificativa para alteração aos dados das propostas atribuídas
     */
    public static final String PROP_PROPOSTA_ATRIBUIDA = "_PROPOSTA_ATRIBUIDA_";

    /**
     * objeto que permite atualização automática dos dados na interface gráfica
     */
    PropertyChangeSupport pcs;
    /**
     * modelo de dados utilizado
     */
    private ApoioPoEManager data;
    /**
     * estado atual
     */
    private IApoioPoEState state;

    /**
     * instância da própria classe, padrão singleton
     */
    private static ApoioPoEContext instance = null;

    /**
     * permite obter a instância da classe, padrão singleton
     * @return instância única da classe
     */
    public static ApoioPoEContext getInstance(){
        if(instance == null)
            instance = new ApoioPoEContext();
        return instance;
    }

    /**
     * construtor privado
     */
    private ApoioPoEContext(){
        init(ApoioPoEState.INICIO);
        pcs = new PropertyChangeSupport(this);
    }

    /**
     * função que inicializa os dados
     * @param state - estado inicial da aplicação
     */
    public void init(ApoioPoEState state){
        this.data = new ApoioPoEManager(new ApoioPoE());
        this.state = state.createState(this, data);
    }

    /**
     * função que permite adicionar um listener a uma certa propriedade
     * @param property - propriedade
     * @param listener - ação a realizar
     */
    public void addPropertyChangeListener(String property, PropertyChangeListener listener){
        pcs.addPropertyChangeListener(property, listener);
    }

    /**
     * retomar um save realizado de uma execução anterior
     * @param data - dados
     * @param state - estado
     */
    public void retomarSave(ApoioPoEManager data, ApoioPoEState state){
        this.data = data;
        this.state = state.createState(this, data);
    }

    /**
     * alterar o estado atual
     * @param state - novo estado
     */
    public void changeState(IApoioPoEState state){
        this.state = state;
        pcs.firePropertyChange(PROP_FASE, null, null);
    }

    /**
     * obter o estado atual
     * @return estado atual
     */
    public ApoioPoEState getState(){
        return state.getState() == null ? null : state.getState();
    }

    /**
     * começar uma nova execução da aplicação
     * @return se conseguiu começar uma nova execução
     */
    public boolean comecarNovo(){
        boolean result = state.comecarNovo();
        pcs.firePropertyChange(PROP_FASE, null, null);
        return result;
    }

    /**
     * carregar save de um ficheiro, segundo a implementação do estado atual
     * @param file - nome do ficheiro
     * @return se conseguiu carregar
     */
    public boolean carregarSave(String file){
        boolean result = state.carregarSave(file);
        pcs.firePropertyChange(PROP_FASE, null, null);
        return result;
    }

    /**
     * alterar para a gestão de alunos, segundo a implementação do estado atual
     * @return se conseguiu alterar
     */
    public boolean gerirAlunos(){
        return state.gerirAlunos();
    }

    /**
     * alterar para a gestão de docentes, segundo a implementação do estado atual
     * @return se conseguiu alterar
     */
    public boolean gerirDocentes(){
        return state.gerirDocentes();
    }

    /**
     * alterar para a gestão de propostas, segundo a implementação do estado atual
     * @return se conseguiu alterar
     */
    public boolean gerirPropostas(){
        return state.gerirPropostas();
    }

    /**
     * atribuir automaticamente propostas que já têm aluno, segundo a implementação do estado atual
     * @return se conseguiu atribuir
     */
    public boolean atribuicaoAutomaticaPropostasComAluno(){
        boolean result = state.atribuicaoAutomaticaPropostasComAluno();
        pcs.firePropertyChange(PROP_PROPOSTA_ATRIBUIDA, null, null);
        return result;
    }

    /**
     * associar automaticamente docentes às propostas, segundo a implementação do estado atual
     * @return se conseguiu associar
     */
    public boolean associacaoAutomaticaDocentesProponentes(){
        boolean result = state.associacaoAutomaticaDocentesProponentes();
        pcs.firePropertyChange(PROP_PROPOSTA_ATRIBUIDA, null, null);
        return result;
    }

    /**
     * gerir os dados, segundo a implementação do estado atual
     * @return se conseguiu alterar
     */
    public boolean gerirDados(){
        return state.gerirDados();
    }

    /**
     * regressar à fase anterior, segundo a implementação do estado atual
     * @return se conseguiu regressar
     */
    public boolean regressarFase(){
        return state.regressarFase();
    }

    /**
     * avançar para a próxima fase, segundo a implementação do estado atual
     * @param bloquearFase - se pretende bloquear a fase atual
     * @return se conseguiu avançar
     */
    public boolean avancarFase(boolean bloquearFase){
        return state.avancarFase(bloquearFase);
    }

    /**
     * terminar a aplicação, segundo a implementação do estado atual
     * @param file - ficheiro onde vão ser guardados os dados da execução
     * @return se conseguiu terminar a aplicação
     */
    public boolean terminarAplicacao(String file){
        return state.terminarAplicacao(file);
    }

    /**
     * obter lista de alunos, segundo os filtros e segundo a implementação do estado atual
     * @param filtros - array de filtros
     * @return lista de alunos
     */
    public ArrayList<Aluno> consultarAlunos(boolean ... filtros){
        return state.consultarAlunos(filtros);
    }

    /**
     * obter lista de propostas, segundo os filtros e segundo a implementação do estado atual
     * @param filtros - array de filtros
     * @return lista de propostas
     */
    public ArrayList<Proposta> consultarPropostas(boolean ... filtros){
        return state.consultarPropostas(filtros);
    }

    /**
     * adicionar dados, segundo a implementação do estado atual
     * @param dados - dados a adicionar
     * @return se conseguiu adicionar
     */
    public boolean adicionarDados(String... dados) {
        boolean result = state.adicionarDados(dados);
        pcs.firePropertyChange(PROP_ALUNO, null, null);
        pcs.firePropertyChange(PROP_DOCENTE, null, null);
        pcs.firePropertyChange(PROP_PROPOSTA, null, null);
        pcs.firePropertyChange(PROP_CANDIDATURA, null, null);
        pcs.firePropertyChange(PROP_PROPOSTA_ATRIBUIDA, null, null);
        return result;
    }

    /**
     * editar dados, segundo a implementação do estado atual
     * @param dados - dados a editar
     * @return se conseguiu editar
     */
    public boolean editarDados(String... dados) {
        boolean result = state.editarDados(dados);
        pcs.firePropertyChange(PROP_ALUNO, null, null);
        pcs.firePropertyChange(PROP_DOCENTE, null, null);
        pcs.firePropertyChange(PROP_PROPOSTA, null, null);
        pcs.firePropertyChange(PROP_CANDIDATURA, null, null);
        pcs.firePropertyChange(PROP_PROPOSTA_ATRIBUIDA, null, null);
        return result;
    }

    /**
     * remover dados, segundo a implementação do estado atual
     * @param dados - dados a remover
     * @return se conseguiu remover
     */
    public boolean removerDados(String... dados) {
        boolean result = state.removerDados(dados);
        pcs.firePropertyChange(PROP_ALUNO, null, null);
        pcs.firePropertyChange(PROP_DOCENTE, null, null);
        pcs.firePropertyChange(PROP_PROPOSTA, null, null);
        pcs.firePropertyChange(PROP_CANDIDATURA, null, null);
        pcs.firePropertyChange(PROP_PROPOSTA_ATRIBUIDA, null, null);
        return result;
    }

    /**
     * consultar dados, segundo a implementação do estado atual
     * @param filtro - dado a pesquisar
     * @return resultado da pesquisa
     */
    public String consultarDados(String filtro) {
        return state.consultarDados(filtro);
    }

    /**
     * atribuição automática de propostas disponíveis, segundo a implementação do estado atual
     * @return se conseguiu atribuir
     */
    public boolean atribuicaoAutomaticaPropostasDisponiveis(){
        boolean result = state.atribuicaoAutomaticaPropostasDisponiveis();
        pcs.firePropertyChange(PROP_PROPOSTA_ATRIBUIDA, null, null);
        return result;
    }

    /**
     * importar dados de um ficheiro csv, segundo a implementação do estado atual
     * @param filename - nome do ficheiro
     * @return se conseguiu importar
     */
    public boolean importarDadosFicheiroCsv(String filename) {
        boolean result = state.importarDadosFicheiroCsv(filename);
        pcs.firePropertyChange(PROP_ALUNO, null, null);
        pcs.firePropertyChange(PROP_DOCENTE, null, null);
        pcs.firePropertyChange(PROP_PROPOSTA, null, null);
        pcs.firePropertyChange(PROP_CANDIDATURA, null, null);
        return result;
    }

    /**
     * exportar dados de um ficheiro csv, segundo a implementação do estado atual
     * @param filename - nome do ficheiro
     * @return se conseguiu exportar
     */
    public boolean exportarDadosFicheiroCsv(String filename) {
        return state.exportarDadosFicheiroCsv(filename);
    }

    /**
     * consultar alunos, segundo a implementação do estado atual
     * @param filtro - número de aluno a pesquisar
     * @return resultado da pesquisa
     */
    public String consultarAlunos(String filtro) {
        return state.consultarAlunos(filtro);
    }

    /**
     * consultar docentes, segundo a implementação do estado atual
     * @param filtro - email do docente a pesquisar
     * @return resultado da pesquisa
     */
    public ArrayList<String> consultarDocentes(String filtro) {
        return state.consultarDocentes(filtro);
    }

    /**
     * consultar propostas, segundo a implementação do estado atual
     * @param filtro - id da proposta a pesquisar
     * @return resultado da pesquisa
     */
    public String consultarPropostas(String filtro) {
        return state.consultarPropostas(filtro);
    }

    /**
     * consultar candidaturas, segundo a implementação do estado atual
     * @param filtro - número de aluno associado à proposta a pesquisar
     * @return resultado da pesquisa
     */
    public String consultarCandidaturas(String filtro) {
        return state.consultarCandidaturas(filtro);
    }

    /**
     * exportar alunos de um ficheiro csv, segundo a implementação do estado atual
     * @param filename - nome do ficheiro
     * @return se conseguiu exportar
     */
    public boolean exportarAlunosFicheiroCsv(String filename) {
        return state.exportarAlunosFicheiroCsv(filename);
    }

    /**
     * exportar docentes de um ficheiro csv, segundo a implementação do estado atual
     * @param filename - nome do ficheiro
     * @return se conseguiu exportar
     */
    public boolean exportarDocentesFicheiroCsv(String filename) {
        return state.exportarDocentesFicheiroCsv(filename);
    }

    /**
     * exportar propostas de um ficheiro csv, segundo a implementação do estado atual
     * @param filename - nome do ficheiro
     * @return se conseguiu exportar
     */
    public boolean exportarPropostasFicheiroCsv(String filename) {
        return state.exportarPropostasFicheiroCsv(filename);
    }

    /**
     * exportar candidaturas de um ficheiro csv, segundo a implementação do estado atual
     * @param filename - nome do ficheiro
     * @return se conseguiu exportar
     */
    public boolean exportarCandidaturasFicheiroCsv(String filename){
        return state.exportarCandidaturasFicheiroCsv(filename);
    }

    /**
     * exportar propostas atribuídas de um ficheiro csv, segundo a implementação do estado atual
     * @param filename - nome do ficheiro
     * @return se conseguiu exportar
     */
    public boolean exportarPropostasAtribuidasFicheiroCsv(String filename, boolean guardarOrientador){
        return state.exportarPropostasAtribuidasFicheiroCsv(filename, guardarOrientador);
    }

    /**
     * remover todos os dados, segundo a implementação do estado atual
     * @return se conseguiu remover
     */
    public boolean removerTodosDados(){
        boolean result = state.removerTodosDados();
        pcs.firePropertyChange(PROP_ALUNO, null, null);
        pcs.firePropertyChange(PROP_DOCENTE, null, null);
        pcs.firePropertyChange(PROP_PROPOSTA, null, null);
        pcs.firePropertyChange(PROP_CANDIDATURA, null, null);
        pcs.firePropertyChange(PROP_PROPOSTA_ATRIBUIDA, null, null);
        return result;
    }

    /**
     * desfazer operação, segundo a implementação do estado atual
     * @return se conseguiu desfazer
     */
    public boolean undo(){
        boolean result = state.undo();
        pcs.firePropertyChange(PROP_ALUNO, null, null);
        pcs.firePropertyChange(PROP_DOCENTE, null, null);
        pcs.firePropertyChange(PROP_PROPOSTA, null, null);
        pcs.firePropertyChange(PROP_CANDIDATURA, null, null);
        pcs.firePropertyChange(PROP_PROPOSTA_ATRIBUIDA, null, null);
        return result;
    }

    /**
     * é possível desfazer operações, segundo a implementação do estado atual
     * @return sim ou não
     */
    public boolean hasUndo(){return state.hasUndo();}

    /**
     * refazer operação, segundo a implementação do estado atual
     * @return se conseguiu refazer
     */
    public boolean redo(){
        boolean result = state.redo();
        pcs.firePropertyChange(PROP_ALUNO, null, null);
        pcs.firePropertyChange(PROP_DOCENTE, null, null);
        pcs.firePropertyChange(PROP_PROPOSTA, null, null);
        pcs.firePropertyChange(PROP_CANDIDATURA, null, null);
        pcs.firePropertyChange(PROP_PROPOSTA_ATRIBUIDA, null, null);
        return result;
    }
    /**
     * é possível refazer operações, segundo a implementação do estado atual
     * @return sim ou não
     */
    public boolean hasRedo(){return state.hasRedo();}

    /**
     * obter o tipo de proposta
     * @param id - id da proposta
     * @return tipo da proposta
     */
    public String getTipoProposta(String id){return data.getTipoProposta(id);}

    /**
     * carregar um save de um ficheiro binário
     * @param file - ficheiro a carregar
     * @return se conseguiu carregar
     */
    public boolean loadStateInFile(String file){
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))){

            retomarSave((ApoioPoEManager) ois.readObject(), (ApoioPoEState) ois.readObject());

        } catch (FileNotFoundException e) {
            ErrorOccurred.getInstance().setError(ErrorType.FILE_NOT_FOUND);
        } catch (IOException e) {
            ErrorOccurred.getInstance().setError(ErrorType.IO_EXCEPTION);
        } catch (ClassNotFoundException e) {
            ErrorOccurred.getInstance().setError(ErrorType.CLASS_NOT_FOUND);
        }
        return true;
    }


    /**
     * guardar um save de um ficheiro binário
     * @param file - ficheiro onde vai ser guardado
     * @return se conseguiu guardar
     */
    public boolean saveStateInFile(String file, ApoioPoEState state){
        try(ObjectOutputStream oos = new ObjectOutputStream( new FileOutputStream(file))){

            oos.writeObject(data);
            oos.writeObject(state);

        } catch (FileNotFoundException e) {
            ErrorOccurred.getInstance().setError(ErrorType.FILE_NOT_FOUND);
        } catch (IOException e) {
            ErrorOccurred.getInstance().setError(ErrorType.IO_EXCEPTION);
        }

        return true;
    }

    /**
     * obter lista de todos os alunos
     * @return lista de alunos
     */
    public ArrayList<Aluno> getAlunos(){return data.getAlunos();}

    /**
     * obter um aluno específico
     * @param nAluno - número do aluno a pesquisar
     * @return resultado da pesquisa
     */
    public Aluno getAluno(long nAluno){return data.getAluno(nAluno);}
    /**
     * obter lista de todos os docentes
     * @return lista de docentes
     */
    public ArrayList<Docente> getDocentes(){return data.getDocentes();}

    /**
     * obter um docente específico
     * @param email - email do docente a pesquisar
     * @return resultado da pesquisa
     */
    public Docente getDocente(String email){return data.getDocente(email);}
    /**
     * obter lista de todas as propostas
     * @return lista de propostas
     */
    public ArrayList<Proposta> getPropostas(){return data.getPropostas();}

    /**
     * obter uma proposta específica
     * @param id - id da proposta a pesquisar
     * @return resultado da pesquisa
     */
    public Proposta getProposta(String id){return data.getProposta(id);}
    /**
     * obter lista de todos as candidaturas
     * @return lista de candidaturas
     */
    public ArrayList<Candidatura> getCandidaturas(){return data.getCandidaturas();}

    /**
     * obter uma candidtura específica
     * @param nAluno - número do aluno associado à candidatura
     * @return resultado da pesquisa
     */
    public Candidatura getCandidatura(long nAluno){return data.getCandidatura(nAluno);}
    /**
     * obter lista de todas as propostas atribuídas
     * @return lista de propostas atribuídas
     */
    public ArrayList<PropostaAtribuida> getPropostasAtribuidas(){return data.getPropostasAtribuidas();}

    /**
     * obter uma proposta atribuída específica
     * @param id - id da proposta atribuída a pesquisar
     * @return resultado da pesquisa
     */
    public PropostaAtribuida getPropostaAtribuida(String id){return data.getPropostaAtribuida(id);}

    /**
     * obter número de alunos para um certo ramo
     * @param ramo - ramo a pesquisar
     * @return número de alunos
     */
    public int nAlunosPorRamo(String ramo){return data.nAlunosPorRamo(ramo);}
    /**
     * obter número de propostas para um certo ramo
     * @param ramo - ramo a pesquisar
     * @return número de propostas
     */
    public int propostasPorRamo(String ramo){return data.propostasPorRamo(ramo);}

    /**
     * calcular o número de orientações para um certo docente
     * @param filtro - email do docente a pesquisar
     * @return número de orientações
     */
    public int calculaNumeroOrientacoesDocente(String filtro){
        return data.calculaNumeroOrientacoesDocente(filtro);
    }

    /**
     * lista de propostas atribuídas para um certo orientador
     * @param filtro - email do orientador
     * @return lista de propostas atribuídas
     */
    public ArrayList<PropostaAtribuida> consultarPropostasAtribuidasDocente(String filtro){
        return data.consultarPropostasAtribuidasDocente(filtro);
    }

    /**
     * lista de alunos, segundo os filtros da fase 5
     * @param comPropostaAtribuida - se alunos têm proposta atribuída
     * @return lista de alunos
     */
    public ArrayList<Aluno> consultarAlunosFase5(boolean comPropostaAtribuida){
        return data.consultarAlunosFase5(comPropostaAtribuida);
    }

    /**
     * obter número de propostas atribuídas, não atribuídas e número total de propostas
     * @return  número de propostas atribuídas, não atribuídas e número total de propostas
     */
    public ArrayList<Integer> propostasAtribuidas_NaoAtribuidas_Total() {
        return data.propostasAtribuidas_NaoAtribuidas_Total();
    }

    /**
     *  top 5 empresas com mais estágios
     * @return nome e quantidade de estágios
     */
    public HashMap<String, Number> top5EmpresasEstagio() {
        return data.top5EmpresasEstagio();
    }

    /**
     * top 5 docentes com mais orientações
     * @return nome e quantidade de orientações
     */
    public HashMap<String, Number> top5DocentesOrientacoes() {
        return data.top5DocentesOrientacoes();
    }

    /**
     * obter número de propostas atribuídas com orientador
     * @return número de propostas atribuídas com orientador
     */
    public int getNumPropotasAtribuidasComOrientador() {
        return data.getNumPropotasAtribuidasComOrientador();
    }
}
