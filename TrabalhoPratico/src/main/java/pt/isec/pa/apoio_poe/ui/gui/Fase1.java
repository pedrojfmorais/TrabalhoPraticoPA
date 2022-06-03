package pt.isec.pa.apoio_poe.ui.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.*;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEContext;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEState;
import pt.isec.pa.apoio_poe.utils.PAInput;


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
        btnAvancar.setPrefSize(100, 40);

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
            update();
        });
        btnGerirDocentes.setOnAction(event -> {
            fsm.gerirDocentes();
            update();
        });
        btnGerirPropostas.setOnAction(event -> {
            fsm.gerirPropostas();
            update();
        });
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
        update();
    }


    private void update() {
        this.setVisible(fsm != null && fsm.getState() == ApoioPoEState.FASE1);
    }

}
