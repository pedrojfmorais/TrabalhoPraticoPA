package pt.isec.pa.apoio_poe.ui.gui.fase4;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEContext;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEState;
import pt.isec.pa.apoio_poe.ui.gui.ComumFases;

public class Fase4 extends BorderPane {
    ApoioPoEContext fsm;

    Button btnAssociacaoAutomaticaDocentesProponentes, btnGestaoManualOrientacoes, btnListaAlunos, btnDadosDocentes;
    Button btnRegressar, btnAvancar;

    public Fase4(ApoioPoEContext fsm) {

        this.fsm = fsm;

        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        btnAssociacaoAutomaticaDocentesProponentes = new Button("Confirmar auto Orientações");
        btnGestaoManualOrientacoes = new Button("Gerir Orientadores");
        btnListaAlunos = new Button("Lista Alunos");
        btnDadosDocentes = new Button("Dados Docentes");
        btnRegressar = new Button("Regressar");
        btnAvancar = new Button("Avançar");

        btnAssociacaoAutomaticaDocentesProponentes.setPrefSize(250, 50);
        btnGestaoManualOrientacoes.setPrefSize(150, 50);
        btnListaAlunos.setPrefSize(125, 50);
        btnDadosDocentes.setPrefSize(125, 50);
        btnRegressar.setPrefSize(75, 30);
        btnAvancar.setPrefSize(75, 30);

        HBox hBox = new HBox(btnRegressar, btnAvancar);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(50);

        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10));
        vbox.getChildren().addAll(
                btnAssociacaoAutomaticaDocentesProponentes,
                btnGestaoManualOrientacoes,
                btnListaAlunos, btnDadosDocentes, hBox);

        this.setCenter(vbox);
    }

    private void registerHandlers() {

        fsm.addPropertyChangeListener(ApoioPoEContext.PROP_FASE, evt -> update());
        fsm.addPropertyChangeListener(ApoioPoEContext.PROP_CANDIDATURA, evt -> update());

        btnAssociacaoAutomaticaDocentesProponentes.setOnAction(
                event -> fsm.associacaoAutomaticaDocentesProponentes()
        );

        btnGestaoManualOrientacoes.setOnAction(event -> fsm.gerirDados());

        btnListaAlunos.setOnAction(event -> {
            Stage dialog = new Stage();

            dialog.setTitle("Lista Alunos com Propostas Atribuída");

            dialog.initOwner(this.getScene().getWindow());
            dialog.initModality(Modality.APPLICATION_MODAL);

            dialog.setWidth(450);
            dialog.setHeight(400);


            dialog.setScene(new Scene(new ListaAlunoFase4(fsm)));
            dialog.setResizable(false);

            dialog.showAndWait();
        });

        btnDadosDocentes.setOnAction(event -> {
            Stage dialog = new Stage();

            dialog.setTitle("Dados Docentes");

            dialog.initOwner(this.getScene().getWindow());
            dialog.initModality(Modality.APPLICATION_MODAL);

            dialog.setWidth(450);
            dialog.setHeight(400);

            dialog.setScene(new Scene(new ListaDocentesFase4(fsm)));
            dialog.setResizable(false);

            dialog.showAndWait();
        });

        btnRegressar.setOnAction(event -> fsm.regressarFase());

        ComumFases.alertaAvancarFase(btnAvancar, fsm);
    }

    private void update() {
        this.setVisible(fsm != null && fsm.getState() == ApoioPoEState.FASE4);
    }
}
