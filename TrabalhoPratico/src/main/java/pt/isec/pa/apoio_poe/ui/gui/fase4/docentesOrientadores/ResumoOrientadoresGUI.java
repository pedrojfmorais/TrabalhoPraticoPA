package pt.isec.pa.apoio_poe.ui.gui.fase4.docentesOrientadores;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEContext;

public class ResumoOrientadoresGUI extends BorderPane {
    ApoioPoEContext fsm;

    Label lbPropostasAtribuidasComOrientador, lbPropostasAtribuidasSemOrientador;
    Label lbPropostasAtribuidasComOrientadorDados, lbPropostasAtribuidasSemOrientadorDadps;

    public ResumoOrientadoresGUI(ApoioPoEContext fsm){
        this.fsm = fsm;

        createViews();
        registerHandlers();
        update();
    }

    private void createViews(){
        lbPropostasAtribuidasComOrientador = new Label("Propostas Atribuídas com Orientador: ");
        lbPropostasAtribuidasSemOrientador = new Label("Propostas Atribuídas sem Orientador: ");

        lbPropostasAtribuidasComOrientadorDados = new Label();
        lbPropostasAtribuidasSemOrientadorDadps = new Label();

        VBox vBox = new VBox(
                new HBox(lbPropostasAtribuidasComOrientador, lbPropostasAtribuidasComOrientadorDados),
                new HBox(lbPropostasAtribuidasSemOrientador, lbPropostasAtribuidasSemOrientadorDadps)
        );
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);

        for (int i = 0; i < vBox.getChildren().size(); i++) {
            HBox hBox = (HBox)  vBox.getChildren().get(i);
            hBox.setAlignment(Pos.CENTER);
        }

        this.setPadding(new Insets(20));
        this.setCenter(vBox);
    }

    private void registerHandlers(){
        fsm.addPropertyChangeListener(ApoioPoEContext.PROP_PROPOSTA, evt -> update());
        fsm.addPropertyChangeListener(ApoioPoEContext.PROP_PROPOSTA_ATRIBUIDA, evt -> update());
    }

    private void update(){
        lbPropostasAtribuidasComOrientadorDados.setText(String.valueOf(fsm.getNumPropotasAtribuidasComOrientador()));
        lbPropostasAtribuidasSemOrientadorDadps.setText(String.valueOf(fsm.getPropostasAtribuidas().size()-fsm.getNumPropotasAtribuidasComOrientador()));
    }
}
