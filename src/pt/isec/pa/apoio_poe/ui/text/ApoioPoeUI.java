package pt.isec.pa.apoio_poe.ui.text;

import pt.isec.pa.apoio_poe.model.exceptionsHandling.ExceptionOccurred;
import pt.isec.pa.apoio_poe.model.exceptionsHandling.ExceptionsTypes;
import pt.isec.pa.apoio_poe.model.fsm.*;
import pt.isec.pa.apoio_poe.utils.PAInput;

public class ApoioPoeUI {
    ApoioPoeContext fsm;

    public ApoioPoeUI(ApoioPoeContext fsm){this.fsm = fsm;}

    private boolean finish = false;

    public void start(){
        while (!finish){

            exceptionMessages();

            switch (fsm.getState()){
                case INICIO -> inicioUI();
                case FASE1 -> fase1UI();
                case FASE1_BLOQUEADA -> fase1BloqueadaUI();
                case GESTAO_ALUNOS -> gestaoAlunosUI();
                case GESTAO_DOCENTES -> gestaoDocentesUI();
                case GESTAO_PROPOSTAS -> gestaoPropostasUI();
                case FASE2 -> fase2UI();
                case FASE2_BLOQUEADA -> fase2BloqueadaUI();
                case GESTAO_CANDIDATURAS -> gestaoCandidaturasUI();
                case FASE3 -> fase3UI();
                case Fase3MasFase2AbertaState -> fase3MasFase2AbertaStateUI();
                case FASE3_BLOQUEADA -> fase3BloqueadaUI();
                case GESTAO_MANUAL_ATRIBUICOES -> gestaoManualAtribuicoesUI();
                case Fase3AtribuicaoAutomatica -> fase3AtribuicaoAutomaticaUI();
                case FASE4 -> fase4UI();
                case GESTAO_MANUAL_ORIENTADORES -> gestaoManualOrientadoresUI();
                case FASE5 -> fase5UI();
            }
        }
    }

    private void exceptionMessages(){
        if(ExceptionOccurred.getException() == ExceptionsTypes.NONE)
            return;

        System.out.println("\n\n\n**************************************************\n");
        switch (ExceptionOccurred.getException()){
            case FileNotFound -> System.out.println("Não foi possivel encontrar o ficheiro;");
            case ClassNotFound -> System.out.println("Erro a carregar o save.");
            case CloneNotFound -> System.out.println("Não foi possivel clonar o objeto. Operação não suportada!");
            case IOException -> System.out.println("Não foi possivel realizar uma operação de input/output!");
        }
        System.out.println("\n**************************************************\n");

        ExceptionOccurred.setException(ExceptionsTypes.NONE);
    }

    private void inicioUI(){
        switch (PAInput.chooseOption("Bem vindo! \n", "Começar novo jogo", "Carregar um jogo", "Sair")){
            case 1 -> fsm.comecarNovo();
            case 2 -> fsm.carregarSave(PAInput.readString("Insira o nome do save: ", false));
            case 3 -> finish = true;
        }
    }

    private void fase1UI() {
        switch (PAInput.chooseOption(
                "Fase 1 - Configuração",
                "Gerir Alunos",
                "Gerir Docentes",
                "Gerir Propostas",
                "Avançar para a próxima Fase",
                "Sair"
        )){
            case 1 -> fsm.gerirAlunos();
            case 2 -> fsm.gerirDocentes();
            case 3 -> fsm.gerirPropostas();
            case 4 -> {
                if(PAInput.chooseOption("Pretende bloquear a Fase 1?", "Sim", "Não") == 1) {
                    if (!fsm.avancarFase(true))
                        System.out.println("Não é possivel bloquear a fase!");
                }
                else
                    fsm.avancarFase(false);
            }
            case 5 -> {
                if(PAInput.chooseOption("Pretende guardar o estado atual?", "Sim", "Não") == 1)
                    fsm.terminarAplicacao(PAInput.readString("Insira o nome do save: ", false));
                else
                    fsm.terminarAplicacao("");
            }
        }
    }

