package pt.isec.pa.apoio_poe.ui.gui;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEContext;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEState;

import java.io.File;

public class Inicio extends BorderPane {

    ApoioPoEContext fsm;

    Button btnComecarNovo, btnCarregarSave, btnSair;
    FileChooser fcCarregarSave;
    Label lbTitulo;

    public Inicio(ApoioPoEContext fsm) {

        this.fsm = fsm;

        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        btnComecarNovo = new Button("Começar Novo");
        btnCarregarSave = new Button("Carregar Save");
        btnSair = new Button("Sair");
        lbTitulo = new Label("Apoio PoE");

        fcCarregarSave = new FileChooser();

        btnComecarNovo.setPrefWidth(75);
        btnSair.setPrefWidth(75);

        lbTitulo.setAlignment(Pos.CENTER);
        lbTitulo.setPrefWidth(Integer.MAX_VALUE);
        lbTitulo.setPadding(new Insets(20));
        lbTitulo.setFont(new Font("Courier New", 16));

        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10));
        vbox.getChildren().addAll(btnComecarNovo, btnCarregarSave, btnSair);

        this.setTop(lbTitulo);
        this.setCenter(vbox);
    }

    private void registerHandlers() {

        btnComecarNovo.setOnAction(ev -> {
            fsm.comecarNovo();
            update();
        });

        btnCarregarSave.setOnAction(e -> {
            File selectedFile = fcCarregarSave.showOpenDialog(new Stage());
            fsm.carregarSave(selectedFile.getAbsolutePath());
            update();
        });

        btnSair.setOnAction(ev -> Platform.exit());
    }


    private void update() {
        this.setVisible(fsm != null && fsm.getState() == ApoioPoEState.INICIO);
    }
}
