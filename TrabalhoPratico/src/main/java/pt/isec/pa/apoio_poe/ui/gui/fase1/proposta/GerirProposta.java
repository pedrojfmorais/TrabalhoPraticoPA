package pt.isec.pa.apoio_poe.ui.gui.fase1.proposta;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
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
import javafx.util.Callback;
import pt.isec.pa.apoio_poe.model.data.propostas.*;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEContext;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEState;
import pt.isec.pa.apoio_poe.ui.gui.mostraDados.AbreMostraDados;
import pt.isec.pa.apoio_poe.ui.gui.resources.ImageManager;

public class GerirProposta extends BorderPane {
    ApoioPoEContext fsm;

    Button btnAdicionar, btnEditar, btnEliminar, btnProcurar, btnRegressarFase;

    TableView<Proposta> tProposta;
    TextField tfFiltros;

    public GerirProposta(ApoioPoEContext fsm){
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

        tProposta = new TableView<>();
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

        setTabelaProposta(tProposta);

        VBox vBox = new VBox(tProposta, btnRegressarFase);
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);

        this.setPadding(new Insets(20));
        this.setCenter(vBox);
    }

    private void registerHandlers(){
        fsm.addPropertyChangeListener(ApoioPoEContext.PROP_FASE, evt -> update());
        fsm.addPropertyChangeListener(ApoioPoEContext.PROP_PROPOSTA, evt -> update());

        btnRegressarFase.setOnAction(actionEvent -> fsm.regressarFase());

        btnAdicionar.setOnAction(actionEvent -> {
            Stage dialog = new Stage();

            dialog.setTitle("Adicionar Proposta");

            dialog.initOwner(this.getScene().getWindow());
            dialog.initModality(Modality.APPLICATION_MODAL);


            dialog.setScene(new Scene(new AdicionarOuEditarProposta(fsm)));
            dialog.setResizable(false);

            dialog.showAndWait();
        });

        btnEditar.setOnAction(actionEvent -> {

            if(tProposta.getSelectionModel().getSelectedItem() == null)
                return;

            Stage dialog = new Stage();

            dialog.setTitle("Editar Proposta");

            AdicionarOuEditarProposta aeProposta = new AdicionarOuEditarProposta(fsm);
            aeProposta.setUserData(tProposta.getSelectionModel().getSelectedItem());

            dialog.initOwner(this.getScene().getWindow());
            dialog.initModality(Modality.APPLICATION_MODAL);

            dialog.setScene(new Scene(aeProposta));
            dialog.setResizable(false);

            aeProposta.setData();

            dialog.showAndWait();
        });

        btnEliminar.setOnAction(actionEvent -> {
            Proposta proposta = tProposta.getSelectionModel().getSelectedItem();
            if(proposta != null)
                fsm.removerDados(proposta.getId());
        });

        tProposta.setRowFactory( tv -> {
            TableRow<Proposta> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    AbreMostraDados.abreMostraDadosProposta(fsm, row.getItem(), (Stage) this.getScene().getWindow());
                }
            });
            return row ;
        });

        tfFiltros.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER)
                btnProcurar.fire();
        });

        btnProcurar.setOnAction(actionEvent -> {

            procurarProposta(tfFiltros, tProposta, fsm);
        });
    }

    private void update(){
        this.setVisible(fsm != null && fsm.getState() == ApoioPoEState.GESTAO_PROPOSTAS);

        tProposta.getItems().clear();
        for (var proposta : fsm.getPropostas())
            tProposta.getItems().add(proposta);
    }

    public static void procurarProposta(TextField tfFiltros, TableView<Proposta> tProposta, ApoioPoEContext fsm) {
        String idProposta;

        if(tfFiltros.getText().isBlank()){
            tProposta.getItems().clear();
            for (var proposta : fsm.getPropostas())
                tProposta.getItems().add(proposta);
            return;
        }

        idProposta = tfFiltros.getText();

        Proposta proposta = fsm.getProposta(idProposta);
        if(proposta != null) {
            tProposta.getItems().clear();
            tProposta.getItems().add(proposta);

            tfFiltros.setText("");
        }
    }

    public static void setTabelaProposta(TableView<Proposta> tProposta) {
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
            else if(p.getValue() instanceof PropostaAtribuida)
                return new ReadOnlyObjectWrapper<>("Atribuída");
            return new ReadOnlyObjectWrapper<>("");
        });

        tcId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        tProposta.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tProposta.getColumns().clear();
        tProposta.getColumns().addAll(tcTipo,tcId,tcTitulo);
        tProposta.setPlaceholder(new Label("Ainda não foram inseridos Propostas"));
    }
}
