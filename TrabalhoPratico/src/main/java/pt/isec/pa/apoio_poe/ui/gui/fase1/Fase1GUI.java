package pt.isec.pa.apoio_poe.ui.gui.fase1;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEContext;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEState;
import pt.isec.pa.apoio_poe.ui.gui.AppMenuGUI;
import pt.isec.pa.apoio_poe.ui.gui.ComumFasesGUI;
import pt.isec.pa.apoio_poe.ui.gui.resources.CSSManager;

public class Fase1GUI extends BorderPane {

    ApoioPoEContext fsm;

    Button btnGerirAlunos;
    Button btnGerirDocentes;
    Button btnGerirPropostas;
    Button btnAvancar;

    public Fase1GUI(ApoioPoEContext fsm) {

        this.fsm = fsm;

        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {

        CSSManager.applyCSS(this,"backgroundColors.css");
        this.getStyleClass().add("fase1");

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
            AppMenuGUI.mostraAlunosRamo((Stage) this.getScene().getWindow());
        });
        btnGerirDocentes.setOnAction(event -> fsm.gerirDocentes());
        btnGerirPropostas.setOnAction(event -> {
            fsm.gerirPropostas();
            AppMenuGUI.mostraPropostasRamo((Stage) this.getScene().getWindow());
        });

        ComumFasesGUI.alertaAvancarFase(btnAvancar, fsm);
    }

    private void update() {
        this.setVisible(fsm != null && fsm.getState() == ApoioPoEState.FASE1);
    }
}
