package pt.isec.pa.apoio_poe.ui.gui.fase5;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pt.isec.pa.apoio_poe.model.data.pessoas.alunos.Aluno;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEContext;
import pt.isec.pa.apoio_poe.ui.gui.fase1.aluno.GerirAlunoGUI;
import pt.isec.pa.apoio_poe.ui.gui.fase1.aluno.MostraDadosAlunoGUI;
import pt.isec.pa.apoio_poe.ui.gui.resources.ImageManager;

import java.util.ArrayList;

public class ListaAlunoFase5GUI extends BorderPane {
    ApoioPoEContext fsm;

    Label lbComOrientador;
    CheckBox ckComOrientador;
    TableView<Aluno> tAlunos;

    public ListaAlunoFase5GUI(ApoioPoEContext fsm) {
        this.fsm = fsm;

        createViews();
        registerHandlers();
        update();
    }

    private void createViews(){

        lbComOrientador = new Label("Alunos com Orientador Associado ");

        ckComOrientador = new CheckBox();

        tAlunos = new TableView<>();

        lbComOrientador.setLabelFor(ckComOrientador);

        GerirAlunoGUI.setTabelaAluno(tAlunos);

        VBox vBox = new VBox();
        vBox.getChildren().add(
                new HBox(lbComOrientador, ckComOrientador)
        );
        vBox.setAlignment(Pos.CENTER);

        HBox hBox = (HBox) vBox.getChildren().get(0);
        hBox.setAlignment(Pos.CENTER);

        ckComOrientador.setSelected(true);

        this.setPadding(new Insets(20));
        this.setTop(vBox);
        this.setCenter(tAlunos);
    }

    private void registerHandlers(){
        fsm.addPropertyChangeListener(ApoioPoEContext.PROP_FASE, evt -> update());
        fsm.addPropertyChangeListener(ApoioPoEContext.PROP_PROPOSTA_ATRIBUIDA, evt -> update());

        ckComOrientador.setOnAction(event -> update());

        tAlunos.setRowFactory( tv -> {
            TableRow<Aluno> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Stage dialog = new Stage();
                    dialog.getIcons().add(ImageManager.getImage("mini_logo_isec.png"));

                    dialog.setTitle("Informações Aluno");

                    MostraDadosAlunoGUI mAluno = new MostraDadosAlunoGUI(fsm);
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

        ArrayList<Aluno> alunos = fsm.consultarAlunosFase5(ckComOrientador.isSelected());
        if(alunos != null)
            for (var aluno : alunos)
                tAlunos.getItems().add(aluno);
    }
}
