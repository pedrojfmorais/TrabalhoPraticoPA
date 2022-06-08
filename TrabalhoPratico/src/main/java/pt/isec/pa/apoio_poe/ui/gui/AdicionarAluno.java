package pt.isec.pa.apoio_poe.ui.gui;

import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import pt.isec.pa.apoio_poe.model.data.pessoas.alunos.Aluno;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEContext;

public class AdicionarAluno extends BorderPane {
    ApoioPoEContext fsm;

    Label lbNAluno;
    Label lbNome;
    Label lbEmail;
    Label lbSiglaCurso;
    Label lbSiglaRamos;
    Label lbClassif;
    Label lbAcessoEstagio;

    TextField tfNAluno;
    TextField tfNome;
    TextField tfEmail;
    ComboBox<String> cbCurso;
    ComboBox<String> cbRamo;
    TextField tfClassif;
    CheckBox ckAcessoEstagio;

    Button btnEnviar;

    public AdicionarAluno(ApoioPoEContext fsm){
        this.fsm = fsm;

        createViews();
//        registerHandlers();
//        update();
    }

    private void createViews(){
        lbNAluno = new Label("Número Aluno:");
        lbNome = new Label("Nome:");
        lbEmail = new Label("Email:");
        lbSiglaCurso = new Label("Curso:");
        lbSiglaRamos = new Label("Ramo:");
        lbClassif = new Label("Classificação:");
        lbAcessoEstagio = new Label("Acesso a Estágio:");

        tfNAluno = new TextField();
        tfNome = new TextField();
        tfEmail = new TextField();
        cbCurso = new ComboBox<>();
        cbRamo = new ComboBox<>();
        tfClassif = new TextField();
        ckAcessoEstagio = new CheckBox();

        btnEnviar = new Button("Adicionar");

        tfNAluno.setPrefSize(125, 25);
        tfNome.setPrefSize(125, 25);
        tfEmail.setPrefSize(250, 25);
        cbCurso.setPrefSize(125,25);
        cbRamo.setPrefSize(125,25);
        tfClassif.setPrefSize(125,25);
        ckAcessoEstagio.setPrefSize(25,25);
        btnEnviar.setPrefSize(100,25);

        cbCurso.setItems(FXCollections.observableArrayList(Aluno.cursos));
        cbRamo.setItems(FXCollections.observableArrayList(Aluno.ramos));

        GridPane gp = new GridPane();
        gp.setVgap(4);
        gp.setHgap(10);
        gp.addRow(0, lbNAluno, lbNome);
        gp.addRow(1, tfNAluno, tfNome);
        gp.addRow(2, lbEmail);
        gp.addRow(3, tfEmail);
        gp.addRow(4, lbSiglaCurso, lbSiglaRamos);
        gp.addRow(5, cbCurso, cbRamo);
        gp.addRow(6, lbClassif, lbAcessoEstagio);
        gp.addRow(7, tfClassif, ckAcessoEstagio);
        gp.addRow(8, btnEnviar);

        GridPane.setColumnSpan(tfEmail, 2);
        GridPane.setColumnSpan(btnEnviar, 2);
        GridPane.setHalignment(btnEnviar, HPos.RIGHT);
        this.setPadding(new Insets(20));

        this.setCenter(gp);
    }
}
