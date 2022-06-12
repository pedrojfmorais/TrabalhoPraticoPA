package pt.isec.pa.apoio_poe.ui.gui.fase4.docentesOrientadores;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import pt.isec.pa.apoio_poe.model.data.propostas.PropostaAtribuida;
import pt.isec.pa.apoio_poe.model.errorHandling.ErrorOccurred;
import pt.isec.pa.apoio_poe.model.errorHandling.ErrorsTypes;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEContext;
import pt.isec.pa.apoio_poe.ui.gui.resources.CSSManager;

public class AdicionarOuEditarOrientador extends BorderPane {

    ApoioPoEContext fsm;

    Label lbNAluno, lbEmailDocente;

    TextField tfNAluno, tfEmailDocente;

    Button btnEnviar;

    boolean editar;

    public AdicionarOuEditarOrientador(ApoioPoEContext fsm) {
        this.fsm = fsm;

        createViews();
        registerHandlers();
        update();
    }

    private void createViews(){
        CSSManager.applyCSS(this,"verificacaoInputs.css");

        lbNAluno = new Label("Número do aluno associado à Proposta: ");
        lbEmailDocente = new Label("Insira o email do docente:");

        tfNAluno = new TextField();
        tfEmailDocente = new TextField();

        btnEnviar = new Button("Adicionar");


        tfNAluno.setPrefSize(125, 25);
        tfEmailDocente.setPrefSize(125, 25);

        btnEnviar.setPrefSize(100,25);

        GridPane gp = new GridPane();
        gp.setVgap(4);
        gp.setHgap(10);
        gp.addRow(0, lbNAluno, lbEmailDocente);
        gp.addRow(1, tfNAluno, tfEmailDocente);
        gp.addRow(2, btnEnviar);

        gp.setAlignment(Pos.CENTER);

        GridPane.setColumnSpan(btnEnviar, 2);

        GridPane.setHalignment(btnEnviar, HPos.RIGHT);

        this.setPadding(new Insets(20));

        this.setCenter(gp);
    }

    private void registerHandlers(){

        btnEnviar.setOnAction(actionEvent -> {

            boolean errors = false;

            tfNAluno.getStyleClass().remove("error");
            tfEmailDocente.getStyleClass().remove("error");

            if(!tfNAluno.getText().isBlank()) {
                try {
                    long nAluno = Long.parseLong(tfNAluno.getText());
                } catch (NumberFormatException nfe) {
                    tfNAluno.getStyleClass().add("error");
                    errors = true;
                }
            }

            if(tfEmailDocente.getText().isBlank()) {
                tfEmailDocente.getStyleClass().add("error");
                errors = true;
            }

            boolean res;

            if(!errors){
                if(!editar) //adicionar
                    res = fsm.adicionarDados(tfNAluno.getText(), tfEmailDocente.getText());
                else //editar
                     res = fsm.editarDados(tfNAluno.getText(), tfEmailDocente.getText());

                if(res)
                    this.getScene().getWindow().hide();
                else {
                    if (ErrorOccurred.getInstance().getLastError() == ErrorsTypes.INVALID_DOCENTE) {
                        tfEmailDocente.getStyleClass().add("error");
                    } else {
                        tfNAluno.getStyleClass().add("error");
                    }
                    ErrorOccurred.getInstance().setError(ErrorsTypes.NONE);
                }
            }
        });
    }

    private void update(){
        tfNAluno.setDisable(false);

        editar = false;
        btnEnviar.setText("Adicionar");
    }

    public void setData(){
        PropostaAtribuida editarProposta = (PropostaAtribuida) this.getUserData();
        if(editarProposta != null){

            tfNAluno.setText(String.valueOf(editarProposta.getNAlunoAssociado()));
            tfEmailDocente.setText(String.valueOf(editarProposta.getEmailDocenteOrientador()));

            tfNAluno.setDisable(true);

            btnEnviar.setText("Editar");
            editar = true;
        }
    }
}
