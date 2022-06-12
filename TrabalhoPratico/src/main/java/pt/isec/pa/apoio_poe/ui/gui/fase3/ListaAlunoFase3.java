package pt.isec.pa.apoio_poe.ui.gui.fase3;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pt.isec.pa.apoio_poe.model.data.pessoas.alunos.Aluno;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEContext;
import pt.isec.pa.apoio_poe.ui.gui.fase1.aluno.GerirAluno;
import pt.isec.pa.apoio_poe.ui.gui.fase1.aluno.MostraDadosAluno;

import java.util.ArrayList;

public class ListaAlunoFase3 extends BorderPane {
    ApoioPoEContext fsm;

    Label lbComAutoproposta, lbComCandidatura, lbComPropostaAtribuida, lbSemPropostaAtribuida, lbTodos;
    RadioButton rbAutoproposta, rbComCandidatura, rbComPropostaAtribuida, rbSemPropostaAtribuida, rbTodos;
    ToggleGroup tgFiltros;
    TableView<Aluno> tAlunos;

    public ListaAlunoFase3(ApoioPoEContext fsm) {
        this.fsm = fsm;

        createViews();
        registerHandlers();
        update();
    }

    private void createViews(){

        lbComAutoproposta = new Label("Alunos com Autoproposta ");
        lbComCandidatura = new Label("Alunos com Candidatura ");
        lbComPropostaAtribuida = new Label("Alunos Com Proposta Atribuida ");
        lbSemPropostaAtribuida = new Label("Alunos Sem Proposta Atribuida ");
        lbTodos = new Label("Todos Alunos ");

        rbAutoproposta = new RadioButton();
        rbComCandidatura = new RadioButton();
        rbComPropostaAtribuida = new RadioButton();
        rbSemPropostaAtribuida = new RadioButton();
        rbTodos = new RadioButton();

        tgFiltros = new ToggleGroup();

        tAlunos = new TableView<>();

        lbTodos.setLabelFor(rbTodos);
        lbComAutoproposta.setLabelFor(rbAutoproposta);
        lbComCandidatura.setLabelFor(rbComCandidatura);
        lbComPropostaAtribuida.setLabelFor(rbComPropostaAtribuida);
        lbSemPropostaAtribuida.setLabelFor(rbSemPropostaAtribuida);

        rbAutoproposta.setToggleGroup(tgFiltros);
        rbComCandidatura.setToggleGroup(tgFiltros);
        rbComPropostaAtribuida.setToggleGroup(tgFiltros);
        rbSemPropostaAtribuida.setToggleGroup(tgFiltros);
        rbTodos.setToggleGroup(tgFiltros);

        rbTodos.setSelected(true);

        GerirAluno.setTabelaAluno(tAlunos);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(
                new HBox(lbTodos, rbTodos),
                new HBox(lbComAutoproposta, rbAutoproposta),
                new HBox(lbComCandidatura, rbComCandidatura),
                new HBox(lbComPropostaAtribuida, rbComPropostaAtribuida),
                new HBox(lbSemPropostaAtribuida, rbSemPropostaAtribuida)
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
            ArrayList<Aluno> alunos = fsm.consultarAlunos(rbAutoproposta.isSelected(), rbComCandidatura.isSelected(),
                    rbComPropostaAtribuida.isSelected(), rbSemPropostaAtribuida.isSelected());
            if(alunos != null)
                for (var aluno : alunos)
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
