package pt.isec.pa.apoio_poe.ui.gui.fase4.docentesOrientadores;

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

import java.util.ArrayList;
import java.util.List;

public class GestaoManualOrientacoesGUI extends BorderPane {

    ApoioPoEContext fsm;
    Stage resumo;
    Button btnAdicionar, btnEditar, btnEliminar, btnProcurar, btnRegressarFase;

    TableView<PropostaAtribuida> tPropostasAtribuidas;
    TextField tfFiltros;

    public GestaoManualOrientacoesGUI(ApoioPoEContext fsm){
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
        btnEditar = new Button("Editar");
        btnEliminar = new Button("Eliminar");
        btnProcurar = new Button(null, ImageManager.getImageView("lupa.png",20));
        btnRegressarFase = new Button("Regressar");

        btnAdicionar.getStyleClass().add("btnAdicionar");
        btnEditar.getStyleClass().add("btnEditar");
        btnEliminar.getStyleClass().add("btnEliminar");
        this.getStyleClass().add("gestaoBG");

        tPropostasAtribuidas = new TableView<>();
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

        TableColumn<PropostaAtribuida, List<String>> tcIdProposta = new TableColumn("Proposta");
        tcIdProposta.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<PropostaAtribuida,String> tcNAluno = new TableColumn("Número Aluno");
        tcNAluno.setCellValueFactory(new PropertyValueFactory<>("nAlunoAssociado"));
        TableColumn<PropostaAtribuida,String> tcEmailDocenteOrientador = new TableColumn("Docentes");
        tcEmailDocenteOrientador.setCellValueFactory(new PropertyValueFactory<>("emailDocenteOrientador"));

        tPropostasAtribuidas.setPlaceholder(new Label("Ainda não Existem Orientadores Atribuidos"));
        tPropostasAtribuidas.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tPropostasAtribuidas.getColumns().clear();
        tPropostasAtribuidas.getColumns().addAll(tcIdProposta, tcNAluno, tcEmailDocenteOrientador);

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

            dialog.setTitle("Adicionar Orientador");

            dialog.initOwner(this.getScene().getWindow());
            dialog.initModality(Modality.APPLICATION_MODAL);

            dialog.setScene(new Scene(new AdicionarOuEditarOrientadorGUI(fsm)));
            dialog.setResizable(false);

            dialog.showAndWait();
        });

        btnEditar.setOnAction(actionEvent -> {

            if(tPropostasAtribuidas.getSelectionModel().getSelectedItem() == null)
                return;

            Stage dialog = new Stage();
            dialog.getIcons().add(ImageManager.getImage("mini_logo_isec.png"));

            dialog.setTitle("Editar Orientador");

            AdicionarOuEditarOrientadorGUI aeOrientador = new AdicionarOuEditarOrientadorGUI(fsm);
            aeOrientador.setUserData(tPropostasAtribuidas.getSelectionModel().getSelectedItem());

            dialog.initOwner(this.getScene().getWindow());
            dialog.initModality(Modality.APPLICATION_MODAL);

            dialog.setScene(new Scene(aeOrientador));
            dialog.setResizable(false);

            aeOrientador.setData();

            dialog.showAndWait();
        });

        btnEliminar.setOnAction(actionEvent -> {
            PropostaAtribuida propostaAtribuida = tPropostasAtribuidas.getSelectionModel().getSelectedItem();
            if(propostaAtribuida != null)
                fsm.removerDados(String.valueOf(propostaAtribuida.getNAlunoAssociado()));
        });

        tfFiltros.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER)
                btnProcurar.fire();
        });

        btnProcurar.setOnAction(actionEvent -> {

            if(tfFiltros.getText().isBlank()){
                if(fsm.getPropostasAtribuidas() != null) {
                    tPropostasAtribuidas.getItems().clear();
                    for (var propostaAtribuida : fsm.getPropostasAtribuidas())
                        if (propostaAtribuida.getEmailDocenteOrientador() != null)
                            tPropostasAtribuidas.getItems().add(propostaAtribuida);
                }
                return;
            }

            String id = tfFiltros.getText();

            ArrayList<PropostaAtribuida> propostaAtribuida = fsm.consultarPropostasAtribuidasDocente(id);
            if(propostaAtribuida != null && !propostaAtribuida.isEmpty()) {
                tPropostasAtribuidas.getItems().clear();
                tPropostasAtribuidas.getItems().addAll(propostaAtribuida);

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
        if(fsm != null && fsm.getState() == ApoioPoEState.GESTAO_MANUAL_ORIENTADORES){
            if(!this.isVisible())
                resumo = AbreMostraDadosGUI.abreResumoOrientadoresPropostasAtribuidas(fsm, (Stage) this.getScene().getWindow());
            this.setVisible(true);
        } else {
            this.setVisible(false);
            resumo.close();
        }

        tPropostasAtribuidas.getItems().clear();
        if(fsm.getPropostasAtribuidas() != null)
            for (var propostaAtribuida : fsm.getPropostasAtribuidas())
                if(propostaAtribuida.getEmailDocenteOrientador() != null)
                    tPropostasAtribuidas.getItems().add(propostaAtribuida);
    }

}
