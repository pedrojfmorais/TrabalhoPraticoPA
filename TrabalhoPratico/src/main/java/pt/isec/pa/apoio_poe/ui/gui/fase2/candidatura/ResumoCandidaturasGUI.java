package pt.isec.pa.apoio_poe.ui.gui.fase2.candidatura;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEContext;

public class ResumoCandidaturasGUI extends BorderPane {
    ApoioPoEContext fsm;

    Label lbTotalAlunos, lbAlunosComCandidatura, lbAlunosSemCandidatura;
    Label lbTotalAlunosDados, lbAlunosComCandidaturaDados, lbAlunosSemCandidaturaDados;

    public ResumoCandidaturasGUI(ApoioPoEContext fsm){
        this.fsm = fsm;

        createViews();
        registerHandlers();
        update();
    }

    private void createViews(){
        lbTotalAlunos = new Label("Total Alunos: ");
        lbAlunosComCandidatura = new Label("Alunos com Candidatura: ");
        lbAlunosSemCandidatura = new Label("Alunos sem Candidatura: ");

        lbTotalAlunosDados = new Label();
        lbAlunosComCandidaturaDados = new Label();
        lbAlunosSemCandidaturaDados = new Label();

        VBox vBox = new VBox(
                new HBox(lbTotalAlunos, lbTotalAlunosDados),
                new HBox(lbAlunosComCandidatura, lbAlunosComCandidaturaDados),
                new HBox(lbAlunosSemCandidatura, lbAlunosSemCandidaturaDados)
        );
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);

        for (int i = 0; i < vBox.getChildren().size(); i++) {
            HBox hBox = (HBox)  vBox.getChildren().get(i);
            hBox.setAlignment(Pos.CENTER);
        }

        this.setPadding(new Insets(20));
        this.setCenter(vBox);
    }

    private void registerHandlers(){
        fsm.addPropertyChangeListener(ApoioPoEContext.PROP_ALUNO, evt -> update());
        fsm.addPropertyChangeListener(ApoioPoEContext.PROP_CANDIDATURA, evt -> update());
    }

    private void update(){
        lbTotalAlunosDados.setText(String.valueOf(fsm.getAlunos().size()));
        lbAlunosComCandidaturaDados.setText(String.valueOf(fsm.getCandidaturas().size()));
        lbAlunosSemCandidaturaDados.setText(String.valueOf(fsm.getAlunos().size()-fsm.getCandidaturas().size()));
    }
}