    private void fase1BloqueadaUI() {
        switch (PAInput.chooseOption(
                "Fase 1 - Configuração (Bloqueada)",
                "Consultar Aluno(s)",
                "Exportar alunos para ficheiro CSV",
                "Consultar Docente(s)",
                "Exportar docentes para ficheiro CSV",
                "Consultar Proposta(s)",
                "Exportar propostas para ficheiro CSV",
                "Avançar para a próxima Fase",
                "Sair"
        )){
            case 1 -> {
                String filtro = PAInput.readString(
                        "Insira o número do aluno a consultar, ou em branco para ver todos.\n",
                        false, true);
                String resultado = fsm.consultarAlunos(filtro);

                System.out.println();

                if(resultado == null && filtro.isBlank())
                    System.out.println("Não existem alunos atualmente no sistema.\n");

                else if(resultado == null && !filtro.isBlank())
                    System.out.println("Não existe um aluno com esse número.\n");
                else
                    System.out.println(resultado);
            }
            case 2 -> fsm.exportarAlunosFicheiroCsv(
                        PAInput.readString("Insira o nome do ficheiro: ", false)
                );
            case 3 -> {
                String filtro = PAInput.readString(
                        "Insira o email do docente a consultar, ou em branco para ver todos.\n",
                        false, true);
                String resultado = fsm.consultarDocentes(filtro);

                System.out.println();

                if(resultado == null && filtro.isBlank())
                    System.out.println("Não existem docentes atualmente no sistema.\n");

                else if(resultado == null && !filtro.isBlank())
                    System.out.println("Não existe um docente com esse email.\n");
                else
                    System.out.println(resultado);
            }
            case 4 ->fsm.exportarDocentesFicheiroCsv(
                        PAInput.readString("Insira o nome do ficheiro: ", false)
                );
            case 5 -> {
                String filtro = PAInput.readString(
                        "Insira o identificador da proposta a consultar, ou em branco para ver todos.\n",
                        false, true);
                String resultado = fsm.consultarPropostas(filtro);

                System.out.println();

                if(resultado == null && filtro.isBlank())
                    System.out.println("Não existem propostas atualmente no sistema.\n");

                else if(resultado == null && !filtro.isBlank())
                    System.out.println("Não existe uma proposta com esse identificador.\n");
                else
                    System.out.println(resultado);
            }
            case 6 -> fsm.exportarPropostasFicheiroCsv(
                    PAInput.readString("Insira o nome do ficheiro: ", false)
            );
            case 7 -> fsm.avancarFase(false);
            case 8 -> {
                if(PAInput.chooseOption("Pretende guardar o estado atual?", "Sim", "Não") == 1)
                    fsm.terminarAplicacao(PAInput.readString("Insira o nome do save: ", false));
                else
                    fsm.terminarAplicacao("");
            }
        }
    }

    private void gestaoAlunosUI() {
        switch (PAInput.chooseOption(
                "Gestão de Alunos",
                "Adicionar Aluno",
                "Editar Aluno",
                "Remover Aluno",
                "Consultar Aluno(s)",
                "Importar alunos de ficheiro CSV",
                "Exportar alunos para ficheiro CSV",
                "Remover todos Alunos",
                "Regressar a Fase 1"
        )){
            case 1 -> {
                //TODO: Meta 2
            }
            case 2 -> {
                //TODO: Meta 2
            }
            case 3 -> {
                //TODO: Meta 2
            }
            case 4 -> {
                String filtro = PAInput.readString(
                        "Insira o número do aluno a consultar, ou em branco para ver todos.\n",
                        false, true);
                String resultado = fsm.consultarDados(filtro);

                System.out.println();

                if(resultado == null && filtro.isBlank())
                    System.out.println("Não existem alunos atualmente no sistema.\n");

                else if(resultado == null && !filtro.isBlank())
                    System.out.println("Não existe um aluno com esse número.\n");
                else
                    System.out.println(resultado);
            }
            case 5 -> fsm.importarDadosFicheiroCsv(
                            PAInput.readString("Insira o nome do ficheiro: ", false)
                        );
            case 6 -> fsm.exportarDadosFicheiroCsv(
                            PAInput.readString("Insira o nome do ficheiro: ", false)
                        );
            case 7 -> fsm.removerTodosDados();
            case 8 -> fsm.regressarFase();
        }
    }

