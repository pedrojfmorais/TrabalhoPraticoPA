package pt.isec.pa.apoio_poe.ui.gui;

import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.FileChooser;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEContext;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEState;
import pt.isec.pa.apoio_poe.model.fsm.concreteStates.Fase2BloqueadaState;

import java.io.File;

public class AppMenu extends MenuBar {
    ApoioPoEContext fsm;

    Menu mnFile;
    MenuItem miNew, miOpen, miSave, miExit;

    Menu mnImport, mnExport;
    MenuItem miImportarAlunos, miImportarDocentes, miImportarPropostas, miImportarCandidaturas;
    MenuItem miExportarAlunos, miExportarDocentes, miExportarPropostas, miExportarCandidaturas,
            miExportarPropostasAtribuidas;

    Menu mnEdit;
    MenuItem mIUndo, miRedo, miRemoverTodosDados;

    public AppMenu(ApoioPoEContext fsm){
        this.fsm = fsm;

        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        mnFile = new Menu("File");
        miNew = new MenuItem("New");
        miNew.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));
        miOpen = new MenuItem("Open");
        miOpen.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
        miSave = new MenuItem("Save");
        miSave.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
        miExit = new MenuItem("Exit");
        miExit.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN));

        mnImport = new Menu("Import");
        mnExport = new Menu("Export");

        miImportarAlunos = new MenuItem("Alunos");
        miImportarDocentes = new MenuItem("Docentes");
        miImportarPropostas = new MenuItem("Propostas");
        miImportarCandidaturas = new MenuItem("Candidaturas");

        miExportarAlunos = new MenuItem("Alunos");
        miExportarDocentes = new MenuItem("Docentes");
        miExportarPropostas = new MenuItem("Propostas");
        miExportarCandidaturas = new MenuItem("Candidaturas");
        miExportarPropostasAtribuidas = new MenuItem("Propostas Atribuidas");

        mnImport.getItems().addAll(miImportarAlunos, miImportarDocentes, miImportarPropostas, miImportarCandidaturas);
        mnExport.getItems().addAll(miExportarAlunos, miExportarDocentes, miExportarPropostas, miExportarCandidaturas,
                miExportarPropostasAtribuidas);

        mnFile.getItems().addAll(miNew, miOpen, miSave, mnImport, mnExport, miExit);

        mnEdit = new Menu("Edit");
        mIUndo = new MenuItem("Undo");
        mIUndo.setAccelerator(new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN));
        miRedo = new MenuItem("Redo");
        miRedo.setAccelerator(new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN));
        miRemoverTodosDados = new MenuItem("Remover Todos Dados");
        mnEdit.getItems().addAll(mIUndo, miRedo, miRemoverTodosDados);

        this.getMenus().addAll(mnFile, mnEdit);
        this.setUseSystemMenuBar(true);

    }

    private void registerHandlers() {

        fsm.addPropertyChangeListener(ApoioPoEContext.PROP_FASE, evt -> update());
        fsm.addPropertyChangeListener(ApoioPoEContext.PROP_ALUNO, evt -> update());

        miNew.setOnAction(actionEvent -> fsm.comecarNovo());

        miOpen.setOnAction(actionEvent -> {

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Carregar Save ...");
            fileChooser.setInitialDirectory(new File("."));
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("All", "*")
            );

            File hFile = fileChooser.showOpenDialog(this.getScene().getWindow());

            if(hFile != null)
                fsm.carregarSave(hFile.getAbsolutePath());
        } );
        miSave.setOnAction(actionEvent -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Guardar Estado ...");
            fileChooser.setInitialDirectory(new File("."));
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("All", "*")
            );

            File hFile = fileChooser.showSaveDialog(this.getScene().getWindow());

            if(hFile != null)
                fsm.saveStateInFile(hFile.getAbsolutePath(), fsm.getState());

        } );

        miExit.setOnAction(actionEvent -> {

            if (fsm.getState() == ApoioPoEState.INICIO)
                Platform.exit();

            Alert alert = new Alert(
                    Alert.AlertType.CONFIRMATION,
                    "",
                    ButtonType.YES, ButtonType.NO
            );
            alert.setTitle("Guardar Estado");
            alert.setHeaderText("Pretende guardar o estado atual?");

            alert.showAndWait().ifPresent(response -> {
                if (response.getButtonData() == ButtonBar.ButtonData.YES) {
                    miSave.fire();
                }
                fsm.terminarAplicacao("");
            });
        });

        mIUndo.setOnAction(actionEvent -> fsm.undo());

        miRedo.setOnAction(actionEvent -> fsm.redo());

        miImportarAlunos.setOnAction(actionEvent -> {

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Importar Alunos...");
            fileChooser.setInitialDirectory(new File("."));
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("All", "*")
            );

            File hFile = fileChooser.showOpenDialog(this.getScene().getWindow());

            if(hFile != null)
                fsm.importarDadosFicheiroCsv(hFile.getAbsolutePath());
        });

        miImportarDocentes.setOnAction(actionEvent -> {

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Importar Docentes...");
            fileChooser.setInitialDirectory(new File("."));
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("All", "*")
            );

            File hFile = fileChooser.showOpenDialog(this.getScene().getWindow());

            if(hFile != null)
                fsm.importarDadosFicheiroCsv(hFile.getAbsolutePath());
        });

        miImportarPropostas.setOnAction(actionEvent -> {

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Importar Propostas...");
            fileChooser.setInitialDirectory(new File("."));
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("All", "*")
            );

            File hFile = fileChooser.showOpenDialog(this.getScene().getWindow());

            if(hFile != null)
                fsm.importarDadosFicheiroCsv(hFile.getAbsolutePath());
        });

        miImportarCandidaturas.setOnAction(actionEvent -> {

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Importar Candidaturas...");
            fileChooser.setInitialDirectory(new File("."));
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("All", "*")
            );

            File hFile = fileChooser.showOpenDialog(this.getScene().getWindow());

            if(hFile != null)
                fsm.importarDadosFicheiroCsv(hFile.getAbsolutePath());
        });

        miExportarAlunos.setOnAction(actionEvent -> {

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Exportar Alunos...");
            fileChooser.setInitialDirectory(new File("."));
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("All", "*")
            );

            File hFile = fileChooser.showSaveDialog(this.getScene().getWindow());

            if(hFile != null)
                if(fsm.getState() == ApoioPoEState.GESTAO_ALUNOS)
                    fsm.exportarDadosFicheiroCsv(hFile.getAbsolutePath());
                else
                    fsm.exportarAlunosFicheiroCsv(hFile.getAbsolutePath());
        });

        miExportarDocentes.setOnAction(actionEvent -> {

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Exportar Docentes...");
            fileChooser.setInitialDirectory(new File("."));
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("All", "*")
            );

            File hFile = fileChooser.showSaveDialog(this.getScene().getWindow());

            if(hFile != null)
                if(fsm.getState() == ApoioPoEState.GESTAO_DOCENTES)
                    fsm.exportarDadosFicheiroCsv(hFile.getAbsolutePath());
                else
                    fsm.exportarDocentesFicheiroCsv(hFile.getAbsolutePath());
        });

        miExportarPropostas.setOnAction(actionEvent -> {

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Exportar Propostas...");
            fileChooser.setInitialDirectory(new File("."));
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("All", "*")
            );

            File hFile = fileChooser.showSaveDialog(this.getScene().getWindow());

            if(hFile != null)
                if(fsm.getState() == ApoioPoEState.GESTAO_PROPOSTAS)
                    fsm.exportarDadosFicheiroCsv(hFile.getAbsolutePath());
                else
                    fsm.exportarPropostasFicheiroCsv(hFile.getAbsolutePath());
        });

        miExportarCandidaturas.setOnAction(actionEvent -> {

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Exportar Candidaturas...");
            fileChooser.setInitialDirectory(new File("."));
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("All", "*")
            );

            File hFile = fileChooser.showSaveDialog(this.getScene().getWindow());

            if(hFile != null)
                if(fsm.getState() == ApoioPoEState.GESTAO_CANDIDATURAS ||
                        fsm.getState() == ApoioPoEState.FASE2_BLOQUEADA)
                    fsm.exportarDadosFicheiroCsv(hFile.getAbsolutePath());
                else
                    fsm.exportarPropostasFicheiroCsv(hFile.getAbsolutePath());
        });

        miExportarPropostasAtribuidas.setOnAction(actionEvent -> {

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Exportar Propostas Atribuidas...");
            fileChooser.setInitialDirectory(new File("."));
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("All", "*")
            );

            File hFile = fileChooser.showSaveDialog(this.getScene().getWindow());

            if(hFile != null)
                fsm.exportarPropostasAtribuidasFicheiroCsv(hFile.getAbsolutePath(),
                        fsm.getState() == ApoioPoEState.FASE4 || fsm.getState() == ApoioPoEState.FASE5);
        });

        miRemoverTodosDados.setOnAction(actionEvent -> fsm.removerTodosDados());
    }

    private void update() {

        switch (fsm.getState()) {
            case FASE1, FASE1_BLOQUEADA, FASE2, FASE2_BLOQUEADA, FASE3,
                    FASE3_BLOQUEADA, Fase3MasFase2AbertaState, FASE4, FASE5 -> {
                miSave.setDisable(false);
                miExit.setDisable(false);
            }
            default -> {
                miSave.setDisable(true);
                miExit.setDisable(true);
            }
        }

        if(fsm.getState() == ApoioPoEState.INICIO) {
            miNew.setDisable(false);
            miOpen.setDisable(false);
            miExit.setDisable(false);
        }
        else {
            miNew.setDisable(true);
            miOpen.setDisable(true);
        }

        mIUndo.setDisable(!fsm.hasUndo());

        miRedo.setDisable(!fsm.hasRedo());

        miImportarAlunos.setDisable(fsm.getState() != ApoioPoEState.GESTAO_ALUNOS);
        miImportarDocentes.setDisable(fsm.getState() != ApoioPoEState.GESTAO_DOCENTES);
        miImportarPropostas.setDisable(fsm.getState() != ApoioPoEState.GESTAO_PROPOSTAS);
        miImportarCandidaturas.setDisable(fsm.getState() != ApoioPoEState.GESTAO_CANDIDATURAS);

        switch (fsm.getState()){
            case FASE3, Fase3MasFase2AbertaState, FASE3_BLOQUEADA, FASE4, FASE5 -> {
                miExportarAlunos.setDisable(false);
                miExportarDocentes.setDisable(false);
                miExportarPropostas.setDisable(false);
                miExportarCandidaturas.setDisable(false);
                miExportarPropostasAtribuidas.setDisable(false);
            }
            default -> {
                miExportarAlunos.setDisable(true);
                miExportarDocentes.setDisable(true);
                miExportarPropostas.setDisable(true);
                miExportarCandidaturas.setDisable(true);
                miExportarPropostasAtribuidas.setDisable(true);
            }
        }

        miExportarAlunos.setDisable(fsm.getState() != ApoioPoEState.GESTAO_ALUNOS &&
                fsm.getState() != ApoioPoEState.FASE1_BLOQUEADA);
        miExportarDocentes.setDisable(fsm.getState() != ApoioPoEState.GESTAO_DOCENTES &&
                fsm.getState() != ApoioPoEState.FASE1_BLOQUEADA);
        miExportarPropostas.setDisable(fsm.getState() != ApoioPoEState.GESTAO_PROPOSTAS &&
                fsm.getState() != ApoioPoEState.FASE1_BLOQUEADA);
        miExportarCandidaturas.setDisable(fsm.getState() != ApoioPoEState.GESTAO_CANDIDATURAS &&
                fsm.getState() != ApoioPoEState.FASE2_BLOQUEADA);

        switch (fsm.getState()){
            case GESTAO_ALUNOS, GESTAO_DOCENTES, GESTAO_PROPOSTAS, GESTAO_CANDIDATURAS, GESTAO_MANUAL_ATRIBUICOES ->
                    miRemoverTodosDados.setDisable(false);
            default -> miRemoverTodosDados.setDisable(true);
        }
    }
}
