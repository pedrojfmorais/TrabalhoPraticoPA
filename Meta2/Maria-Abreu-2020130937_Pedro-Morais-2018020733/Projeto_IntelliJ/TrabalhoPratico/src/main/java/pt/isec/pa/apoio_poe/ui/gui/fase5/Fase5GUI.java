package pt.isec.pa.apoio_poe.ui.gui.fase5;

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
import pt.isec.pa.apoio_poe.ui.gui.AppMenuGUI;
import pt.isec.pa.apoio_poe.ui.gui.fase4.ListaDocentesFase4GUI;
import pt.isec.pa.apoio_poe.ui.gui.resources.CSSManager;
import pt.isec.pa.apoio_poe.ui.gui.resources.ImageManager;

public class Fase5GUI extends BorderPane {
    ApoioPoEContext fsm;

    Button btnListaAlunos, btnListaPropostas, btnDadosDocentes;
    Button btnSair;

    public Fase5GUI(ApoioPoEContext fsm) {

        this.fsm = fsm;

        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {

        CSSManager.applyCSS(this,"backgroundColors.css");
        this.getStyleClass().add("fase");

        btnListaAlunos = new Button("Lista Alunos");
        btnListaPropostas = new Button("Lista Propostas");
        btnDadosDocentes = new Button("Dados Docentes");
        btnSair = new Button("Sair");

        btnListaAlunos.setPrefSize(125, 50);
        btnListaPropostas.setPrefSize(125, 50);
        btnDadosDocentes.setPrefSize(125, 50);
        btnSair.setPrefSize(75, 30);

        HBox hBox = new HBox(btnSair);
        hBox.setAlignment(Pos.CENTER_RIGHT);
        hBox.setSpacing(50);

        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10));
        vbox.getChildren().addAll(
                btnListaAlunos, btnListaPropostas, btnDadosDocentes, hBox);

        this.setCenter(vbox);
    }

    private void registerHandlers() {

        fsm.addPropertyChangeListener(ApoioPoEContext.PROP_FASE, evt -> update());
        fsm.addPropertyChangeListener(ApoioPoEContext.PROP_ALUNO, evt -> update());
        fsm.addPropertyChangeListener(ApoioPoEContext.PROP_DOCENTE, evt -> update());
        fsm.addPropertyChangeListener(ApoioPoEContext.PROP_PROPOSTA, evt -> update());
        fsm.addPropertyChangeListener(ApoioPoEContext.PROP_CANDIDATURA, evt -> update());
        fsm.addPropertyChangeListener(ApoioPoEContext.PROP_PROPOSTA_ATRIBUIDA, evt -> update());

        btnListaAlunos.setOnAction(event -> {
            Stage dialog = new Stage();
            dialog.getIcons().add(ImageManager.getImage("mini_logo_isec.png"));

            dialog.setTitle("Lista Alunos com Propostas Atribuída");

            dialog.initOwner(this.getScene().getWindow());
            dialog.initModality(Modality.APPLICATION_MODAL);

            dialog.setWidth(450);
            dialog.setHeight(400);


            dialog.setScene(new Scene(new ListaAlunoFase5GUI(fsm)));
            dialog.setResizable(false);

            dialog.showAndWait();
        });

        btnListaPropostas.setOnAction(event -> {
            Stage dialog = new Stage();
            dialog.getIcons().add(ImageManager.getImage("mini_logo_isec.png"));

            dialog.setTitle("Lista Propostas");

            dialog.initOwner(this.getScene().getWindow());
            dialog.initModality(Modality.APPLICATION_MODAL);

            dialog.setWidth(450);
            dialog.setHeight(400);


            dialog.setScene(new Scene(new ListaPropostaFase5GUI(fsm)));
            dialog.setResizable(false);

            dialog.showAndWait();
        });

        btnDadosDocentes.setOnAction(event -> {
            Stage dialog = new Stage();
            dialog.getIcons().add(ImageManager.getImage("mini_logo_isec.png"));

            dialog.setTitle("Dados Docentes");

            dialog.initOwner(this.getScene().getWindow());
            dialog.initModality(Modality.APPLICATION_MODAL);

            dialog.setWidth(450);
            dialog.setHeight(400);

            dialog.setScene(new Scene(new ListaDocentesFase4GUI(fsm)));
            dialog.setResizable(false);

            dialog.showAndWait();
        });

        btnSair.setOnAction(event -> AppMenuGUI.btnSair());
    }

    private void update() {
        this.setVisible(fsm != null && fsm.getState() == ApoioPoEState.FASE5);
    }
}