    private void gestaoDocentesUI() {
        switch (PAInput.chooseOption(
                "Gestão de Docentes",
                "Adicionar Docente",
                "Editar Docente",
                "Remover Docente",
                "Consultar Docente(s)",
                "Importar Docentes de ficheiro CSV",
                "Exportar Docentes para ficheiro CSV",
                "Remover todos Docentes",
                "Regressar a Fase 1"
        )){
            case 1 -> {
                //TODO: Meta 2
            }
            case 2 -> {
                //TODO: Meta 2
            }
            case 3 -> {
                //TODO: Meta 2
            }
            case 4 -> {
                String filtro = PAInput.readString(
                        "Insira o email do docente a consultar, ou em branco para ver todos.\n",
                        false, true);
                String resultado = fsm.consultarDados(filtro);

                System.out.println();

                if(resultado == null && filtro.isBlank())
                    System.out.println("Não existem docentes atualmente no sistema.\n");

                else if(resultado == null && !filtro.isBlank())
                    System.out.println("Não existe um docente com esse email.\n");
                else
                    System.out.println(resultado);
            }
            case 5 -> fsm.importarDadosFicheiroCsv(
                    PAInput.readString("Insira o nome do ficheiro: ", false)
            );
            case 6 -> fsm.exportarDadosFicheiroCsv(
                    PAInput.readString("Insira o nome do ficheiro: ", false)
            );
            case 7 -> fsm.removerTodosDados();
            case 8 -> fsm.regressarFase();
        }
    }

    private void gestaoPropostasUI() {
        switch (PAInput.chooseOption(
                "Gestão de Propostas",
                "Adicionar Proposta",
                "Editar Proposta",
                "Remover Proposta",
                "Consultar Proposta(s)",
                "Importar Propostas de ficheiro CSV",
                "Exportar Propostas para ficheiro CSV",
                "Remover todas Propostas",
                "Regressar a Fase 1"
        )){
            case 1 -> {
                //TODO: Meta 2
            }
            case 2 -> {
                //TODO: Meta 2
            }
            case 3 -> {
                //TODO: Meta 2
            }
            case 4 -> {
                String filtro = PAInput.readString(
                        "Insira o identificador da proposta a consultar, ou em branco para ver todos.\n",
                        false, true);
                String resultado = fsm.consultarDados(filtro);

                System.out.println();

                if(resultado == null && filtro.isBlank())
                    System.out.println("Não existem propostas atualmente no sistema.\n");

                else if(resultado == null && !filtro.isBlank())
                    System.out.println("Não existe uma proposta com esse identificador.\n");
                else
                    System.out.println(resultado);
            }
            case 5 -> fsm.importarDadosFicheiroCsv(
                    PAInput.readString("Insira o nome do ficheiro: ", false)
            );
            case 6 -> fsm.exportarDadosFicheiroCsv(
                    PAInput.readString("Insira o nome do ficheiro: ", false)
            );
            case 7 -> fsm.removerTodosDados();
            case 8 -> fsm.regressarFase();
        }
    }

    private void fase2UI() {
        switch (PAInput.chooseOption(
                "Fase 2 - Opções de Candidatura",
                "Gerir Candidaturas",
                "Lista Alunos",
                "Lista Propostas",
                "Regressar à fase anterior",
                "Avançar para a próxima Fase",
                "Sair"
        )){
            case 1 -> fsm.gerirDados();
            case 2 -> listaAlunosFase2();
            case 3 -> listaPropostasFase2();
            case 4 -> fsm.regressarFase();
            case 5 -> {
                if(PAInput.chooseOption("Pretende bloquear a Fase 2?", "Sim", "Não") == 1) {
                    if (!fsm.avancarFase(true))
                        System.out.println("Não é possivel bloquear a fase!");
                }
                else
                    fsm.avancarFase(false);
            }
            case 6 -> {
                if(PAInput.chooseOption("Pretende guardar o estado atual?", "Sim", "Não") == 1)
                    fsm.terminarAplicacao(PAInput.readString("Insira o nome do save: ", false));
                else
                    fsm.terminarAplicacao("");
            }
        }
    }

