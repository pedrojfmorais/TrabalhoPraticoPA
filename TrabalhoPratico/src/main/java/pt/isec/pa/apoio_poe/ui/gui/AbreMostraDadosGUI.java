package pt.isec.pa.apoio_poe.ui.gui;

import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pt.isec.pa.apoio_poe.model.data.pessoas.alunos.Aluno;
import pt.isec.pa.apoio_poe.model.data.propostas.Proposta;
import pt.isec.pa.apoio_poe.model.data.propostas.PropostaAtribuida;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEContext;
import pt.isec.pa.apoio_poe.ui.gui.fase1.aluno.MostraDadosAlunoGUI;
import pt.isec.pa.apoio_poe.ui.gui.fase1.proposta.MostraDadosPropostaGUI;
import pt.isec.pa.apoio_poe.ui.gui.fase2.candidatura.ResumoCandidaturasGUI;
import pt.isec.pa.apoio_poe.ui.gui.fase3.propostaAtribuida.MostraDadosPropostaAtribuidaGUI;
import pt.isec.pa.apoio_poe.ui.gui.fase3.propostaAtribuida.ResumoPropostasAtribuidasGUI;
import pt.isec.pa.apoio_poe.ui.gui.fase4.docentesOrientadores.ResumoOrientadoresGUI;

public class AbreMostraDadosGUI {
    public static void abreMostraDadosAluno(ApoioPoEContext fsm, Aluno aluno, Stage owner){
        Stage dialog = new Stage();

        dialog.setTitle("Informações Aluno");

        MostraDadosAlunoGUI mAluno = new MostraDadosAlunoGUI(fsm);
        mAluno.setUserData(aluno);

        dialog.initOwner(owner);
        dialog.initModality(Modality.APPLICATION_MODAL);

        dialog.setScene(new Scene(mAluno));
        dialog.setResizable(false);

        mAluno.setData();

        dialog.showAndWait();
    }

    public static void abreMostraDadosProposta(ApoioPoEContext fsm, Proposta proposta, Stage owner){
        Stage dialog = new Stage();

        dialog.setTitle("Informações Proposta");

        MostraDadosPropostaGUI mProposta = new MostraDadosPropostaGUI(fsm);
        mProposta.setUserData(proposta);

        dialog.initOwner(owner);
        dialog.initModality(Modality.APPLICATION_MODAL);

        dialog.setScene(new Scene(mProposta));
        dialog.setResizable(false);

        mProposta.setData();

        dialog.showAndWait();
    }

    public static void abreMostraDadosPropostaAtribuida(ApoioPoEContext fsm, PropostaAtribuida item, Stage window) {
        Stage dialog = new Stage();

        dialog.setTitle("Informações Proposta Atribuída");

        MostraDadosPropostaAtribuidaGUI mProposta = new MostraDadosPropostaAtribuidaGUI(fsm);
        mProposta.setUserData(item);

        dialog.initOwner(window);
        dialog.initModality(Modality.APPLICATION_MODAL);

        dialog.setScene(new Scene(mProposta));
        dialog.setResizable(false);

        mProposta.setData();

        dialog.showAndWait();
    }

    public static void abreResumoCandidaturas(ApoioPoEContext fsm, Stage stage) {
        Stage dialog = new Stage();

        dialog.setTitle("Resumo Candidaturas");

        ResumoCandidaturasGUI resumoCandidaturasGUI = new ResumoCandidaturasGUI(fsm);

        dialog.initOwner(stage);
        dialog.setWidth(250);
        dialog.setX(stage.getX() + stage.getWidth());
        dialog.setY(stage.getY());
        dialog.initModality(Modality.NONE);

        dialog.setScene(new Scene(resumoCandidaturasGUI));
        dialog.setResizable(false);

        dialog.show();
    }

    public static void abreResumoPropostasAtribuidas(ApoioPoEContext fsm, Stage stage) {
        Stage dialog = new Stage();

        dialog.setTitle("Resumo Propostas Atribuídas");

        ResumoPropostasAtribuidasGUI resumoPropostasAtribuidasGUI = new ResumoPropostasAtribuidasGUI(fsm);

        dialog.initOwner(stage);
        dialog.setWidth(300);
        dialog.setX(stage.getX() + stage.getWidth());
        dialog.setY(stage.getY());
        dialog.initModality(Modality.NONE);

        dialog.setScene(new Scene(resumoPropostasAtribuidasGUI));
        dialog.setResizable(false);

        dialog.show();
    }

    public static void abreResumoOrientadoresPropostasAtribuidas(ApoioPoEContext fsm, Stage stage) {
        Stage dialog = new Stage();

        dialog.setTitle("Resumo Orientadores Propostas Atribuídas");

        ResumoOrientadoresGUI resumoOrientadoresGUI = new ResumoOrientadoresGUI(fsm);

        dialog.initOwner(stage);
        dialog.setWidth(350);
        dialog.setX(stage.getX() + stage.getWidth());
        dialog.setY(stage.getY());
        dialog.initModality(Modality.NONE);

        dialog.setScene(new Scene(resumoOrientadoresGUI));
        dialog.setResizable(false);

        dialog.show();
    }
}
