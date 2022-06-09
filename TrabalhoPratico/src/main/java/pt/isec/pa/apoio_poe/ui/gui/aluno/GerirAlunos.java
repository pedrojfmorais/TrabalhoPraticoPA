package pt.isec.pa.apoio_poe.ui.gui.aluno;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pt.isec.pa.apoio_poe.model.data.pessoas.alunos.Aluno;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEContext;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEState;
import pt.isec.pa.apoio_poe.ui.gui.aluno.AdicionarOuEditarAluno;
import pt.isec.pa.apoio_poe.ui.gui.resources.*;

public class GerirAlunos extends BorderPane {
    ApoioPoEContext fsm;

    Button btnAdicionar;
    Button btnEditar;
    Button btnEliminar;
    Button btnProcurar;

    TableView<Aluno> tAluno;
    TextField tfFiltros;

    public GerirAlunos(ApoioPoEContext fsm){
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

        TableColumn<Aluno,String> tcNAluno = new TableColumn("Número Aluno");
        tcNAluno.setCellValueFactory(new PropertyValueFactory<>("nAluno"));
        TableColumn<Aluno,String> tcNome = new TableColumn("Nome");
        tcNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        TableColumn<Aluno,String> tcEmail = new TableColumn("Email");
        tcEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        tAluno.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tAluno.getColumns().clear();
        tAluno.getColumns().addAll(tcNAluno,tcNome,tcEmail);

        this.setCenter(tAluno);
    }

    private void registerHandlers(){
        fsm.addPropertyChangeListener(ApoioPoEContext.PROP_FASE, evt -> update());
        fsm.addPropertyChangeListener(ApoioPoEContext.PROP_ALUNO, evt -> update());

        btnAdicionar.setOnAction(actionEvent -> {
            Stage dialog = new Stage();

            dialog.setTitle("Adicionar Aluno");

            dialog.initOwner(this.getScene().getWindow());
            dialog.initModality(Modality.APPLICATION_MODAL);


            dialog.setScene(new Scene(new AdicionarOuEditarAluno(fsm)));
            dialog.setResizable(false);

            dialog.showAndWait();
        });

        btnEditar.setOnAction(actionEvent -> {
            Stage dialog = new Stage();

            dialog.setTitle("Editar Aluno");

            AdicionarOuEditarAluno aeAluno = new AdicionarOuEditarAluno(fsm);
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
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    //TODO: abre janela e mostra todas as informações
                    System.out.println(row.getItem());
                }
            });
            return row ;
        });

        //TODO: procurar, undo, redo, removerTodos, importar, exportar, regressarFase
    }

    private void update(){
        this.setVisible(fsm != null && fsm.getState() == ApoioPoEState.GESTAO_ALUNOS);

        tAluno.getItems().clear();
        for (var aluno : fsm.getAlunos())
            tAluno.getItems().add(aluno);
    }
}