    private void fase2BloqueadaUI() {
        switch (PAInput.chooseOption(
                "Fase 2 - Opções de Candidatura (Bloqueada)",
                "Consultar Candidatura(s)",
                "Exportar candidaturas para ficheiro CSV",
                "Lista Alunos",
                "Lista Propostas",
                "Regressar à fase anterior",
                "Avançar para a próxima Fase",
                "Sair"
        )){
            case 1 -> {
                String filtro = PAInput.readString(
                        "Insira o número do aluno candidato a consultar, ou em branco para ver todos.\n",
                        false, true);
                String resultado = fsm.consultarCandidaturas(filtro);

                System.out.println();

                if(resultado == null && filtro.isBlank())
                    System.out.println("Não existem candidaturas atualmente no sistema.\n");

                else if(resultado == null && !filtro.isBlank())
                    System.out.println("Não existe uma candidatura para o aluno com esse número.\n");
                else
                    System.out.println(resultado);
            }
            case 2 -> fsm.exportarDadosFicheiroCsv(
                    PAInput.readString("Insira o nome do ficheiro: ", false)
                );
            case 3 -> listaAlunosFase2();
            case 4 -> listaPropostasFase2();
            case 5 -> fsm.regressarFase();
            case 6 -> fsm.avancarFase(false);
            case 7 -> {
                if(PAInput.chooseOption("Pretende guardar o estado atual?", "Sim", "Não") == 1)
                    fsm.terminarAplicacao(PAInput.readString("Insira o nome do save: ", false));
                else
                    fsm.terminarAplicacao("");
            }
        }
    }

    private void gestaoCandidaturasUI() {
        switch (PAInput.chooseOption(
                "Gestão de Candidaturas",
                "Adicionar Candidatura",
                "Editar Candidatura",
                "Remover Candidatura",
                "Consultar Candidatura(s)",
                "Importar Candidaturas de ficheiro CSV",
                "Exportar Candidaturas para ficheiro CSV",
                "Remover todas Candidaturas",
                "Regressar a Fase 2"
        )){
            case 1 -> {
                //TODO: Meta 2
            }
            case 2 -> {
                //TODO: Meta 2
            }
            case 3 -> {
                //TODO: Meta 2
            }
            case 4 -> {
                String filtro = PAInput.readString(
                        "Insira o número do aluno candidato a consultar, ou em branco para ver todos.\n",
                        false, true);
                String resultado = fsm.consultarDados(filtro);

                System.out.println();

                if(resultado == null && filtro.isBlank())
                    System.out.println("Não existem candidaturas atualmente no sistema.\n");

                else if(resultado == null && !filtro.isBlank())
                    System.out.println("Não existe uma candidatura para o aluno com esse número.\n");
                else
                    System.out.println(resultado);
            }
            case 5 -> fsm.importarDadosFicheiroCsv(
                    PAInput.readString("Insira o nome do ficheiro: ", false)
            );
            case 6 -> fsm.exportarDadosFicheiroCsv(
                    PAInput.readString("Insira o nome do ficheiro: ", false)
            );
            case 7 -> fsm.removerTodosDados();
            case 8 -> fsm.regressarFase();
        }
    }

