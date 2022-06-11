package pt.isec.pa.apoio_poe.ui.gui.Fase2;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import pt.isec.pa.apoio_poe.model.data.propostas.Autoproposto;
import pt.isec.pa.apoio_poe.model.data.propostas.Estagio;
import pt.isec.pa.apoio_poe.model.data.propostas.Projeto;
import pt.isec.pa.apoio_poe.model.data.propostas.Proposta;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEContext;
import pt.isec.pa.apoio_poe.ui.gui.Fase1.proposta.MostraDadosProposta;

public class ListaProposta extends BorderPane {
    ApoioPoEContext fsm;

    Label lbAutoproposta, lbPropostasDocentes, lbComCandidatura, lbSemCandidatura;
    CheckBox ckAutoproposta, ckPropostasDocentes, ckComCandidatura, ckSemCandidatura;
    TableView<Proposta> tPropostas;

    public ListaProposta(ApoioPoEContext fsm) {
        this.fsm = fsm;

        createViews();
        registerHandlers();
        update();
    }

    private void createViews(){

        lbAutoproposta = new Label("Autopropostas de Alunos ");
        lbPropostasDocentes = new Label("Propostas de Docentes ");
        lbComCandidatura = new Label("Com Candidatura registada ");
        lbSemCandidatura = new Label("Sem Candidatura registada ");

        ckAutoproposta = new CheckBox();
        ckPropostasDocentes = new CheckBox();
        ckComCandidatura = new CheckBox();
        ckSemCandidatura = new CheckBox();

        tPropostas = new TableView<>();

        lbAutoproposta.setLabelFor(ckAutoproposta);
        lbPropostasDocentes.setLabelFor(ckPropostasDocentes);
        lbComCandidatura.setLabelFor(ckComCandidatura);
        lbSemCandidatura.setLabelFor(ckSemCandidatura);

        TableColumn tcTipo = new TableColumn("Tipo");
        TableColumn tcId = new TableColumn("Identificador");
        TableColumn tcTitulo = new TableColumn("Titulo");

        tcTipo.setCellValueFactory((Callback<TableColumn.CellDataFeatures<Proposta, String>, ObservableValue<String>>) p -> {
            if(p.getValue() instanceof Projeto)
                return new ReadOnlyObjectWrapper<>("Projeto");
            else if(p.getValue() instanceof Estagio)
                return new ReadOnlyObjectWrapper<>("Estágio");
            else if(p.getValue() instanceof Autoproposto)
                return new ReadOnlyObjectWrapper<>("Autoproposto");
            return new ReadOnlyObjectWrapper<>("");
        });

        tcId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        tPropostas.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tPropostas.getColumns().clear();
        tPropostas.getColumns().addAll(tcTipo,tcId,tcTitulo);
        tPropostas.setPlaceholder(new Label("Ainda não foram inseridos Propostas"));

        VBox vBox = new VBox();
        vBox.getChildren().addAll(
                new HBox(lbAutoproposta, ckAutoproposta),
                new HBox(lbPropostasDocentes, ckPropostasDocentes),
                new HBox(lbComCandidatura, ckComCandidatura),
                new HBox(lbSemCandidatura, ckSemCandidatura)
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
        fsm.addPropertyChangeListener(ApoioPoEContext.PROP_ALUNO, evt -> update());

        EventHandler<ActionEvent> eh = event -> {
            if (event.getSource() instanceof CheckBox) {
                tPropostas.getItems().clear();
                for (var aluno : fsm.consultarPropostas(
                        ckAutoproposta.isSelected(),
                        ckPropostasDocentes.isSelected(),
                        ckComCandidatura.isSelected(),
                        ckSemCandidatura.isSelected())
                )
                    tPropostas.getItems().add(aluno);
            }
        };

        ckAutoproposta.setOnAction(eh);
        ckPropostasDocentes.setOnAction(eh);
        ckComCandidatura.setOnAction(eh);
        ckSemCandidatura.setOnAction(eh);

        tPropostas.setRowFactory( tv -> {
            TableRow<Proposta> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Stage dialog = new Stage();

                    dialog.setTitle("Informações Proposta");

                    MostraDadosProposta mProposta = new MostraDadosProposta(fsm);
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
        ckComCandidatura.setSelected(false);
        ckSemCandidatura.setSelected(false);

        tPropostas.getItems().clear();
        for (var proposta : fsm.getPropostas())
            tPropostas.getItems().add(proposta);
    }
}
