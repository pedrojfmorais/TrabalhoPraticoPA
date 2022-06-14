package pt.isec.pa.apoio_poe.ui.gui.fase5;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pt.isec.pa.apoio_poe.model.data.propostas.Proposta;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEContext;
import pt.isec.pa.apoio_poe.ui.gui.fase1.proposta.GerirPropostaGUI;
import pt.isec.pa.apoio_poe.ui.gui.fase1.proposta.MostraDadosPropostaGUI;

import java.util.ArrayList;

public class ListaPropostaFase5GUI extends BorderPane {
    ApoioPoEContext fsm;

    Label lbPropostasAtribuidas, lbPropostasDisponiveis;
    RadioButton rbPropostasAtribuidas, rbPropostasDisponiveis;
    TableView<Proposta> tPropostas;
    ToggleGroup tg;

    public ListaPropostaFase5GUI(ApoioPoEContext fsm) {
        this.fsm = fsm;

        createViews();
        registerHandlers();
        update();
    }

    private void createViews(){

        lbPropostasAtribuidas = new Label("Propostas Atribuídas ");
        lbPropostasDisponiveis = new Label("Propostas Disponíveis ");

        rbPropostasAtribuidas = new RadioButton();
        rbPropostasDisponiveis = new RadioButton();

        tPropostas = new TableView<>();

        lbPropostasAtribuidas.setLabelFor(rbPropostasAtribuidas);
        lbPropostasDisponiveis.setLabelFor(rbPropostasDisponiveis);

        GerirPropostaGUI.setTabelaProposta(tPropostas);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(
                new HBox(lbPropostasAtribuidas, rbPropostasAtribuidas),
                new HBox(lbPropostasDisponiveis, rbPropostasDisponiveis)
        );
        vBox.setAlignment(Pos.CENTER);

        for (int i = 0; i < vBox.getChildren().size(); i++) {
            HBox hBox = (HBox) vBox.getChildren().get(i);
            hBox.setAlignment(Pos.CENTER);
        }

        tg = new ToggleGroup();
        rbPropostasAtribuidas.setToggleGroup(tg);
        rbPropostasDisponiveis.setToggleGroup(tg);

        this.setPadding(new Insets(20));
        this.setTop(vBox);
        this.setCenter(tPropostas);
    }

    private void registerHandlers(){
        fsm.addPropertyChangeListener(ApoioPoEContext.PROP_FASE, evt -> update());
        fsm.addPropertyChangeListener(ApoioPoEContext.PROP_PROPOSTA, evt -> update());

        tg.selectedToggleProperty().addListener((observable, oldVal, newVal) -> {
            tPropostas.getItems().clear();
            ArrayList<Proposta> propostas = fsm.consultarPropostas(rbPropostasAtribuidas.isSelected());
            if(propostas != null)
                for (var proposta : propostas)
                    tPropostas.getItems().add(proposta);
        });

        tPropostas.setRowFactory( tv -> {
            TableRow<Proposta> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Stage dialog = new Stage();

                    dialog.setTitle("Informações Proposta");

                    MostraDadosPropostaGUI mProposta = new MostraDadosPropostaGUI(fsm);
                    mProposta.setUserData(row.getItem());

                    dialog.initOwner(this.getScene().getWindow());
                    dialog.initModality(Modality.APPLICATION_MODAL);

                    dialog.setScene(new Scene(mProposta));
                    dialog.setResizable(false);

                    mProposta.setData();

                    dialog.showAndWait();
                }
            });
            return row ;
        });
    }

    private void update(){
        rbPropostasAtribuidas.setSelected(true);
    }
}
