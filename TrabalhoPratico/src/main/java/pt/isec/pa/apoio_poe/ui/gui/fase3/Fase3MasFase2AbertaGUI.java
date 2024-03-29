package pt.isec.pa.apoio_poe.ui.gui.fase3;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEContext;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEState;
import pt.isec.pa.apoio_poe.ui.gui.resources.CSSManager;

public class Fase3MasFase2AbertaGUI extends BorderPane {
    ApoioPoEContext fsm;

    Button btnAtribuicaoAutomaticaAutopropostaEPropostasDocentes, btnListaAlunos, btnListaPropostas;
    Button btnRegressar, btnAvancar;

    public Fase3MasFase2AbertaGUI(ApoioPoEContext fsm) {

        this.fsm = fsm;

        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {

        CSSManager.applyCSS(this,"backgroundColors.css");
        this.getStyleClass().add("fase");

        btnAtribuicaoAutomaticaAutopropostaEPropostasDocentes = new Button("Confirmar Auto Atribuições");
        btnListaAlunos = new Button("Lista Alunos");
        btnListaPropostas = new Button("Lista Propostas");
        btnRegressar = new Button("Regressar");
        btnAvancar = new Button("Avançar");

        btnAtribuicaoAutomaticaAutopropostaEPropostasDocentes.setPrefSize(250, 50);
        btnListaAlunos.setPrefSize(125, 50);
        btnListaPropostas.setPrefSize(125, 50);
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
                btnAtribuicaoAutomaticaAutopropostaEPropostasDocentes,
                btnListaAlunos,
                btnListaPropostas,
                hBox
        );

        this.setCenter(vbox);
    }

    private void registerHandlers() {

        fsm.addPropertyChangeListener(ApoioPoEContext.PROP_FASE, evt -> update());
        fsm.addPropertyChangeListener(ApoioPoEContext.PROP_CANDIDATURA, evt -> update());

        btnAtribuicaoAutomaticaAutopropostaEPropostasDocentes.setOnAction(
                event -> fsm.atribuicaoAutomaticaPropostasComAluno()
        );

        btnListaAlunos.setOnAction(event -> Fase3GUI.listaAlunosFase3(this.getScene().getWindow(), fsm));

        btnListaPropostas.setOnAction(event -> Fase3GUI.listaPropostasFase3(this.getScene().getWindow(), fsm));

        btnRegressar.setOnAction(event -> fsm.regressarFase());

        btnAvancar.setOnAction(event -> fsm.avancarFase(false));
    }

    private void update() {
        this.setVisible(fsm != null && fsm.getState() == ApoioPoEState.FASE3_MAS_FASE2_ABERTA);
    }
}