    private void fase3UI() {
        switch (PAInput.chooseOption(
                "Fase 3 - Atribuição de propostas",
                "Atribuição automática de autopropostas e propostas de docentes",
                "Atribuição automática de propostas a alunos sem atribuição",
                "Gerir manualmente atribuição de propostas",
                "Lista Alunos",
                "Lista Propostas",
                "Exportar dados",
                "Regressar à fase anterior",
                "Avançar para a próxima Fase",
                "Sair"
        )){
            case 1 -> fsm.atribuicaoAutomaticaPropostasComAluno();
            case 2 -> fsm.atribuicaoAutomaticaPropostasDisponiveis();
            case 3 -> fsm.gerirDados();
            case 4 -> listaAlunosFase3();
            case 5 -> listaPropostasFase3();
            case 6 -> exportarDados(false);

            case 7 -> fsm.regressarFase();
            case 8 -> {
                if(PAInput.chooseOption("Pretende bloquear a Fase 3?", "Sim", "Não") == 1) {
                    if (!fsm.avancarFase(true))
                        System.out.println("Não é possivel bloquear a fase!");
                }
                else
                    fsm.avancarFase(false);
            }
            case 9 -> {
                if(PAInput.chooseOption("Pretende guardar o estado atual?", "Sim", "Não") == 1)
                    fsm.terminarAplicacao(PAInput.readString("Insira o nome do save: ", false));
                else
                    fsm.terminarAplicacao("");
            }
        }
    }

    private void fase3MasFase2AbertaStateUI(){
        switch (PAInput.chooseOption(
                "Fase 3 - Atribuição de propostas",
                "Atribuição automática de autopropostas e propostas de docentes",
                "Lista Alunos",
                "Lista Propostas",
                "Exportar dados",
                "Regressar à fase anterior",
                "Avançar para a próxima Fase",
                "Sair"
        )){
            case 1 -> fsm.atribuicaoAutomaticaPropostasComAluno();
            case 2 -> listaAlunosFase3();
            case 3 -> listaPropostasFase3();
            case 4 -> exportarDados(false);
            case 5 -> fsm.regressarFase();
            case 6 -> fsm.avancarFase(false);
            case 7 -> {
                if(PAInput.chooseOption("Pretende guardar o estado atual?", "Sim", "Não") == 1)
                    fsm.terminarAplicacao(PAInput.readString("Insira o nome do save: ", false));
                else
                    fsm.terminarAplicacao("");
            }
        }
    }
    private void fase3BloqueadaUI() {
        switch (PAInput.chooseOption(
                "Fase 3 - Atribuição de propostas (Bloqueada)",
                "Lista Alunos",
                "Lista Propostas",
                "Exportar dados",
                "Regressar à fase anterior",
                "Avançar para a próxima Fase",
                "Sair"
        )){
            case 1 -> listaAlunosFase3();
            case 2 -> listaPropostasFase3();
            case 3 -> exportarDados(false);
            case 4 -> fsm.regressarFase();
            case 5 -> fsm.avancarFase(false);
            case 6 -> {
                if(PAInput.chooseOption("Pretende guardar o estado atual?", "Sim", "Não") == 1)
                    fsm.terminarAplicacao(PAInput.readString("Insira o nome do save: ", false));
                else
                    fsm.terminarAplicacao("");
            }
        }
    }

    private void gestaoManualAtribuicoesUI() {
        switch (PAInput.chooseOption(
                "Gestão Manual de Atribuição de Propostas a Alunos",
                "Atribuir Proposta a Aluno",
                "Remover Atribuição",
                "Remover Todas Atribuições",
                "Consultar Proposta(s) Atribuída(s)",
                "Undo",
                "Redo",
                "Regressar a Fase 3"
        )){
            case 1 -> {
                if(!fsm.adicionarDados(
                        PAInput.readString("Insira o identificador da proposta a atribuir: ", false),
                        PAInput.readString("Insira o número do aluno a quem vai ser atribuida a proposta: ",
                                false)
                ))
                    System.out.println("Não foi possivel atribuir a proposta ao aluno!");
            }
            case 2 -> {
                if(!fsm.removerDados(
                        PAInput.readString("Insira o identificador da proposta atribuida a remover: ",
                                false)
                ))
                    System.out.println("Não foi possivel remover a proposta atribuida!");
            }case 3 -> fsm.removerTodosDados();
            case 4 -> {
                String filtro = PAInput.readString(
                        "Insira o identificador da proposta atribuida a consultar, " +
                                "ou em branco para ver todas as propostas atribuídas.\n",
                        false, true);
                String resultado = fsm.consultarDados(filtro);

                System.out.println();

                if(resultado == null && filtro.isBlank())
                    System.out.println("Não existem propostas atribuidas atualmente no sistema.\n");

                else if(resultado == null && !filtro.isBlank())
                    System.out.println("Não existe uma proposta atribuida com esse identificador.\n");
                else
                    System.out.println(resultado);
            }
            case 5 -> {
                if(!fsm.undo()) {
                    if (!fsm.hasUndo())
                        System.out.println("Terminaram as operações para undo!" + System.lineSeparator());
                    else
                        System.out.println("Não foi possivel fazer undo!" + System.lineSeparator());
                }
            }
            case 6 -> {
                if(!fsm.redo()) {
                    if (!fsm.hasRedo())
                        System.out.println("Terminaram as operações para redo!" + System.lineSeparator());
                    else
                        System.out.println("Não foi possivel fazer redo!" + System.lineSeparator());
                }
            }
            case 7 -> fsm.regressarFase();
        }
    }

