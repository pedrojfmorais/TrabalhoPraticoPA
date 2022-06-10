package pt.isec.pa.apoio_poe.ui.gui;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEContext;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEState;

import java.io.File;

public class Inicio extends BorderPane {

    ApoioPoEContext fsm;

    Button btnComecarNovo, btnCarregarSave, btnSair;
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

        btnComecarNovo.setPrefSize(125, 50);
        btnCarregarSave.setPrefSize(100, 40);
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
        fsm.addPropertyChangeListener(ApoioPoEContext.PROP_FASE, evt -> update());

        btnComecarNovo.setOnAction(ev -> fsm.comecarNovo());

        btnCarregarSave.setOnAction(e -> {

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Carregar Save ...");
            fileChooser.setInitialDirectory(new File("."));
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("All", "*")
            );

            File hFile = fileChooser.showOpenDialog(this.getScene().getWindow());

            if(hFile != null)
                fsm.carregarSave(hFile.getAbsolutePath());
        });

        btnSair.setOnAction(ev -> Platform.exit());
    }

    private void update() {
        this.setVisible(fsm != null && fsm.getState() == ApoioPoEState.INICIO);
    }
}
