package pt.isec.pa.apoio_poe.ui.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pt.isec.pa.apoio_poe.model.data.pessoas.alunos.Aluno;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEContext;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEState;
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
        btnProcurar = new Button(null, ImageManager.getImageView("lupa.png",40));

        tAluno = new TableView<>();
        tfFiltros = new TextField();

        btnAdicionar.setPrefSize(125, 50);
        btnEditar.setPrefSize(125, 50);
        btnEliminar.setPrefSize(125, 50);
        btnProcurar.setPrefSize(50,50);
        btnProcurar.setMinWidth(50);

        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(10);
        hbox.setPadding(new Insets(10));
        hbox.getChildren().addAll(btnAdicionar, btnEditar, btnEliminar, tfFiltros, btnProcurar);
        this.setTop(hbox);

        this.setCenter(tAluno);

        TableColumn<Aluno,String> tcNAluno = new TableColumn("Número Aluno");
        tcNAluno.setCellValueFactory(new PropertyValueFactory<>("nAluno"));
        TableColumn<Aluno,String> tcNome = new TableColumn("Nome");
        tcNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        TableColumn<Aluno,String> tcEmail = new TableColumn("Email");
        tcEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        tAluno.getColumns().clear();
        tAluno.getColumns().addAll(tcNAluno,tcNome,tcEmail);

    }

    private void registerHandlers(){
        fsm.addPropertyChangeListener(ApoioPoEContext.PROP_FASE, evt -> update());

        btnEliminar.setOnAction(actionEvent -> {
            Aluno aluno = tAluno.getSelectionModel().getSelectedItem();
            if(aluno != null)
                fsm.removerDados(String.valueOf(aluno.getNAluno()));
            update();
        });

        btnAdicionar.setOnAction(actionEvent -> {
            Stage dialog = new Stage();
            dialog.initOwner(this.getScene().getWindow());
            dialog.initModality(Modality.APPLICATION_MODAL);

            dialog.setScene(new Scene(new AdicionarAluno(fsm)));
            dialog.setResizable(false);

            dialog.showAndWait();
        });

    }

    private void update(){
        this.setVisible(fsm != null && fsm.getState() == ApoioPoEState.GESTAO_ALUNOS);

        tAluno.getItems().clear();
        for (var aluno : fsm.getAlunos() )
            tAluno.getItems().add(aluno);
    }
}
