package pt.isec.pa.apoio_poe.ui.gui.Fase2;

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
import pt.isec.pa.apoio_poe.ui.gui.Fase1.aluno.MostraDadosAluno;

public class ListaAluno extends BorderPane {

    ApoioPoEContext fsm;

    Label lbComAutoproposta, lbComCandidatura, lbSemCandidatura,lbTodos;
    RadioButton rbAutoproposta, rbComCandidatura, rbSemCandidatura, rbTodos;
    ToggleGroup tgFiltros;
    TableView<Aluno> tAlunos;

    public ListaAluno(ApoioPoEContext fsm) {
        this.fsm = fsm;

        createViews();
        registerHandlers();
        update();
    }

    private void createViews(){

        lbComAutoproposta = new Label("Alunos com Autoproposta ");
        lbComCandidatura = new Label("Alunos com Candidatura ");
        lbSemCandidatura = new Label("Alunos sem Candidatura ");
        lbTodos = new Label("Todos Alunos ");

        rbAutoproposta = new RadioButton();
        rbComCandidatura = new RadioButton();
        rbSemCandidatura = new RadioButton();
        rbTodos = new RadioButton();

        tgFiltros = new ToggleGroup();

        tAlunos = new TableView<>();

        lbTodos.setLabelFor(rbTodos);
        lbComAutoproposta.setLabelFor(rbAutoproposta);
        lbComCandidatura.setLabelFor(rbComCandidatura);
        lbSemCandidatura.setLabelFor(rbSemCandidatura);

        rbAutoproposta.setToggleGroup(tgFiltros);
        rbComCandidatura.setToggleGroup(tgFiltros);
        rbSemCandidatura.setToggleGroup(tgFiltros);
        rbTodos.setToggleGroup(tgFiltros);

        rbTodos.setSelected(true);

        TableColumn<Aluno,String> tcNAluno = new TableColumn("Número Aluno");
        tcNAluno.setCellValueFactory(new PropertyValueFactory<>("nAluno"));
        TableColumn<Aluno,String> tcNome = new TableColumn("Nome");
        tcNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        TableColumn<Aluno,String> tcEmail = new TableColumn("Email");
        tcEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        tAlunos.setPlaceholder(new Label("Ainda não foram inseridos Alunos"));
        tAlunos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tAlunos.getColumns().clear();
        tAlunos.getColumns().addAll(tcNAluno,tcNome,tcEmail);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(
                new HBox(lbTodos, rbTodos),
                new HBox(lbComAutoproposta, rbAutoproposta),
                new HBox(lbComCandidatura, rbComCandidatura),
                new HBox(lbSemCandidatura, rbSemCandidatura)
        );
        vBox.setAlignment(Pos.CENTER);

        for (int i = 0; i < vBox.getChildren().size(); i++) {
            HBox hBox = (HBox) vBox.getChildren().get(i);
            hBox.setAlignment(Pos.CENTER);
        }

        this.setPadding(new Insets(20));
        this.setTop(vBox);
        this.setCenter(tAlunos);
    }

    private void registerHandlers(){
        fsm.addPropertyChangeListener(ApoioPoEContext.PROP_FASE, evt -> update());
        fsm.addPropertyChangeListener(ApoioPoEContext.PROP_ALUNO, evt -> update());

        tgFiltros.selectedToggleProperty().addListener((observable, oldVal, newVal) -> {
            tAlunos.getItems().clear();
            for (var aluno : fsm.consultarAlunos(rbAutoproposta.isSelected(), rbComCandidatura.isSelected(), rbSemCandidatura.isSelected()))
                tAlunos.getItems().add(aluno);
        });

        tAlunos.setRowFactory( tv -> {
            TableRow<Aluno> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Stage dialog = new Stage();

                    dialog.setTitle("Informações Aluno");

                    MostraDadosAluno mAluno = new MostraDadosAluno(fsm);
                    mAluno.setUserData(row.getItem());

                    dialog.initOwner(this.getScene().getWindow());
                    dialog.initModality(Modality.APPLICATION_MODAL);

                    dialog.setScene(new Scene(mAluno));
                    dialog.setResizable(false);

                    mAluno.setData();

                    dialog.showAndWait();
                }
            });
            return row ;
        });
    }

    private void update(){
        tAlunos.getItems().clear();

        rbTodos.setSelected(true);

        for (var aluno : fsm.getAlunos())
            tAlunos.getItems().add(aluno);
    }
}
