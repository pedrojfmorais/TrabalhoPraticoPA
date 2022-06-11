package pt.isec.pa.apoio_poe.ui.gui.docente;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import pt.isec.pa.apoio_poe.model.data.pessoas.Docente;
import pt.isec.pa.apoio_poe.model.errorHandling.ErrorOccurred;
import pt.isec.pa.apoio_poe.model.errorHandling.ErrorsTypes;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEContext;
import pt.isec.pa.apoio_poe.ui.gui.resources.CSSManager;

public class AdicionarOuEditarDocente extends BorderPane {

    ApoioPoEContext fsm;

    Label lbNome, lbEmail;

    TextField tfNome, tfEmail;

    Button btnEnviar;

    boolean editar;

    public AdicionarOuEditarDocente(ApoioPoEContext fsm){
        this.fsm = fsm;

        createViews();
        registerHandlers();
        update();

    }

    private void createViews(){

        CSSManager.applyCSS(this,"verificacaoInputs.css");

        lbNome = new Label("Nome:");
        lbEmail = new Label("Email:");

        tfNome = new TextField();
        tfEmail = new TextField();

        btnEnviar = new Button("Adicionar");


        tfNome.setPrefSize(125, 25);
        tfEmail.setPrefSize(250, 25);
        btnEnviar.setPrefSize(100,25);

        GridPane gp = new GridPane();
        gp.setVgap(4);
        gp.setHgap(10);
        gp.addRow(0, lbNome, tfNome);
        gp.addRow(1, lbEmail);
        gp.addRow(2, tfEmail);
        gp.addRow(3, btnEnviar);

        GridPane.setColumnSpan(tfEmail, 2);
        GridPane.setColumnSpan(btnEnviar, 2);
        GridPane.setHalignment(btnEnviar, HPos.RIGHT);
        this.setPadding(new Insets(20));

        this.setCenter(gp);
    }

    private void registerHandlers(){
        btnEnviar.setOnAction(actionEvent -> {

            boolean errors = false;

            tfNome.getStyleClass().remove("error");
            tfEmail.getStyleClass().remove("error");

            if(tfNome.getText().isBlank()) {
                tfNome.getStyleClass().add("error");
                errors = true;
            }
            if(tfEmail.getText().isBlank()) {
                tfEmail.getStyleClass().add("error");
                errors = true;
            }

            if(!errors){
                if(!editar) { //adicionar
                    if (fsm.adicionarDados(tfNome.getText(), tfEmail.getText())) {
                        this.getScene().getWindow().hide();
                    } else {
                        switch (ErrorOccurred.getInstance().getLastError()) {
                            case INVALID_DOCENTE -> tfNome.getStyleClass().add("error");
                            case DUPLICATED_EMAIL -> tfEmail.getStyleClass().add("error");
                        }
                    }
                } else { //editar
                    if (fsm.editarDados(tfEmail.getText(), tfNome.getText())) {
                        this.getScene().getWindow().hide();
                    } else {
                        if (ErrorOccurred.getInstance().getLastError() == ErrorsTypes.INVALID_DOCENTE) {
                            tfNome.getStyleClass().add("error");
                        }
                    }
                }
            }
        });
    }

    private void update() {
        tfEmail.setDisable(false);

        editar = false;
        btnEnviar.setText("Adicionar");
    }

    public void setData(){
        Docente editarDocente = (Docente) this.getUserData();
        if(editarDocente != null){
            tfNome.setText(String.valueOf(editarDocente.getNome()));
            tfEmail.setText(String.valueOf(editarDocente.getEmail()));

            tfEmail.setDisable(true);

            btnEnviar.setText("Editar");
            editar = true;
        }
    }
}
