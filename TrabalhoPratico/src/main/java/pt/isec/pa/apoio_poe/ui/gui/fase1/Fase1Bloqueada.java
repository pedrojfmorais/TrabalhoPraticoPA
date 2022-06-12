package pt.isec.pa.apoio_poe.ui.gui.fase1;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pt.isec.pa.apoio_poe.model.data.pessoas.alunos.Aluno;
import pt.isec.pa.apoio_poe.model.data.propostas.Proposta;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEContext;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEState;
import pt.isec.pa.apoio_poe.ui.gui.AppMenu;
import pt.isec.pa.apoio_poe.ui.gui.fase1.aluno.GerirAluno;
import pt.isec.pa.apoio_poe.ui.gui.fase1.docente.GerirDocente;
import pt.isec.pa.apoio_poe.ui.gui.fase1.proposta.GerirProposta;
import pt.isec.pa.apoio_poe.ui.gui.mostraDados.AbreMostraDados;
import pt.isec.pa.apoio_poe.ui.gui.resources.ImageManager;

public class Fase1Bloqueada extends BorderPane {

    ApoioPoEContext fsm;

    ToggleButton tbConsultarAlunos, tbConsultarDocentes, tbConsultarPropostas;

    ToggleGroup tgConsultar;

    TableView tableView;

    TextField tfFiltros;
    Button btnAvancar, btnProcurar;

    public Fase1Bloqueada(ApoioPoEContext fsm) {

        this.fsm = fsm;

        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        tbConsultarAlunos = new ToggleButton("Alunos");
        tbConsultarDocentes = new ToggleButton("Docentes");
        tbConsultarPropostas = new ToggleButton("Propostas");
        tfFiltros = new TextField();
        btnProcurar = new Button(null, ImageManager.getImageView("lupa.png",20));
        btnAvancar = new Button("Avançar");

        tbConsultarAlunos.setPrefSize(125, 50);
        tbConsultarDocentes.setPrefSize(125, 50);
        tbConsultarPropostas.setPrefSize(125, 50);
        btnAvancar.setPrefSize(75, 30);
        btnProcurar.setPrefSize(30,30);
        btnProcurar.setMinWidth(30);

        tgConsultar = new ToggleGroup();

        tableView = new TableView();

        tbConsultarAlunos.setToggleGroup(tgConsultar);
        tbConsultarDocentes.setToggleGroup(tgConsultar);
        tbConsultarPropostas.setToggleGroup(tgConsultar);

        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(10);
        hbox.setPadding(new Insets(10));
        hbox.getChildren().addAll(tbConsultarAlunos, tbConsultarDocentes, tbConsultarPropostas, tfFiltros, btnProcurar);

        VBox vBox = new VBox(hbox, tableView, btnAvancar);
        vBox.setAlignment(Pos.CENTER_RIGHT);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10));
        this.setCenter(vBox);
    }

    private void registerHandlers() {

        fsm.addPropertyChangeListener(ApoioPoEContext.PROP_FASE, evt -> update());
        fsm.addPropertyChangeListener(ApoioPoEContext.PROP_ALUNO, evt -> {
            if(tbConsultarAlunos.isSelected())
                GerirAluno.procurarAluno(tfFiltros, tableView, fsm);
        });
        fsm.addPropertyChangeListener(ApoioPoEContext.PROP_DOCENTE, evt -> {
            if(tbConsultarDocentes.isSelected())
                GerirDocente.procurarDocente(tfFiltros, tableView, fsm);
        });
        fsm.addPropertyChangeListener(ApoioPoEContext.PROP_PROPOSTA, evt -> {
            if(tbConsultarPropostas.isSelected())
                GerirProposta.procurarProposta(tfFiltros, tableView, fsm);
        });

        tbConsultarAlunos.setOnAction(event -> {

            if (!tbConsultarAlunos.isSelected()) {
                tbConsultarAlunos.setSelected(true);
                return;
            }

            tableView.getColumns().clear();

            GerirAluno.setTabelaAluno(tableView);


            tableView.getItems().clear();
            for (var aluno : fsm.getAlunos())
                tableView.getItems().add(aluno);

        });

        tbConsultarDocentes.setOnAction(event -> {

            if (!tbConsultarDocentes.isSelected()) {
                tbConsultarDocentes.setSelected(true);
                return;
            }

            tableView.getColumns().clear();

            GerirDocente.setTabelaDocente(tableView);

            tableView.getItems().clear();
            for (var docente : fsm.getDocentes())
                tableView.getItems().add(docente);
        });

        tbConsultarPropostas.setOnAction(event -> {

            if (!tbConsultarPropostas.isSelected()) {
                tbConsultarPropostas.setSelected(true);
                return;
            }

            tableView.getColumns().clear();
            GerirProposta.setTabelaProposta(tableView);

            tableView.getItems().clear();
            for (var proposta : fsm.getPropostas())
                tableView.getItems().add(proposta);
        });

        tableView.setRowFactory(tv -> {
            TableRow row = new TableRow();
                row.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 2 && (! row.isEmpty()) )

                        if(row.getItem() instanceof Aluno)
                            AbreMostraDados.abreMostraDadosAluno(fsm, (Aluno) row.getItem(),
                                    (Stage) this.getScene().getWindow());

                        else if(row.getItem() instanceof Proposta)
                            AbreMostraDados.abreMostraDadosProposta(fsm, (Proposta) row.getItem(),
                                    (Stage) this.getScene().getWindow());
                });
                return row ;
        });

        tfFiltros.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER)
                btnProcurar.fire();
        });

        btnProcurar.setOnAction(actionEvent -> {
            System.out.println(tbConsultarAlunos.isSelected() + " " + tbConsultarDocentes.isSelected() + " " + tbConsultarPropostas.isSelected());

            tableView.getItems().clear();

            if(tbConsultarAlunos.isSelected()){
                GerirAluno.procurarAluno(tfFiltros, tableView, fsm);
            } else if(tbConsultarDocentes.isSelected()){
                GerirDocente.procurarDocente(tfFiltros, tableView, fsm);
            } else if(tbConsultarPropostas.isSelected()){
                GerirProposta.procurarProposta(tfFiltros, tableView, fsm);
            }
        });

        btnAvancar.setOnAction(event -> fsm.avancarFase(false));
    }

    private void update() {
        tbConsultarAlunos.fire();
        tbConsultarAlunos.setSelected(true);
        this.setVisible(fsm != null && fsm.getState() == ApoioPoEState.FASE1_BLOQUEADA);

        AppMenu.closeDialog();
    }
}
