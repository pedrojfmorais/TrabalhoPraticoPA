package pt.isec.pa.apoio_poe.ui.gui.fase3.propostaAtribuida;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import pt.isec.pa.apoio_poe.model.errorHandling.ErrorOccurred;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEContext;
import pt.isec.pa.apoio_poe.ui.gui.resources.CSSManager;

public class AtribuirPropostaGUI extends BorderPane {
    ApoioPoEContext fsm;

    Label lbNAluno, lbIdProposta;

    TextField tfNAluno, tfIdProposta;

    Button btnEnviar;

    public AtribuirPropostaGUI(ApoioPoEContext fsm){
        this.fsm = fsm;

        createViews();
        registerHandlers();
        update();
    }

    private void createViews(){

        CSSManager.applyCSS(this,"verificacaoInputs.css");

        lbNAluno = new Label("Número Aluno:");
        lbIdProposta = new Label("Identificador Proposta:");

        tfNAluno = new TextField();
        tfIdProposta = new TextField();

        btnEnviar = new Button("Adicionar");

        tfNAluno.setPrefSize(125, 25);
        tfIdProposta.setPrefSize(125, 25);
        btnEnviar.setPrefSize(100,25);

        GridPane gp = new GridPane();
        gp.setVgap(4);
        gp.setHgap(10);
        gp.addRow(0, lbNAluno, tfNAluno);
        gp.addRow(1, lbIdProposta, tfIdProposta);
        gp.addRow(2, btnEnviar);

        GridPane.setColumnSpan(btnEnviar, 2);

        GridPane.setHalignment(btnEnviar, HPos.RIGHT);

        this.setPadding(new Insets(20));

        this.setCenter(gp);
    }

    private void registerHandlers(){

        btnEnviar.setOnAction(actionEvent -> {
            boolean errors = false;

            tfNAluno.getStyleClass().remove("error");
            tfIdProposta.getStyleClass().remove("error");

            if(tfNAluno.getText().isBlank()) {
                tfNAluno.getStyleClass().add("error");
                errors = true;
            }

            try {
                long l = Long.parseLong(tfNAluno.getText());
            } catch (NumberFormatException nfe) {
                tfNAluno.getStyleClass().add("error");
                errors = true;
            }

            if(tfIdProposta.getText().isBlank()){
                tfIdProposta.getStyleClass().add("error");
                errors = true;
            }

            if(!errors) {
                if(fsm.adicionarDados(tfIdProposta.getText(), tfNAluno.getText()))
                    this.getScene().getWindow().hide();
                else {
                    switch (ErrorOccurred.getInstance().getLastError()) {
                        case INVALID_NUMERO_ALUNO, ALUNO_JA_TEM_PROPOSTA -> tfNAluno.getStyleClass().add("error");
                        case INVALID_ID_PROPOSTA, PROPOSTA_JA_FOI_ATRIBUIDA -> tfIdProposta.getStyleClass().add("error");
                        case ALUNO_PROPOSTA_AREA_NAO_CORRESPONDEM -> {
                            tfNAluno.getStyleClass().add("error");
                            tfIdProposta.getStyleClass().add("error");
                        }
                    }
                }
            }
        });
    }

    private void update() {}
}
