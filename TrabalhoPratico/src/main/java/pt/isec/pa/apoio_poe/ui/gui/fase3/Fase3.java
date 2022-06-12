package pt.isec.pa.apoio_poe.ui.gui.fase3;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEContext;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEState;
import pt.isec.pa.apoio_poe.ui.gui.ComumFases;

public class Fase3 extends BorderPane {
    ApoioPoEContext fsm;

    Button btnAtribuicaoAutomaticaAutopropostaEPropostasDocentes, btnAtribuicaoAutomaticaAlunosSemAtribuicao,
            btnGestaoManualAtribuicoes, btnListaAlunos, btnListaPropostas;
    Button btnRegressar, btnAvancar;

    public Fase3(ApoioPoEContext fsm) {

        this.fsm = fsm;

        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        btnAtribuicaoAutomaticaAutopropostaEPropostasDocentes = new Button("Confirmar Auto Atribuições");
        btnAtribuicaoAutomaticaAlunosSemAtribuicao = new Button("Atribuição Automática");
        btnGestaoManualAtribuicoes = new Button("Gerir Atribuições");
        btnListaAlunos = new Button("Lista Alunos");
        btnListaPropostas = new Button("Lista Propostas");
        btnRegressar = new Button("Regressar");
        btnAvancar = new Button("Avançar");

        btnAtribuicaoAutomaticaAutopropostaEPropostasDocentes.setPrefSize(250, 50);
        btnAtribuicaoAutomaticaAlunosSemAtribuicao.setPrefSize(200, 50);
        btnGestaoManualAtribuicoes.setPrefSize(150, 50);
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
                btnAtribuicaoAutomaticaAlunosSemAtribuicao,
                btnGestaoManualAtribuicoes,
                btnListaAlunos, btnListaPropostas, hBox);

        this.setCenter(vbox);
    }

    private void registerHandlers() {

        fsm.addPropertyChangeListener(ApoioPoEContext.PROP_FASE, evt -> update());
        fsm.addPropertyChangeListener(ApoioPoEContext.PROP_CANDIDATURA, evt -> update());

        btnAtribuicaoAutomaticaAutopropostaEPropostasDocentes.setOnAction(
                event -> fsm.atribuicaoAutomaticaPropostasComAluno()
        );

        btnAtribuicaoAutomaticaAlunosSemAtribuicao.setOnAction(event -> fsm.atribuicaoAutomaticaPropostasDisponiveis());

        btnGestaoManualAtribuicoes.setOnAction(event -> fsm.gerirDados());

        btnListaAlunos.setOnAction(event -> Fase3.listaAlunosFase3(this.getScene().getWindow(), fsm));

        btnListaPropostas.setOnAction(event -> Fase3.listaPropostasFase3(this.getScene().getWindow(), fsm));

        btnRegressar.setOnAction(event -> fsm.regressarFase());

        ComumFases.alertaAvancarFase(btnAvancar, fsm);
    }

    private void update() {
        this.setVisible(fsm != null && fsm.getState() == ApoioPoEState.FASE3);
    }

    public static void listaAlunosFase3(Window window, ApoioPoEContext fsm){
        Stage dialog = new Stage();

        dialog.setTitle("Lista Alunos");

        dialog.initOwner(window);
        dialog.initModality(Modality.APPLICATION_MODAL);

        dialog.setWidth(450);
        dialog.setHeight(400);


        dialog.setScene(new Scene(new ListaAlunoFase3(fsm)));
        dialog.setResizable(false);

        dialog.showAndWait();
    }

    public static void listaPropostasFase3(Window window, ApoioPoEContext fsm){
        Stage dialog = new Stage();

        dialog.setTitle("Lista Propostas");

        dialog.initOwner(window);
        dialog.initModality(Modality.APPLICATION_MODAL);

        dialog.setWidth(450);
        dialog.setHeight(400);

        dialog.setScene(new Scene(new ListaPropostaFase3(fsm)));
        dialog.setResizable(false);

        dialog.showAndWait();
    }
}
