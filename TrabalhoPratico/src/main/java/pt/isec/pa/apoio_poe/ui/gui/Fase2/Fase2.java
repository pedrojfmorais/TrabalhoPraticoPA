package pt.isec.pa.apoio_poe.ui.gui.Fase2;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEContext;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEState;
import pt.isec.pa.apoio_poe.ui.gui.Fase1.aluno.AdicionarOuEditarAluno;

public class Fase2 extends BorderPane {
    ApoioPoEContext fsm;

    Button btnGerirCandidaturas;
    Button btnListaAlunos;
    Button btnListaPropostas;
    Button btnRegressar, btnAvancar;

    public Fase2(ApoioPoEContext fsm) {

        this.fsm = fsm;

        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        btnGerirCandidaturas = new Button("Gerir Candidaturas");
        btnListaAlunos = new Button("Lista Alunos");
        btnListaPropostas = new Button("Lista Propostas");
        btnRegressar = new Button("Regressar");
        btnAvancar = new Button("Avançar");

        btnGerirCandidaturas.setPrefSize(125, 50);
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
        vbox.getChildren().addAll(btnGerirCandidaturas, btnListaAlunos, btnListaPropostas, hBox);

        this.setCenter(vbox);
    }

    private void registerHandlers() {

        fsm.addPropertyChangeListener(ApoioPoEContext.PROP_FASE, evt -> update());
        fsm.addPropertyChangeListener(ApoioPoEContext.PROP_CANDIDATURA, evt -> update());

        btnGerirCandidaturas.setOnAction(event -> fsm.gerirDados());

        btnListaAlunos.setOnAction(event -> {
            Stage dialog = new Stage();

            dialog.setTitle("Lista Alunos");

            dialog.initOwner(this.getScene().getWindow());
            dialog.initModality(Modality.APPLICATION_MODAL);

            dialog.setWidth(450);
            dialog.setHeight(400);


            dialog.setScene(new Scene(new ListaAluno(fsm)));
            dialog.setResizable(false);

            dialog.showAndWait();
        });

        btnListaPropostas.setOnAction(event -> {
            Stage dialog = new Stage();

            dialog.setTitle("Lista Propostas");

            dialog.initOwner(this.getScene().getWindow());
            dialog.initModality(Modality.APPLICATION_MODAL);

            dialog.setWidth(450);
            dialog.setHeight(400);


            dialog.setScene(new Scene(new ListaProposta(fsm)));
            dialog.setResizable(false);

            dialog.showAndWait();
        });

        btnRegressar.setOnAction(event -> fsm.regressarFase());

        btnAvancar.setOnAction(event -> {
            Alert alert = new Alert(
                    Alert.AlertType.CONFIRMATION,
                    "",
                    ButtonType.YES, ButtonType.NO
            );
            alert.setTitle("Avançar Fase");
            alert.setHeaderText("Pretende bloquear a fase?");

            alert.showAndWait().ifPresent(response -> {
                switch (response.getButtonData()){
                    case YES -> {
                        boolean result = fsm.avancarFase(true);
                        if(!result) {
                            Alert errorMessage = new Alert(
                                    Alert.AlertType.ERROR,
                                    "",
                                    ButtonType.OK
                            );

                            errorMessage.setTitle("Erro Bloquear Fase");
                            errorMessage.setHeaderText("Neste momento não é possivel bloquear a fase!");

                            errorMessage.showAndWait();
                        }

                    }
                    case NO -> fsm.avancarFase(false);
                }
            });
        });
    }

    private void update() {
        this.setVisible(fsm != null && fsm.getState() == ApoioPoEState.FASE2);
    }
}
