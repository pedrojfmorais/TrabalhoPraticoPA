package pt.isec.pa.apoio_poe.ui.gui.fase1.aluno;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import pt.isec.pa.apoio_poe.model.data.pessoas.alunos.Aluno;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEContext;

public class MostraDadosAlunoGUI extends BorderPane {

    ApoioPoEContext fsm;

    Label lbInfo, lbNome, lbEmail, lbNAluno, lbCurso, lbRamo, lbClassif, lbAcessoEstagio;
    Label lbNomeDados, lbEmailDados, lbNAlunoDados, lbCursoDados, lbRamoDados, lbClassifDados, lbAcessoEstagioDados;

    public MostraDadosAlunoGUI(ApoioPoEContext fsm){
        this.fsm = fsm;

        createViews();
        registerHandlers();
        update();
    }

    private void createViews(){

        lbInfo = new Label("Informações do Aluno");
        lbNAluno = new Label("Número de Aluno: ");
        lbNome = new Label("Nome Aluno: ");
        lbEmail = new Label("Email Aluno: ");
        lbCurso = new Label("Curso Aluno: ");
        lbRamo = new Label("Ramo Aluno: ");
        lbClassif = new Label("Classificação Aluno: ");
        lbAcessoEstagio = new Label("Aluno com Acesso a Estágio: ");

        lbNAlunoDados = new Label();
        lbNomeDados = new Label();
        lbEmailDados = new Label();
        lbCursoDados = new Label();
        lbRamoDados = new Label();
        lbClassifDados = new Label();
        lbAcessoEstagioDados = new Label();

        lbInfo.setFont(new Font("Arial", 30));
        lbNAluno.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        lbNome.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        lbEmail.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        lbCurso.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        lbRamo.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        lbClassif.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        lbAcessoEstagio.setFont(Font.font("Arial", FontWeight.BOLD, 13));

        VBox vBox = new VBox();
        vBox.getChildren().addAll(
                lbInfo,
                new HBox(lbNAluno, lbNAlunoDados),
                new HBox(lbNome,lbNomeDados),
                new HBox(lbEmail, lbEmailDados),
                new HBox(lbCurso, lbCursoDados),
                new HBox(lbRamo, lbRamoDados),
                new HBox(lbClassif, lbClassifDados),
                new HBox(lbAcessoEstagio, lbAcessoEstagioDados)
        );

        for (int i = 1; i < vBox.getChildren().size(); i++) {
            HBox hBox = (HBox) vBox.getChildren().get(i);
            hBox.setAlignment(Pos.CENTER);
        }

        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);

        this.setPadding(new Insets(20));

        this.setCenter(vBox);

    }

    private void registerHandlers(){}

    private void update(){}

    public void setData(){
        Aluno mostraAluno = (Aluno) this.getUserData();
        if(mostraAluno != null){
            lbNAlunoDados.setText(String.valueOf(mostraAluno.getNAluno()));
            lbNomeDados.setText(mostraAluno.getNome());
            lbEmailDados.setText(mostraAluno.getEmail());
            lbCursoDados.setText(mostraAluno.getSiglaCurso());
            lbRamoDados.setText(mostraAluno.getSiglaRamo());
            lbClassifDados.setText(String.valueOf(mostraAluno.getClassificacao()));
            lbAcessoEstagioDados.setText(mostraAluno.isAcessoEstagio() ? "Sim" : "Não");
        }
    }
}