    private void fase3AtribuicaoAutomaticaUI(){
        while (!fsm.atribuicaoAutomaticaPropostasDisponiveis()){

            switch (PAInput.chooseOption("Resolva o conflito: ", "Consultar dados alunos",
                    "Consultar propostas aluno 1", "Consultar propostas aluno 2", "Resolver Conflito")){
                case 1 -> System.out.println(fsm.consultarAlunos());
                case 2 -> System.out.println(fsm.consultarPropostas(true));
                case 3 -> System.out.println(fsm.consultarPropostas(false));
                case 4 -> fsm.adicionarDados(
                        PAInput.readString("Insira o número do aluno a atribuir a proposta: ", false),
                        PAInput.readString("Insira o identificador da proposta a atribuir: ", false));
            }

        }
        fsm.regressarFase();
    }

    private void fase4UI() {
        switch (PAInput.chooseOption(
                "Fase 4 - Atribuição de orientadores",
                "Associacao automática de docentes proponentes de projetos",
                "Gerir manualmente orientações",
                "Lista Alunos com proposta atribuída",
                "Dados Docente(s)",
                "Exportar dados",
                "Regressar à fase anterior",
                "Avançar para a próxima Fase",
                "Sair"
        )){
            case 1 -> fsm.associacaoAutomaticaDocentesProponentes();
            case 2 -> fsm.gerirDados();
            case 3 -> {
                boolean comOrientadorAssociado = PAInput.chooseOption(
                        "Com Orientador Associado: ", "Sim", "Não") == 1;

                System.out.println(fsm.consultarAlunos(comOrientadorAssociado));
            }
            case 4 -> consultarDadosDocenteFase4_5();
            case 5 -> exportarDados(true);
            case 6 -> fsm.regressarFase();
            case 7 -> {
                if(PAInput.chooseOption("Se avançar não poderá voltar atrás e alterar dados!\nDeseja continuar?",
                        "Sim", "Não") == 1)
                    fsm.avancarFase(true);
            }
            case 8 -> {
                if(PAInput.chooseOption("Pretende guardar o estado atual?", "Sim", "Não") == 1)
                    fsm.terminarAplicacao(PAInput.readString("Insira o nome do save: ", false));
                else
                    fsm.terminarAplicacao("");
            }
        }
    }

