package pt.isec.pa.apoio_poe.ui.gui.fase2.candidatura;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pt.isec.pa.apoio_poe.model.data.Candidatura;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEContext;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEState;
import pt.isec.pa.apoio_poe.ui.gui.AbreMostraDadosGUI;
import pt.isec.pa.apoio_poe.ui.gui.resources.*;

import java.util.List;

public class GerirCandidaturaGUI extends BorderPane {
    ApoioPoEContext fsm;

    Button btnAdicionar, btnEditar, btnEliminar, btnProcurar, btnRegressarFase;

    TableView<Candidatura> tCandidatura;
    TextField tfFiltros;

    public GerirCandidaturaGUI(ApoioPoEContext fsm){
        this.fsm = fsm;

        createViews();
        registerHandlers();
        update();
    }

    private void createViews(){
        btnAdicionar = new Button("Adicionar");
        btnEditar = new Button("Editar");
        btnEliminar = new Button("Eliminar");
        btnProcurar = new Button(null, ImageManager.getImageView("lupa.png",20));
        btnRegressarFase = new Button("Regressar");

        tCandidatura = new TableView<>();
        tfFiltros = new TextField();

        btnAdicionar.setPrefSize(125, 50);
        btnEditar.setPrefSize(125, 50);
        btnEliminar.setPrefSize(125, 50);
        btnProcurar.setPrefSize(30,30);
        btnProcurar.setMinWidth(30);

        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(10);
        hbox.setPadding(new Insets(10));
        hbox.getChildren().addAll(btnAdicionar, btnEditar, btnEliminar, tfFiltros, btnProcurar);
        this.setTop(hbox);

        inicializarColunasCandidaturas(tCandidatura);

        VBox vBox = new VBox(tCandidatura, btnRegressarFase);
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);

        this.setPadding(new Insets(20));
        this.setCenter(vBox);
    }

    private void registerHandlers(){
        fsm.addPropertyChangeListener(ApoioPoEContext.PROP_FASE, evt -> update());
        fsm.addPropertyChangeListener(ApoioPoEContext.PROP_CANDIDATURA, evt -> update());

        btnRegressarFase.setOnAction(actionEvent -> fsm.regressarFase());

        btnAdicionar.setOnAction(actionEvent -> {
            Stage dialog = new Stage();

            dialog.setTitle("Adicionar Candidatura");

            dialog.initOwner(this.getScene().getWindow());
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.setHeight(300);


            dialog.setScene(new Scene(new AdicionarOuEditarCandidaturaGUI(fsm)));
            dialog.setResizable(false);

            dialog.showAndWait();
        });

        btnEditar.setOnAction(actionEvent -> {

            if(tCandidatura.getSelectionModel().getSelectedItem() == null)
                return;

            Stage dialog = new Stage();

            dialog.setTitle("Editar Candidatura");

            AdicionarOuEditarCandidaturaGUI aeCandidatura = new AdicionarOuEditarCandidaturaGUI(fsm);
            aeCandidatura.setUserData(tCandidatura.getSelectionModel().getSelectedItem());

            dialog.initOwner(this.getScene().getWindow());
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.setHeight(300);

            dialog.setScene(new Scene(aeCandidatura));
            dialog.setResizable(false);

            aeCandidatura.setData();

            dialog.showAndWait();
        });

        btnEliminar.setOnAction(actionEvent -> {
            Candidatura candidatura = tCandidatura.getSelectionModel().getSelectedItem();
            if(candidatura != null)
                fsm.removerDados(String.valueOf(candidatura.getNAluno()));
        });

        pesquisarCandidaturas(tfFiltros, btnProcurar, tCandidatura, fsm);
    }

    private void update(){
        if(fsm != null && fsm.getState() == ApoioPoEState.GESTAO_CANDIDATURAS){
            this.setVisible(true);
            AbreMostraDadosGUI.abreResumoCandidaturas(fsm, (Stage) this.getScene().getWindow());
        } else
            this.setVisible(false);

        tCandidatura.getItems().clear();
        for (var candidatura : fsm.getCandidaturas())
            tCandidatura.getItems().add(candidatura);
    }

    public static void inicializarColunasCandidaturas(TableView<Candidatura> tCandidatura) {
        TableColumn<Candidatura,String> tcNAluno = new TableColumn("Número Aluno");
        tcNAluno.setCellValueFactory(new PropertyValueFactory<>("nAluno"));
        TableColumn<Candidatura, List<String>> tcPropostas = new TableColumn("Propostas da Candidatura");
        tcPropostas.setCellValueFactory(new PropertyValueFactory<>("idPropostas"));

        tCandidatura.setPlaceholder(new Label("Ainda não foram inseridas Candidaturas"));
        tCandidatura.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tCandidatura.getColumns().clear();
        tCandidatura.getColumns().addAll(tcNAluno,tcPropostas);
    }

    public static void pesquisarCandidaturas(TextField tfFiltros, Button btnProcurar, TableView<Candidatura> tCandidatura, ApoioPoEContext fsm) {
        tfFiltros.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER)
                btnProcurar.fire();
        });

        btnProcurar.setOnAction(actionEvent -> {

            long nAluno;

            if(tfFiltros.getText().isBlank()){
                tCandidatura.getItems().clear();
                for (var candidatura : fsm.getCandidaturas())
                    tCandidatura.getItems().add(candidatura);
                return;
            }

            try {
                nAluno = Long.parseLong(tfFiltros.getText());
            } catch (NumberFormatException e){
                return;
            }

            Candidatura candidatura = fsm.getCandidatura(nAluno);
            if(candidatura != null) {
                tCandidatura.getItems().clear();
                tCandidatura.getItems().add(candidatura);

                tfFiltros.setText("");
            }
        });
    }
}
