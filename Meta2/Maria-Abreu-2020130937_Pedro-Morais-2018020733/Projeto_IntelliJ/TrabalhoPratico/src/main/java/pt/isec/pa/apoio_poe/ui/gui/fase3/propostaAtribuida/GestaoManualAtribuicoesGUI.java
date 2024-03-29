package pt.isec.pa.apoio_poe.ui.gui.fase3.propostaAtribuida;

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

import pt.isec.pa.apoio_poe.model.data.propostas.PropostaAtribuida;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEContext;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEState;
import pt.isec.pa.apoio_poe.ui.gui.AbreMostraDadosGUI;
import pt.isec.pa.apoio_poe.ui.gui.resources.CSSManager;
import pt.isec.pa.apoio_poe.ui.gui.resources.ImageManager;

import java.util.List;

public class GestaoManualAtribuicoesGUI extends BorderPane {

    ApoioPoEContext fsm;
    Stage resumo;
    Button btnAdicionar, btnEliminar, btnProcurar, btnRegressarFase;

    TableView<PropostaAtribuida> tPropostasAtribuidas;
    TextField tfFiltros;

    public GestaoManualAtribuicoesGUI(ApoioPoEContext fsm){
        this.fsm = fsm;

        createViews();
        registerHandlers();
        update();
    }

    private void createViews(){
        resumo = new Stage();
        resumo.getIcons().add(ImageManager.getImage("mini_logo_isec.png"));

        CSSManager.applyCSS(this,"backgroundColors.css");

        btnAdicionar = new Button("Adicionar");
        btnEliminar = new Button("Eliminar");
        btnProcurar = new Button(null, ImageManager.getImageView("lupa.png",20));
        btnRegressarFase = new Button("Regressar");

        btnAdicionar.getStyleClass().add("btnAdicionar");
        btnEliminar.getStyleClass().add("btnEliminar");
        this.getStyleClass().add("gestaoBG");

        tPropostasAtribuidas = new TableView<>();
        tfFiltros = new TextField();

        btnAdicionar.setPrefSize(125, 50);
        btnEliminar.setPrefSize(125, 50);
        btnProcurar.setPrefSize(30,30);
        btnProcurar.setMinWidth(30);

        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(10);
        hbox.setPadding(new Insets(10));
        hbox.getChildren().addAll(btnAdicionar, btnEliminar, tfFiltros, btnProcurar);
        this.setTop(hbox);

        TableColumn<PropostaAtribuida, List<String>> tcIdProposta = new TableColumn("Proposta");
        tcIdProposta.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<PropostaAtribuida,String> tcNAluno = new TableColumn("Número Aluno");
        tcNAluno.setCellValueFactory(new PropertyValueFactory<>("nAlunoAssociado"));

        tPropostasAtribuidas.setPlaceholder(new Label("Ainda não Existem Propostas Atribuidas"));
        tPropostasAtribuidas.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tPropostasAtribuidas.getColumns().clear();
        tPropostasAtribuidas.getColumns().addAll(tcIdProposta, tcNAluno);

        VBox vBox = new VBox(tPropostasAtribuidas, btnRegressarFase);
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);

        this.setPadding(new Insets(20));
        this.setCenter(vBox);
    }

    private void registerHandlers(){
        fsm.addPropertyChangeListener(ApoioPoEContext.PROP_FASE, evt -> update());
        fsm.addPropertyChangeListener(ApoioPoEContext.PROP_PROPOSTA_ATRIBUIDA, evt -> update());

        btnRegressarFase.setOnAction(actionEvent -> fsm.regressarFase());

        btnAdicionar.setOnAction(actionEvent -> {
            Stage dialog = new Stage();
            dialog.getIcons().add(ImageManager.getImage("mini_logo_isec.png"));

            dialog.setTitle("Atribuir Proposta");

            dialog.initOwner(this.getScene().getWindow());
            dialog.initModality(Modality.APPLICATION_MODAL);

            dialog.setScene(new Scene(new AtribuirPropostaGUI(fsm)));
            dialog.setResizable(false);

            dialog.showAndWait();
        });

        btnEliminar.setOnAction(actionEvent -> {
            PropostaAtribuida propostaAtribuida = tPropostasAtribuidas.getSelectionModel().getSelectedItem();
            if(propostaAtribuida != null)
                fsm.removerDados(propostaAtribuida.getId());
        });

        tfFiltros.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER)
                btnProcurar.fire();
        });

        btnProcurar.setOnAction(actionEvent -> {

            if(tfFiltros.getText().isBlank()){
                tPropostasAtribuidas.getItems().clear();
                if(fsm.getPropostasAtribuidas() != null)
                    for (var propostaAtribuida : fsm.getPropostasAtribuidas())
                        tPropostasAtribuidas.getItems().add(propostaAtribuida);
                return;
            }

            String id = tfFiltros.getText();

            PropostaAtribuida propostaAtribuida = fsm.getPropostaAtribuida(id);
            if(propostaAtribuida != null) {
                tPropostasAtribuidas.getItems().clear();
                tPropostasAtribuidas.getItems().add(propostaAtribuida);

                tfFiltros.setText("");
            }
        });

        tPropostasAtribuidas.setRowFactory( tv -> {
            TableRow<PropostaAtribuida> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    AbreMostraDadosGUI.abreMostraDadosPropostaAtribuida(fsm, row.getItem(),
                            (Stage) this.getScene().getWindow());
                }
            });
            return row ;
        });
    }

    private void update(){
        if(fsm != null && fsm.getState() == ApoioPoEState.GESTAO_MANUAL_ATRIBUICOES) {
            if(!this.isVisible())
                resumo = AbreMostraDadosGUI.abreResumoPropostasAtribuidas(fsm, (Stage) this.getScene().getWindow());
            this.setVisible(true);
        } else {
            this.setVisible(false);
            resumo.close();
        }

        tPropostasAtribuidas.getItems().clear();
        if(fsm.getPropostasAtribuidas() != null)
            for (var propostaAtribuida : fsm.getPropostasAtribuidas())
                tPropostasAtribuidas.getItems().add(propostaAtribuida);
    }

}
