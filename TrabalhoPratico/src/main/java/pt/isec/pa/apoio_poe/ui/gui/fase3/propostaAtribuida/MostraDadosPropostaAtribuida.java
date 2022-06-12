package pt.isec.pa.apoio_poe.ui.gui.fase3.propostaAtribuida;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import pt.isec.pa.apoio_poe.model.data.propostas.PropostaAtribuida;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEContext;

public class MostraDadosPropostaAtribuida extends BorderPane {

    ApoioPoEContext fsm;

    VBox vBox;
    HBox hBoxDocenteOrientador;
    Label lbInfo, lbId, lbTitulo, lbNAlunoAssociado, lbEmailDocente, lbOrdemPreferencia;
    Label lbIdDados, lbTituloDados, lbNAlunoAssociadoDados, lbEmailDocenteDados, lbOrdemPreferenciaDados;

    public MostraDadosPropostaAtribuida(ApoioPoEContext fsm) {
        this.fsm = fsm;

        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {

        lbInfo = new Label("Informações da Proposta Atribuída");
        lbId = new Label("Identificador da Proposta: ");
        lbTitulo = new Label("Titulo Proposta: ");
        lbNAlunoAssociado = new Label("Aluno Associado: ");
        lbEmailDocente = new Label("Email Docente Orientador: ");
        lbOrdemPreferencia = new Label("Ordem de Preferência : ");

        lbIdDados = new Label();
        lbTituloDados = new Label();
        lbNAlunoAssociadoDados = new Label();
        lbEmailDocenteDados = new Label();
        lbOrdemPreferenciaDados = new Label();

        lbInfo.setFont(new Font("Arial", 30));
        lbId.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        lbTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        lbNAlunoAssociado.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        lbEmailDocente.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        lbOrdemPreferencia.setFont(Font.font("Arial", FontWeight.BOLD, 13));

        hBoxDocenteOrientador = new HBox();
        hBoxDocenteOrientador.getChildren().addAll(lbEmailDocente, lbEmailDocenteDados);

        vBox = new VBox();
        vBox.getChildren().addAll(
                lbInfo,
                new HBox(lbOrdemPreferencia, lbOrdemPreferenciaDados),
                new HBox(lbId,lbIdDados),
                new HBox(lbTitulo, lbTituloDados),
                new HBox(lbNAlunoAssociado, lbNAlunoAssociadoDados)
        );

        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);

        this.setPadding(new Insets(20));

        this.setCenter(vBox);
    }

    private void registerHandlers() {}

    private void update() {}

    public void setData() {

        PropostaAtribuida mostraPropostaAtribuida = (PropostaAtribuida) this.getUserData();
        if(mostraPropostaAtribuida != null){
            lbIdDados.setText(mostraPropostaAtribuida.getId());
            lbTituloDados.setText(mostraPropostaAtribuida.getTitulo());
            lbNAlunoAssociadoDados.setText(String.valueOf(mostraPropostaAtribuida.getNAlunoAssociado()));
            lbOrdemPreferenciaDados.setText(String.valueOf(mostraPropostaAtribuida.getOrdemPreferencia()));

            if (mostraPropostaAtribuida.getEmailDocenteOrientador() != null) {
                lbEmailDocenteDados.setText(mostraPropostaAtribuida.getEmailDocenteOrientador());
                vBox.getChildren().addAll(new HBox(lbEmailDocente, lbEmailDocenteDados));
            }
        }

        for (int i = 1; i < vBox.getChildren().size(); i++) {
            HBox hBox = (HBox) vBox.getChildren().get(i);
            hBox.setAlignment(Pos.CENTER);
        }
    }
}
