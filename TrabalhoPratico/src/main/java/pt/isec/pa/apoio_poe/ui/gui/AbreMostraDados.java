package pt.isec.pa.apoio_poe.ui.gui;

import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pt.isec.pa.apoio_poe.model.data.pessoas.alunos.Aluno;
import pt.isec.pa.apoio_poe.model.data.propostas.Proposta;
import pt.isec.pa.apoio_poe.model.data.propostas.PropostaAtribuida;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEContext;
import pt.isec.pa.apoio_poe.ui.gui.fase1.aluno.MostraDadosAluno;
import pt.isec.pa.apoio_poe.ui.gui.fase1.proposta.MostraDadosProposta;
import pt.isec.pa.apoio_poe.ui.gui.fase3.propostaAtribuida.MostraDadosPropostaAtribuida;

public class AbreMostraDados {
    public static void abreMostraDadosAluno(ApoioPoEContext fsm, Aluno aluno, Stage owner){
        Stage dialog = new Stage();

        dialog.setTitle("Informações Aluno");

        MostraDadosAluno mAluno = new MostraDadosAluno(fsm);
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

        MostraDadosProposta mProposta = new MostraDadosProposta(fsm);
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

        MostraDadosPropostaAtribuida mProposta = new MostraDadosPropostaAtribuida(fsm);
        mProposta.setUserData(item);

        dialog.initOwner(window);
        dialog.initModality(Modality.APPLICATION_MODAL);

        dialog.setScene(new Scene(mProposta));
        dialog.setResizable(false);

        mProposta.setData();

        dialog.showAndWait();
    }
}
