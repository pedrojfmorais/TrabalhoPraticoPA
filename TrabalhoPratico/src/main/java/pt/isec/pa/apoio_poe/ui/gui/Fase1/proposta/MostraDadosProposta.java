package pt.isec.pa.apoio_poe.ui.gui.Fase1.proposta;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import pt.isec.pa.apoio_poe.model.data.propostas.Estagio;
import pt.isec.pa.apoio_poe.model.data.propostas.Projeto;
import pt.isec.pa.apoio_poe.model.data.propostas.Proposta;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEContext;

public class MostraDadosProposta extends BorderPane {

    ApoioPoEContext fsm;

    VBox vBox;
    HBox hBoxnAlunoAssociado;
    Label lbInfo, lbTipo, lbId, lbTitulo, lbNAlunoAssociado, lbRamo, lbEmailDocente, lbEntidadeAcolhimento;
    Label lbTipoDados, lbIdDados, lbTituloDados, lbNAlunoAssociadoDados, lbRamoDados,
            lbEmailDocenteDados, lbEntidadeAcolhimentoDados;

    public MostraDadosProposta(ApoioPoEContext fsm) {
        this.fsm = fsm;

        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {

        lbInfo = new Label("Informações da Proposta");
        lbTipo = new Label("Tipo de Proposta: ");
        lbId = new Label("Identificador da Proposta: ");
        lbTitulo = new Label("Titulo Proposta: ");
        lbNAlunoAssociado = new Label("Aluno Associado: ");
        lbRamo = new Label("Ramo Proposta: ");
        lbEmailDocente = new Label("Email Docente: ");
        lbEntidadeAcolhimento = new Label("Entidade de Acolhimento: ");

        lbTipoDados = new Label();
        lbIdDados = new Label();
        lbTituloDados = new Label();
        lbNAlunoAssociadoDados = new Label();
        lbRamoDados = new Label();
        lbEmailDocenteDados = new Label();
        lbEntidadeAcolhimentoDados = new Label();

        lbInfo.setFont(new Font("Arial", 30));
        lbTipo.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        lbId.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        lbTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        lbNAlunoAssociado.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        lbRamo.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        lbEmailDocente.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        lbEntidadeAcolhimento.setFont(Font.font("Arial", FontWeight.BOLD, 13));

        hBoxnAlunoAssociado = new HBox();
        hBoxnAlunoAssociado.getChildren().addAll(lbNAlunoAssociado, lbNAlunoAssociadoDados);

        vBox = new VBox();
        vBox.getChildren().addAll(
                lbInfo,
                new HBox(lbTipo, lbTipoDados),
                new HBox(lbId,lbIdDados),
                new HBox(lbTitulo, lbTituloDados)
        );

        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);

        this.setPadding(new Insets(20));

        this.setCenter(vBox);
    }

    private void registerHandlers() {}

    private void update() {}

    public void setData() {

        Proposta mostraProposta = (Proposta) this.getUserData();
        if(mostraProposta != null){
            lbIdDados.setText(mostraProposta.getId());
            lbTituloDados.setText(mostraProposta.getTitulo());
            if(mostraProposta.getnAlunoAssociado() != 0) {
                lbNAlunoAssociadoDados.setText(String.valueOf(mostraProposta.getnAlunoAssociado()));
                vBox.getChildren().add(hBoxnAlunoAssociado);
            } else
                vBox.getChildren().remove(hBoxnAlunoAssociado);

            if(mostraProposta instanceof Estagio e){
                lbTipoDados.setText("Estágio");
                lbRamoDados.setText(e.getAreasDestino());
                lbEntidadeAcolhimentoDados.setText(e.getEntidadeAcolhimento());
                vBox.getChildren().addAll(new HBox(lbRamo, lbRamoDados));
                vBox.getChildren().addAll(new HBox(lbEntidadeAcolhimento, lbEntidadeAcolhimentoDados));

            } else if (mostraProposta instanceof Projeto p) {
                lbTipoDados.setText("Projeto");
                lbRamoDados.setText(p.getRamosDestino());
                lbEmailDocenteDados.setText(p.getEmailDocente());
                vBox.getChildren().addAll(new HBox(lbRamo, lbRamoDados));
                vBox.getChildren().addAll(new HBox(lbEmailDocente, lbEmailDocenteDados));
            } else
                lbTipoDados.setText("Autoproposto");
        }

        for (int i = 1; i < vBox.getChildren().size(); i++) {
            HBox hBox = (HBox) vBox.getChildren().get(i);
            hBox.setAlignment(Pos.CENTER);
        }

    }
}