    private void gestaoManualOrientadoresUI() {
        switch (PAInput.chooseOption(
                "Gestão Manual de Orientadores a Propostas Atribuídas",
                "Adicionar Orientador",
                "Alterar Orientador",
                "Remover Orientador",
                "Consultar Proposta(s) Atribuída(s)",
                "Undo",
                "Redo",
                "Regressar a Fase 4"
        )){
            case 1 -> {
                if(!fsm.adicionarDados(
                        PAInput.readString("Insira o número do aluno com proposta atribuida: ", false),
                        PAInput.readString("Insira o email do orientador a adicionar: ", false)
                ))
                    System.out.println("Não foi possivel adicionar o orientador à proposta atribuida!");
            }

            case 2 -> {
                if(!fsm.editarDados(
                        PAInput.readString("Insira o número do aluno com proposta atribuida: ", false),
                        PAInput.readString("Insira o email do novo orientador: ", false)
                ))
                    System.out.println("Não foi possivel alterar o orientador da proposta atribuida!");
            }
            case 3 -> {
                if(!fsm.removerDados(
                        PAInput.readString("Insira o número do aluno com proposta atribuida: ", false)
                ))
                    System.out.println("Não foi possivel remover o orientador da proposta atribuida!");
            }
            case 4 -> {
                String filtro = PAInput.readString(
                        "Insira o email do orientador associado com uma proposta atribuida a consultar, " +
                                "ou em branco para ver todas as propostas atribuídas.\n",
                        false, true);
                String resultado = fsm.consultarDados(filtro);

                System.out.println();

                if(resultado == null && filtro.isBlank())
                    System.out.println("Não existem propostas atribuidas atualmente no sistema.\n");

                else if(resultado == null && !filtro.isBlank())
                    System.out.println("Não existe uma proposta atribuida com esse orientador.\n");
                else
                    System.out.println(resultado);
            }
            case 5 -> {
                if(!fsm.undo()) {
                    if (!fsm.hasUndo())
                        System.out.println("Terminaram as operações para undo!" + System.lineSeparator());
                    else
                        System.out.println("Não foi possivel fazer undo!" + System.lineSeparator());
                }
            }
            case 6 -> {
                if(!fsm.redo()) {
                    if (!fsm.hasRedo())
                        System.out.println("Terminaram as operações para redo!" + System.lineSeparator());
                    else
                        System.out.println("Não foi possivel fazer redo!" + System.lineSeparator());
                }
            }
            case 7 -> fsm.regressarFase();
        }
    }

    private void fase5UI() {
        switch (PAInput.chooseOption(
                "Fase 5 - Consulta",
                "Lista Alunos",
                "Lista Propostas",
                "Dados Docente(s)",
                "Exportar dados",
                "Sair"
        )){
            case 1 -> {
                boolean comPropostaAtribuida = false;

                if(PAInput.chooseOption("Lista de Alunos: ", "Com propostas atribuídas",
                        "Sem propostas atribuídas e com opções de candidatura") == 1)
                    comPropostaAtribuida = true;

                System.out.println(fsm.consultarAlunos(comPropostaAtribuida));
            }
            case 2 -> {
                boolean propostasAtribuidas = false;

                if(PAInput.chooseOption("Lista de propostas: ", "Atribuídas", "Disponíveis") == 1)
                    propostasAtribuidas = true;

                System.out.println(fsm.consultarPropostas(propostasAtribuidas));
            }
            case 3 -> consultarDadosDocenteFase4_5();
            case 4 -> {
                int opcao = PAInput.chooseOption("Que dados pretende exportar: ", "Alunos", "Docentes",
                        "Propostas", "Candidaturas", "Propostas Atribuidas");

                String filename = PAInput.readString("Insira o nome do ficheiro: ", false);
                switch (opcao){
                    case 1 -> fsm.exportarAlunosFicheiroCsv(filename);
                    case 2 -> fsm.exportarDocentesFicheiroCsv(filename);
                    case 3 -> fsm.exportarPropostasFicheiroCsv(filename);
                    case 4 -> fsm.exportarCandidaturasFicheiroCsv(filename);
                    case 5 -> fsm.exportarPropostasAtribuidasFicheiroCsv(filename, true);
                }
            }
            case 5 -> {
                if(PAInput.chooseOption("Pretende guardar o estado atual?", "Sim", "Não") == 1)
                    fsm.terminarAplicacao(PAInput.readString("Insira o nome do save: ", false));
                else
                    fsm.terminarAplicacao("");
            }
        }
    }

