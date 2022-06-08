package pt.isec.pa.apoio_poe.ui.gui;

import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
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

    public AdicionarAluno(ApoioPoEContext fsm){
        this.fsm = fsm;

        createViews();
        registerHandlers();
        update();
    }

    private void createViews(){
        lbNAluno = new Label("Numero Aluno");
        lbNome = new Label("Nome");
        lbEmail = new Label("Email");
        lbSiglaCurso = new Label("Curso");
        lbSiglaRamos = new Label("Ramo");
        lbClassif = new Label("Classificação");
        lbAcessoEstagio = new Label("Acesso a Estágio");

        tfNAluno = new TextField();
        tfNome = new TextField();
        tfEmail = new TextField();
        cbCurso = new ComboBox<>();
        cbRamo = new ComboBox<>();
        tfClassif = new TextField();
        ckAcessoEstagio = new CheckBox();

        tfNAluno.setPrefSize(125, 50);
        tfNome.setPrefSize(125, 50);
        tfEmail.setPrefSize(250, 50);
        cbCurso.setPrefSize(125,50);
        cbRamo.setPrefSize(125,50);
        tfClassif.setPrefSize(125,50);
        ckAcessoEstagio.setPrefSize(50,50);

        GridPane gp = new GridPane();
        gp.setVgap(4);
        gp.setHgap(10);

    }
}
