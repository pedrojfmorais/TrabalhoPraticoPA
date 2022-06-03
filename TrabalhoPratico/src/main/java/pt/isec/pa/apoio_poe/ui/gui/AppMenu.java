package pt.isec.pa.apoio_poe.ui.gui;

import javafx.application.Platform;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
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

    Menu mnEdit;
    MenuItem mIUndo, miRedo;

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
        mnFile.getItems().addAll(miNew, miOpen, miSave, miExit);

        mnEdit = new Menu("Edit");
        mIUndo = new MenuItem("Undo");
        mIUndo.setAccelerator(new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN));
        miRedo = new MenuItem("Redo");
        miRedo.setAccelerator(new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN));
        mnEdit.getItems().addAll(mIUndo, miRedo);

        this.getMenus().addAll(mnFile, mnEdit);
        this.setUseSystemMenuBar(true);

    }

    private void registerHandlers() {

        fsm.addPropertyChangeListener(ApoioPoEContext.PROP_FASE, evt -> update());

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

        miExit.setOnAction(actionEvent -> Platform.exit());

        mIUndo.setOnAction(actionEvent -> fsm.undo());

        miRedo.setOnAction(actionEvent -> fsm.redo());
    }

    private void update() {

        switch (fsm.getState()){
            case FASE1:
            case FASE1_BLOQUEADA:
            case FASE2:
            case FASE2_BLOQUEADA:
            case FASE3:
            case FASE3_BLOQUEADA:
            case Fase3MasFase2AbertaState:
            case FASE4:
            case FASE5:
                miSave.setDisable(false);
                miExit.setDisable(false);
                break;
            default:
                miSave.setDisable(true);
                miExit.setDisable(true);
                break;
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

        if (!fsm.hasUndo())
            mIUndo.setDisable(true);
        else
            mIUndo.setDisable(false);

        if (!fsm.hasRedo())
            miRedo.setDisable(true);
        else
            miRedo.setDisable(false);
    }

}
