package pt.isec.pa.apoio_poe.ui.gui.fase3;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pt.isec.pa.apoio_poe.model.data.propostas.Proposta;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEContext;
import pt.isec.pa.apoio_poe.ui.gui.fase1.proposta.GerirPropostaGUI;
import pt.isec.pa.apoio_poe.ui.gui.fase1.proposta.MostraDadosPropostaGUI;

public class ListaPropostaFase3GUI extends BorderPane {
    ApoioPoEContext fsm;

    Label lbAutoproposta, lbPropostasDocentes, lbPropostasAtribuidas, lbPropostasDisponiveis;
    CheckBox ckAutoproposta, ckPropostasDocentes, ckPropostasAtribuidas, ckPropostasDisponiveis;
    TableView<Proposta> tPropostas;

    public ListaPropostaFase3GUI(ApoioPoEContext fsm) {
        this.fsm = fsm;

        createViews();
        registerHandlers();
        update();
    }

    private void createViews(){

        lbAutoproposta = new Label("Autopropostas de Alunos ");
        lbPropostasDocentes = new Label("Propostas de Docentes ");
        lbPropostasAtribuidas = new Label("Propostas Atribuídas ");
        lbPropostasDisponiveis = new Label("Propostas Disponíveis ");

        ckAutoproposta = new CheckBox();
        ckPropostasDocentes = new CheckBox();
        ckPropostasAtribuidas = new CheckBox();
        ckPropostasDisponiveis = new CheckBox();

        tPropostas = new TableView<>();

        lbAutoproposta.setLabelFor(ckAutoproposta);
        lbPropostasDocentes.setLabelFor(ckPropostasDocentes);
        lbPropostasAtribuidas.setLabelFor(ckPropostasAtribuidas);
        lbPropostasDisponiveis.setLabelFor(ckPropostasDisponiveis);

        GerirPropostaGUI.setTabelaProposta(tPropostas);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(
                new HBox(lbAutoproposta, ckAutoproposta),
                new HBox(lbPropostasDocentes, ckPropostasDocentes),
                new HBox(lbPropostasAtribuidas, ckPropostasAtribuidas),
                new HBox(lbPropostasDisponiveis, ckPropostasDisponiveis)
        );
        vBox.setAlignment(Pos.CENTER);

        for (int i = 0; i < vBox.getChildren().size(); i++) {
            HBox hBox = (HBox) vBox.getChildren().get(i);
            hBox.setAlignment(Pos.CENTER);
        }

        this.setPadding(new Insets(20));
        this.setTop(vBox);
        this.setCenter(tPropostas);
    }

    private void registerHandlers(){
        fsm.addPropertyChangeListener(ApoioPoEContext.PROP_FASE, evt -> update());
        fsm.addPropertyChangeListener(ApoioPoEContext.PROP_PROPOSTA, evt -> update());

        EventHandler<ActionEvent> eh = event -> {
            if (event.getSource() instanceof CheckBox) {
                tPropostas.getItems().clear();

                for (var proposta : fsm.consultarPropostas(
                        ckAutoproposta.isSelected(),
                        ckPropostasDocentes.isSelected(),
                        ckPropostasDisponiveis.isSelected(),
                        ckPropostasAtribuidas.isSelected()
                ))
                    tPropostas.getItems().add(proposta);
            }
        };

        ckAutoproposta.setOnAction(eh);
        ckPropostasDocentes.setOnAction(eh);
        ckPropostasAtribuidas.setOnAction(eh);
        ckPropostasDisponiveis.setOnAction(eh);

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

        ckAutoproposta.setSelected(false);
        ckPropostasDocentes.setSelected(false);
        ckPropostasAtribuidas.setSelected(false);
        ckPropostasDisponiveis.setSelected(false);

        tPropostas.getItems().clear();
        for (var proposta : fsm.getPropostas())
            tPropostas.getItems().add(proposta);
    }
}
