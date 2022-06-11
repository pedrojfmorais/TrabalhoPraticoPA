package pt.isec.pa.apoio_poe.ui.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEContext;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEState;
import pt.isec.pa.apoio_poe.ui.gui.aluno.PiechartAlunosRamos;
import pt.isec.pa.apoio_poe.ui.gui.proposta.PiechartPropostasRamos;

public class Fase1 extends BorderPane {

    ApoioPoEContext fsm;

    Button btnGerirAlunos;
    Button btnGerirDocentes;
    Button btnGerirPropostas;
    Button btnAvancar;

    Stage dialog;

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
        dialog = new Stage();

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

            dialog = new Stage();

            dialog.initOwner(this.getScene().getWindow());
            dialog.setHeight(this.getScene().getWindow().getHeight());
            dialog.setX(this.getScene().getWindow().getX() + this.getScene().getWindow().getWidth());
            dialog.setY(this.getScene().getWindow().getY());

            dialog.setTitle("Alunos por Ramo");

            dialog.initModality(Modality.NONE);
            dialog.setResizable(false);
            dialog.setWidth(300);

            dialog.setScene(new Scene(new PiechartAlunosRamos(fsm)));
            dialog.show();

        });
        btnGerirDocentes.setOnAction(event -> fsm.gerirDocentes());
        btnGerirPropostas.setOnAction(event -> {
            fsm.gerirPropostas();

            dialog = new Stage();

            dialog.initOwner(this.getScene().getWindow());
            dialog.setHeight(this.getScene().getWindow().getHeight());
            dialog.setX(this.getScene().getWindow().getX() + this.getScene().getWindow().getWidth());
            dialog.setY(this.getScene().getWindow().getY());

            dialog.setTitle("Propostas por Ramo");

            dialog.initModality(Modality.NONE);
            dialog.setResizable(false);
            dialog.setWidth(300);

            dialog.setScene(new Scene(new PiechartPropostasRamos(fsm)));
            dialog.setResizable(false);

            dialog.show();
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
    }

    private void update() {
        this.setVisible(fsm != null && fsm.getState() == ApoioPoEState.FASE1);
        dialog.close();
    }

}
