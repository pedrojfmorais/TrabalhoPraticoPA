package pt.isec.pa.apoio_poe.ui.gui.fase1.docente;

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
import pt.isec.pa.apoio_poe.model.data.pessoas.Docente;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEContext;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEState;
import pt.isec.pa.apoio_poe.ui.gui.resources.CSSManager;
import pt.isec.pa.apoio_poe.ui.gui.resources.ImageManager;

public class GerirDocenteGUI extends BorderPane {
    ApoioPoEContext fsm;

    Button btnAdicionar, btnEditar, btnEliminar, btnProcurar, btnRegressarFase;

    TableView<Docente> tDocente;
    TextField tfFiltros;

    public GerirDocenteGUI(ApoioPoEContext fsm){
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

        tDocente = new TableView<>();
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

        setTabelaDocente(tDocente);

        VBox vBox = new VBox(tDocente, btnRegressarFase);
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);

        this.setPadding(new Insets(20));
        this.setCenter(vBox);
    }

    private void registerHandlers(){
        fsm.addPropertyChangeListener(ApoioPoEContext.PROP_FASE, evt -> update());
        fsm.addPropertyChangeListener(ApoioPoEContext.PROP_DOCENTE, evt -> update());

        btnRegressarFase.setOnAction(actionEvent -> fsm.regressarFase());

        btnAdicionar.setOnAction(actionEvent -> {
            Stage dialog = new Stage();
            dialog.getIcons().add(ImageManager.getImage("mini_logo_isec.png"));

            dialog.setTitle("Adicionar Docente");

            dialog.initOwner(this.getScene().getWindow());
            dialog.initModality(Modality.APPLICATION_MODAL);


            dialog.setScene(new Scene(new AdicionarOuEditarDocenteGUI(fsm)));
            dialog.setResizable(false);

            dialog.showAndWait();
        });

        btnEditar.setOnAction(actionEvent -> {

            if(tDocente.getSelectionModel().getSelectedItem() == null)
                return;

            Stage dialog = new Stage();
            dialog.getIcons().add(ImageManager.getImage("mini_logo_isec.png"));

            dialog.setTitle("Editar Docente");

            AdicionarOuEditarDocenteGUI aeDocente = new AdicionarOuEditarDocenteGUI(fsm);
            aeDocente.setUserData(tDocente.getSelectionModel().getSelectedItem());

            dialog.initOwner(this.getScene().getWindow());
            dialog.initModality(Modality.APPLICATION_MODAL);

            dialog.setScene(new Scene(aeDocente));
            dialog.setResizable(false);

            aeDocente.setData();

            dialog.showAndWait();
        });

        btnEliminar.setOnAction(actionEvent -> {
            Docente docente = tDocente.getSelectionModel().getSelectedItem();
            if(docente != null)
                fsm.removerDados(docente.getEmail());
        });

        tfFiltros.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER)
                btnProcurar.fire();
        });

        btnProcurar.setOnAction(actionEvent -> procurarDocente(tfFiltros, tDocente, fsm));
    }

    private void update(){
        this.setVisible(fsm != null && fsm.getState() == ApoioPoEState.GESTAO_DOCENTES);

        tDocente.getItems().clear();
        for (var docente : fsm.getDocentes())
            tDocente.getItems().add(docente);
    }

    public static void procurarDocente(TextField tfFiltros, TableView<Docente> tDocente, ApoioPoEContext fsm) {
        String email;

        if(tfFiltros.getText().isBlank()){
            tDocente.getItems().clear();
            for (var docente : fsm.getDocentes())
                tDocente.getItems().add(docente);
            return;
        }

        email = tfFiltros.getText();

        Docente docente = fsm.getDocente(email);
        if(docente != null) {
            tDocente.getItems().clear();
            tDocente.getItems().add(docente);

            tfFiltros.setText("");
        }
    }

    public static void setTabelaDocente(TableView<Docente> tDocente) {
        TableColumn<Docente,String> tcNome = new TableColumn("Nome");
        tcNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        TableColumn<Docente,String> tcEmail = new TableColumn("Email");
        tcEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        tDocente.setPlaceholder(new Label("Ainda não foram inseridos Docentes"));
        tDocente.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tDocente.getColumns().clear();
        tDocente.getColumns().addAll(tcNome,tcEmail);
    }
}