    private void exportarDados(boolean guardarOrientador) {
        int opcao = PAInput.chooseOption("Que dados pretende exportar: ", "Alunos", "Docentes",
                "Propostas", "Candidaturas", "Propostas Atribuidas");

        String filename = PAInput.readString("Insira o nome do ficheiro: ", false);
        switch (opcao){
            case 1 -> fsm.exportarAlunosFicheiroCsv(filename);
            case 2 -> fsm.exportarDocentesFicheiroCsv(filename);
            case 3 -> fsm.exportarPropostasFicheiroCsv(filename);
            case 4 -> fsm.exportarCandidaturasFicheiroCsv(filename);
            case 5 -> fsm.exportarPropostasAtribuidasFicheiroCsv(filename, guardarOrientador);
        }
    }

    private void listaPropostasFase2() {
        boolean autopropostasAlunos = false, propostasDocentes = false,
                comCandidatura = false, semCandidatura = false;

        System.out.println("Selecione um ou mais filtros: \n");

        if(PAInput.chooseOption("Autopropostas de Alunos: ", "Sim", "Não") == 1)
            autopropostasAlunos = true;

        if(PAInput.chooseOption("Propostas de Docentes: ", "Sim", "Não") == 1)
            propostasDocentes = true;

        if(PAInput.chooseOption("Com Candidatura registada: ", "Sim", "Não") == 1)
            comCandidatura = true;


        if(PAInput.chooseOption("Sem Candidatura registada: ", "Sim", "Não") == 1)
            semCandidatura = true;

        System.out.println(fsm.consultarPropostas(autopropostasAlunos, propostasDocentes,
                comCandidatura, semCandidatura));
    }

    private void listaAlunosFase2() {
        boolean autoproposta = false, comCandidatura = false, semCandidatura = false;

        switch (PAInput.chooseOption("Selecione um opção:", "Com Autoproposta",
                "Com Candidatura registada", "Sem Candidatura registada")){

            case 1 -> autoproposta = true;
            case 2 -> comCandidatura = true;
            case 3 -> semCandidatura = true;
        }

        System.out.println(fsm.consultarAlunos(autoproposta, comCandidatura, semCandidatura));
    }

    private void listaPropostasFase3() {
        boolean autopropostasAlunos = false, propostasDocentes = false,
                propostasDisponiveis = false, propostasAtribuidas = false;

        System.out.println("Selecione um ou mais filtros: \n");

        if(PAInput.chooseOption("Autopropostas de Alunos: ", "Sim", "Não") == 1)
            autopropostasAlunos = true;

        if(PAInput.chooseOption("Propostas de Docentes: ", "Sim", "Não") == 1)
            propostasDocentes = true;

        if(PAInput.chooseOption("Propostas disponiveis: ", "Sim", "Não") == 1)
            propostasDisponiveis = true;


        if(PAInput.chooseOption("Propostas atribuidas: ", "Sim", "Não") == 1)
            propostasAtribuidas = true;

        System.out.println(fsm.consultarPropostas(autopropostasAlunos, propostasDocentes,
                propostasDisponiveis, propostasAtribuidas));
    }

    private void listaAlunosFase3() {
        boolean autoproposta = false, comCandidatura = false, comPropostaAtribuida = false,
                semPropostaAtribuida = false;

        switch (PAInput.chooseOption("Selecione um opção:", "Com Autoproposta",
                "Com Candidatura registada", "Com proposta atribuida", "Sem proposta atribuida")){

            case 1 -> autoproposta = true;
            case 2 -> comCandidatura = true;
            case 3 -> comPropostaAtribuida = true;
            case 4 -> semPropostaAtribuida = true;
        }

        System.out.println(fsm.consultarAlunos(autoproposta, comCandidatura, comPropostaAtribuida,
                semPropostaAtribuida));
    }

    private void consultarDadosDocenteFase4_5() {
        String filtro = PAInput.readString(
                "Insira o email do docente a consultar, ou em branco para ver dados de todos.\n",
                false, true);
        String resultado = fsm.consultarDocentes(filtro);

        System.out.println();

        if(resultado == null && filtro.isBlank())
            System.out.println("Não existem docentes com orientações atribuidas atualmente no sistema.\n");

        else if(resultado == null && !filtro.isBlank())
            System.out.println("Este docente não existe.\n");
        else
            System.out.println(resultado);
    }
}
