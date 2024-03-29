package pt.isec.pa.apoio_poe.ui.gui.fase1.aluno;

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
import pt.isec.pa.apoio_poe.model.data.pessoas.alunos.Aluno;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEContext;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEState;
import pt.isec.pa.apoio_poe.ui.gui.AbreMostraDadosGUI;
import pt.isec.pa.apoio_poe.ui.gui.resources.*;

public class GerirAlunoGUI extends BorderPane {
    ApoioPoEContext fsm;

    Button btnAdicionar, btnEditar, btnEliminar, btnProcurar, btnRegressarFase;

    TableView<Aluno> tAluno;
    TextField tfFiltros;

    public GerirAlunoGUI(ApoioPoEContext fsm){
        this.fsm = fsm;

        createViews();
        registerHandlers();
        update();
    }

    private void createViews(){

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

        tAluno = new TableView<>();
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

        setTabelaAluno(tAluno);

        VBox vBox = new VBox(tAluno, btnRegressarFase);
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);

        this.setPadding(new Insets(20));
        this.setCenter(vBox);
    }

    private void registerHandlers(){
        fsm.addPropertyChangeListener(ApoioPoEContext.PROP_FASE, evt -> update());
        fsm.addPropertyChangeListener(ApoioPoEContext.PROP_ALUNO, evt -> update());

        btnRegressarFase.setOnAction(actionEvent -> fsm.regressarFase());

        btnAdicionar.setOnAction(actionEvent -> {
            Stage dialog = new Stage();
            dialog.getIcons().add(ImageManager.getImage("mini_logo_isec.png"));

            dialog.setTitle("Adicionar Aluno");

            dialog.initOwner(this.getScene().getWindow());
            dialog.initModality(Modality.APPLICATION_MODAL);


            dialog.setScene(new Scene(new AdicionarOuEditarAlunoGUI(fsm)));
            dialog.setResizable(false);

            dialog.showAndWait();
        });

        btnEditar.setOnAction(actionEvent -> {

            if(tAluno.getSelectionModel().getSelectedItem() == null)
                return;

            Stage dialog = new Stage();
            dialog.getIcons().add(ImageManager.getImage("mini_logo_isec.png"));

            dialog.setTitle("Editar Aluno");

            AdicionarOuEditarAlunoGUI aeAluno = new AdicionarOuEditarAlunoGUI(fsm);
            aeAluno.setUserData(tAluno.getSelectionModel().getSelectedItem());

            dialog.initOwner(this.getScene().getWindow());
            dialog.initModality(Modality.APPLICATION_MODAL);

            dialog.setScene(new Scene(aeAluno));
            dialog.setResizable(false);

            aeAluno.setData();

            dialog.showAndWait();
        });

        btnEliminar.setOnAction(actionEvent -> {
            Aluno aluno = tAluno.getSelectionModel().getSelectedItem();
            if(aluno != null)
                fsm.removerDados(String.valueOf(aluno.getNAluno()));
        });

        tAluno.setRowFactory( tv -> {
            TableRow<Aluno> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) )
                    AbreMostraDadosGUI.abreMostraDadosAluno(fsm, row.getItem(), (Stage) this.getScene().getWindow());
            });
            return row ;
        });

        tfFiltros.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER)
                btnProcurar.fire();
        });

        btnProcurar.setOnAction(actionEvent -> {

            procurarAluno(tfFiltros, tAluno, fsm);
        });
    }

    private void update(){
        this.setVisible(fsm != null && fsm.getState() == ApoioPoEState.GESTAO_ALUNOS);

        tAluno.getItems().clear();
        for (var aluno : fsm.getAlunos())
            tAluno.getItems().add(aluno);
    }

    public static void procurarAluno(TextField tfFiltros, TableView<Aluno> tAluno, ApoioPoEContext fsm) {
        long nAluno;

        if(tfFiltros.getText().isBlank()){
            tAluno.getItems().clear();
            for (var aluno : fsm.getAlunos())
                tAluno.getItems().add(aluno);
            return;
        }

        try {
            nAluno = Long.parseLong(tfFiltros.getText());
        } catch (NumberFormatException e){
            return;
        }

        Aluno aluno = fsm.getAluno(nAluno);
        if(aluno != null) {
            tAluno.getItems().clear();
            tAluno.getItems().add(aluno);

            tfFiltros.setText("");
        }
    }

    public static void setTabelaAluno(TableView<Aluno> tAluno) {
        TableColumn<Aluno,String> tcNAluno = new TableColumn("Número Aluno");
        tcNAluno.setCellValueFactory(new PropertyValueFactory<>("nAluno"));
        TableColumn<Aluno,String> tcNome = new TableColumn("Nome");
        tcNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        TableColumn<Aluno,String> tcEmail = new TableColumn("Email");
        tcEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        tAluno.setPlaceholder(new Label("Ainda não foram inseridos Alunos"));
        tAluno.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tAluno.getColumns().clear();
        tAluno.getColumns().addAll(tcNAluno,tcNome,tcEmail);
    }
}
