package pt.isec.pa.apoio_poe.ui.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import pt.isec.pa.apoio_poe.ui.gui.resources.CSSManager;
import pt.isec.pa.apoio_poe.ui.gui.resources.FontManager;

public class RodapeGUI extends HBox {

    Label lbRodape;

    public RodapeGUI(){

        createViews();
        registerHandlers();
        update();
    }
    private void createViews() {

        CSSManager.applyCSS(this,"backgroundColors.css");
        this.getStyleClass().add("rodape");


        lbRodape = new Label("Maria Abreu - Pedro Morais");

        lbRodape.setFont(FontManager.loadFont("greatvibes.otf",26));

        lbRodape.setAlignment(Pos.CENTER);
        lbRodape.setPrefWidth(Integer.MAX_VALUE);
        lbRodape.setPadding(new Insets(20));
        lbRodape.setBorder(new Border(new BorderStroke(Color.DARKGREY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        this.getChildren().add(lbRodape);
    }

    private void registerHandlers() {
    }

    private void update() {
    }
}
