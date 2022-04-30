package pt.isec.pa.apoio_poe.model.fsm;

public interface IApoioPoEState {

    //Inicio
    boolean comecarNovo();
    boolean carregarSave(String file);

    //Fase 1
    boolean gerirAlunos();
    boolean gerirDocentes();
    boolean gerirPropostas();

    //Fase 2, 3, 4
    boolean gerirDados();

    //Fase 2, 3, 4, 2 Bloqueada, 3 Bloqueada, 3 Mas 2 Aberta, 3 Atribuição Automática, Todas as Gestões
    boolean regressarFase();

    //Fase 1, 2, 3, 4, 1 Bloqueada, 2 Bloqueada, 3 Bloqueada, 3 Mas 2 Aberta
    boolean avancarFase(boolean bloquearFase);

    //Fase 1, 2, 3, 4, 5, 1 Bloqueada, 2 Bloqueada, 3 Bloqueada, 3 Mas 2 Aberta
    boolean terminarAplicacao(String file);

    //Fase 2, 3, 4, 5, 2 Bloqueada, 3 Bloqueada, 3 Mas 2 Aberta, 3 Atribuição Automática
    String consultarAlunos(boolean ... filtros);

    //Fase 2, 3, 5, 2 Bloqueada, 3 Bloqueada, 3 Mas 2 Aberta, 3 Atribuição Automática
    String consultarPropostas(boolean ... filtros);

    //Os 6 estados de Gestão
    boolean adicionarDados(String ... dados);
    boolean editarDados(String ... dados); // menos de Gestão Manual de Atribuições
    boolean removerDados(String ... dados);
    String consultarDados(String filtro);

    // Todas as Gestões
    boolean undo();
    boolean hasUndo();
    boolean redo();
    boolean hasRedo();

    //Gestões
    boolean importarDadosFicheiroCsv(String filename);
    boolean exportarDadosFicheiroCsv(String filename);

    //Fase 1 Bloqueada, 4, 5
    String consultarDocentes(String filtro);

    //Fase 1 Bloqueada
    String consultarAlunos(String filtro);
    String consultarPropostas(String filtro);

    boolean exportarAlunosFicheiroCsv(String filename);
    boolean exportarDocentesFicheiroCsv(String filename);
    boolean exportarPropostasFicheiroCsv(String filename);
    boolean exportarCandidaturasFicheiroCsv(String filename);
    boolean exportarPropostasAtribuidasFicheiroCsv(String filename, boolean guardarOrientador);

    //Fase 2 Bloqueada
    String consultarCandidaturas(String filtro);

    //Fase 3, Fase 3 Mas Fase 2 Aberta
    boolean atribuicaoAutomaticaPropostasComAluno();
    //Fase 3
    boolean atribuicaoAutomaticaPropostasDisponiveis();

    //Fase 4
    boolean associacaoAutomaticaDocentesProponentes();

    //Gestão Alunos, Docentes, Propostas, Candidaturas
    boolean removerTodosDados();

    //Todos
    ApoioPoEState getState();
}
