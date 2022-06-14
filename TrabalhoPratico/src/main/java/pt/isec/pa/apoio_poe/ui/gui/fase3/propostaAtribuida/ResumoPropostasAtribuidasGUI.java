package pt.isec.pa.apoio_poe.ui.gui.fase3.propostaAtribuida;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEContext;

public class ResumoPropostasAtribuidasGUI extends BorderPane {
    ApoioPoEContext fsm;

    Label lbAlunosComAtribuicao, lbAlunosSemAtribuicao, lbPropostasAtribuidas, lbPropostasNaoAtribuidas;
    Label lbAlunosComAtribuicaoDados, lbAlunosSemAtribuicaoDados, lbPropostasAtribuidasDados, lbPropostasNaoAtribuidasDados;

    public ResumoPropostasAtribuidasGUI(ApoioPoEContext fsm){
        this.fsm = fsm;

        createViews();
        registerHandlers();
        update();
    }

    private void createViews(){
        lbAlunosComAtribuicao = new Label("Alunos com Atribuição: ");
        lbAlunosSemAtribuicao = new Label("Alunos sem Atribuição: ");
        lbPropostasAtribuidas = new Label("Propostas Atribuídas: ");
        lbPropostasNaoAtribuidas = new Label("Propostas Não Atribuídas: ");

        lbAlunosComAtribuicaoDados = new Label();
        lbAlunosSemAtribuicaoDados = new Label();
        lbPropostasAtribuidasDados = new Label();
        lbPropostasNaoAtribuidasDados = new Label();

        VBox vBox = new VBox(
                new HBox(lbAlunosComAtribuicao, lbAlunosComAtribuicaoDados),
                new HBox(lbAlunosSemAtribuicao, lbAlunosSemAtribuicaoDados),
                new HBox(lbPropostasAtribuidas, lbPropostasAtribuidasDados),
                new HBox(lbPropostasNaoAtribuidas, lbPropostasNaoAtribuidasDados)
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
        fsm.addPropertyChangeListener(ApoioPoEContext.PROP_PROPOSTA, evt -> update());
        fsm.addPropertyChangeListener(ApoioPoEContext.PROP_PROPOSTA_ATRIBUIDA, evt -> update());
    }

    private void update(){
        lbAlunosComAtribuicaoDados.setText(String.valueOf(fsm.getPropostasAtribuidas().size()));
        lbAlunosSemAtribuicaoDados.setText(String.valueOf(fsm.getAlunos().size()-fsm.getPropostasAtribuidas().size()));
        lbPropostasAtribuidasDados.setText(String.valueOf(fsm.getPropostasAtribuidas().size()));
        lbPropostasNaoAtribuidasDados.setText(String.valueOf(fsm.getPropostas().size()-fsm.getPropostasAtribuidas().size()));
    }
}
