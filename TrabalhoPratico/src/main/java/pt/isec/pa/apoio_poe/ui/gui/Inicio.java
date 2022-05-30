package pt.isec.pa.apoio_poe.ui.gui;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEContext;

public class Inicio extends BorderPane {

    ApoioPoEContext fsm;

    Button comecarNovo, sair;
    FileChooser carregarSave;
    Label titulo, rodape;

    public Inicio(ApoioPoEContext fsm) {

        this.fsm = fsm;

        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        comecarNovo = new Button("Começar Novo");
        sair = new Button("Sair");
        titulo = new Label("Apoio PoE");
        rodape = new Label("Mariri Pedro");
        carregarSave = new FileChooser();

        comecarNovo.setPrefWidth(75);
        sair.setPrefWidth(75);

        titulo.setAlignment(Pos.CENTER);
        titulo.setPrefWidth(Integer.MAX_VALUE);
        titulo.setPadding(new Insets(20));
        titulo.setFont(new Font("Courier New", 16));

        rodape.setAlignment(Pos.CENTER);
        rodape.setPrefWidth(Integer.MAX_VALUE);
        rodape.setPadding(new Insets(20));
        rodape.setBorder(new Border(new BorderStroke(Color.DARKGREY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        rodape.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, Insets.EMPTY)));
        rodape.setFont(new Font("Courier New", 16));

        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10));
        vbox.getChildren().addAll(comecarNovo, sair);

        this.setTop(titulo);
        this.setCenter(vbox);
        this.setBottom(rodape);
    }

    private void registerHandlers() {

        comecarNovo.setOnAction(ev -> {
            fsm.comecarNovo();
            update();
        });

        sair.setOnAction(ev -> {
            Platform.exit();
        });
    }


    private void update() {

    }
}
