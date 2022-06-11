package pt.isec.pa.apoio_poe.ui.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Rodape extends HBox {

    Label lbRodape;

    public Rodape(){

        createViews();
        registerHandlers();
        update();
    }
    private void createViews() {


        lbRodape = new Label("Maria Abreu - Pedro Morais");

        lbRodape.setAlignment(Pos.CENTER);
        lbRodape.setPrefWidth(Integer.MAX_VALUE);
        lbRodape.setPadding(new Insets(20));
        lbRodape.setBorder(new Border(new BorderStroke(Color.DARKGREY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        lbRodape.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, Insets.EMPTY)));
        lbRodape.setFont(new Font("Courier New", 16));

        this.getChildren().add(lbRodape);
    }

    private void registerHandlers() {
    }

    private void update() {
    }
}
