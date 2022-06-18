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
import pt.isec.pa.apoio_poe.ui.gui.resources.CSSManager;
import pt.isec.pa.apoio_poe.ui.gui.resources.FontManager;
import pt.isec.pa.apoio_poe.ui.gui.resources.ImageManager;

import java.io.File;

public class InicioGUI extends BorderPane {

    ApoioPoEContext fsm;

    Button btnComecarNovo, btnCarregarSave, btnSair;
    Label lbImagemISEC, lbTitulo;

    public InicioGUI(ApoioPoEContext fsm) {

        this.fsm = fsm;

        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {

        CSSManager.applyCSS(this,"backgroundColors.css");
        this.getStyleClass().add("inicio");

        btnComecarNovo = new Button("Começar Novo");
        btnCarregarSave = new Button("Carregar Save");
        btnSair = new Button("Sair");
        lbImagemISEC = new Label(null, ImageManager.getImageView("logo_isec.png", 85));
        lbTitulo = new Label("Apoio Projetos e Estágios");

        btnComecarNovo.setPrefSize(125, 50);
        btnCarregarSave.setPrefSize(100, 40);
        btnSair.setPrefWidth(75);

        lbImagemISEC.setAlignment(Pos.TOP_LEFT);
        lbImagemISEC.setPadding(new Insets(10));

        lbTitulo.setAlignment(Pos.CENTER);
        lbTitulo.setPrefWidth(Integer.MAX_VALUE);
        lbTitulo.setPadding(new Insets(0, 0, 20, 0));
        lbTitulo.setFont(FontManager.loadFont("greatvibes.otf",32));

        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10));
        vbox.getChildren().addAll(lbTitulo, btnComecarNovo, btnCarregarSave, btnSair);

        this.setTop(lbImagemISEC);
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
