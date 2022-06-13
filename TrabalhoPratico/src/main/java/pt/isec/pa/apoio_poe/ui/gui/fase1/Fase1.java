package pt.isec.pa.apoio_poe.ui.gui.fase1;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEContext;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEState;
import pt.isec.pa.apoio_poe.ui.gui.AppMenu;
import pt.isec.pa.apoio_poe.ui.gui.ComumFases;

public class Fase1 extends BorderPane {

    ApoioPoEContext fsm;

    Button btnGerirAlunos;
    Button btnGerirDocentes;
    Button btnGerirPropostas;
    Button btnAvancar;

    public Fase1(ApoioPoEContext fsm) {

        this.fsm = fsm;

        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        btnGerirAlunos = new Button("Gerir Alunos");
        btnGerirDocentes = new Button("Gerir Docentes");
        btnGerirPropostas = new Button("Gerir Propostas");
        btnAvancar = new Button("Avançar");
        btnGerirAlunos.setPrefSize(125, 50);
        btnGerirDocentes.setPrefSize(125, 50);
        btnGerirPropostas.setPrefSize(125, 50);
        btnAvancar.setPrefSize(75, 30);

        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10));
        vbox.getChildren().addAll(btnGerirAlunos, btnGerirDocentes, btnGerirPropostas, btnAvancar);

        this.setCenter(vbox);
    }

    private void registerHandlers() {

        fsm.addPropertyChangeListener(ApoioPoEContext.PROP_FASE, evt -> update());

        btnGerirAlunos.setOnAction(event -> {
            fsm.gerirAlunos();
            AppMenu.mostraAlunosRamo((Stage) this.getScene().getWindow());

        });
        btnGerirDocentes.setOnAction(event -> fsm.gerirDocentes());
        btnGerirPropostas.setOnAction(event -> {
            fsm.gerirPropostas();

            AppMenu.mostraPropostasRamo((Stage) this.getScene().getWindow());
        });

        ComumFases.alertaAvancarFase(btnAvancar, fsm);
    }

    private void update() {
        this.setVisible(fsm != null && fsm.getState() == ApoioPoEState.FASE1);
    }
}
