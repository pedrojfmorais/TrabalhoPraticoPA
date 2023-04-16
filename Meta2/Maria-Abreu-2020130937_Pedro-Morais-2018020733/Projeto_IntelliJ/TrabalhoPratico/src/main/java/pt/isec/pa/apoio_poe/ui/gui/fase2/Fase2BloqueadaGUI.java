package pt.isec.pa.apoio_poe.ui.gui.fase2;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pt.isec.pa.apoio_poe.model.data.Candidatura;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEContext;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEState;
import pt.isec.pa.apoio_poe.ui.gui.fase2.candidatura.GerirCandidaturaGUI;
import pt.isec.pa.apoio_poe.ui.gui.resources.CSSManager;
import pt.isec.pa.apoio_poe.ui.gui.resources.ImageManager;

public class Fase2BloqueadaGUI extends BorderPane {
    ApoioPoEContext fsm;
    Button btnListaAlunos,btnListaPropostas;
    Button btnRegressar, btnAvancar;

    TableView<Candidatura> tCandidatura;
    TextField tfFiltros;
    Button btnProcurar;

    public Fase2BloqueadaGUI(ApoioPoEContext fsm) {

        this.fsm = fsm;

        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {

        CSSManager.applyCSS(this,"backgroundColors.css");
        this.getStyleClass().add("faseBloqueada");

        btnListaAlunos = new Button("Lista Alunos");
        btnListaPropostas = new Button("Lista Propostas");
        btnRegressar = new Button("Regressar");
        btnAvancar = new Button("Avançar");

        tCandidatura = new TableView<>();
        tfFiltros = new TextField();
        btnProcurar = new Button(null, ImageManager.getImageView("lupa.png",20));

        btnListaAlunos.setPrefSize(125, 50);
        btnListaPropostas.setPrefSize(125, 50);
        tfFiltros.setPrefSize(125, 50);
        btnProcurar.setPrefSize(30, 30);
        btnRegressar.setPrefSize(75, 30);
        btnAvancar.setPrefSize(75, 30);

        GerirCandidaturaGUI.inicializarColunasCandidaturas(tCandidatura);

        HBox hBoxMudarFase = new HBox(btnRegressar, btnAvancar);
        hBoxMudarFase.setAlignment(Pos.CENTER);
        hBoxMudarFase.setSpacing(50);

        HBox hBox = new HBox(btnListaAlunos, btnListaPropostas, tfFiltros, btnProcurar);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(50);

        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10));
        vbox.getChildren().addAll(hBox, tCandidatura, hBoxMudarFase);

        this.setCenter(vbox);
    }

    private void registerHandlers() {

        fsm.addPropertyChangeListener(ApoioPoEContext.PROP_FASE, evt -> update());
        fsm.addPropertyChangeListener(ApoioPoEContext.PROP_CANDIDATURA, evt -> update());

        btnListaAlunos.setOnAction(event -> {
            Stage dialog = new Stage();
            dialog.getIcons().add(ImageManager.getImage("mini_logo_isec.png"));

            dialog.setTitle("Lista Alunos");

            dialog.initOwner(this.getScene().getWindow());
            dialog.initModality(Modality.APPLICATION_MODAL);

            dialog.setWidth(450);
            dialog.setHeight(400);


            dialog.setScene(new Scene(new ListaAlunoFase2GUI(fsm)));
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


            dialog.setScene(new Scene(new ListaPropostaFase2GUI(fsm)));
            dialog.setResizable(false);

            dialog.showAndWait();
        });

        GerirCandidaturaGUI.pesquisarCandidaturas(tfFiltros, btnProcurar, tCandidatura, fsm);

        btnRegressar.setOnAction(event -> fsm.regressarFase());
        btnAvancar.setOnAction(event -> fsm.avancarFase(false));
    }

    private void update() {
        this.setVisible(fsm != null && fsm.getState() == ApoioPoEState.FASE2_BLOQUEADA);

        tCandidatura.getItems().clear();
        for (var candidatura : fsm.getCandidaturas())
            tCandidatura.getItems().add(candidatura);
    }
}
