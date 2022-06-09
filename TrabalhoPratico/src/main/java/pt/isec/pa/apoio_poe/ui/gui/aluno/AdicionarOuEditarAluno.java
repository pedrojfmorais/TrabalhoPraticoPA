package pt.isec.pa.apoio_poe.ui.gui.aluno;

import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import pt.isec.pa.apoio_poe.model.data.pessoas.alunos.Aluno;
import pt.isec.pa.apoio_poe.model.errorHandling.ErrorOccurred;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEContext;
import pt.isec.pa.apoio_poe.ui.gui.resources.CSSManager;

public class AdicionarOuEditarAluno extends BorderPane {
    ApoioPoEContext fsm;

    Label lbNAluno;
    Label lbNome;
    Label lbEmail;
    Label lbSiglaCurso;
    Label lbSiglaRamos;
    Label lbClassif;
    Label lbAcessoEstagio;

    TextField tfNAluno;
    TextField tfNome;
    TextField tfEmail;
    ComboBox<String> cbCurso;
    ComboBox<String> cbRamo;
    TextField tfClassif;
    CheckBox ckAcessoEstagio;

    Button btnEnviar;

    public AdicionarOuEditarAluno(ApoioPoEContext fsm){
        this.fsm = fsm;

        createViews();
        registerHandlers();
        update();

    }

    private void createViews(){

        CSSManager.applyCSS(this,"verificacaoInputs.css");

        lbNAluno = new Label("Número Aluno:");
        lbNome = new Label("Nome:");
        lbEmail = new Label("Email:");
        lbSiglaCurso = new Label("Curso:");
        lbSiglaRamos = new Label("Ramo:");
        lbClassif = new Label("Classificação:");
        lbAcessoEstagio = new Label("Acesso a Estágio:");

        tfNAluno = new TextField();
        tfNome = new TextField();
        tfEmail = new TextField();
        cbCurso = new ComboBox<>();
        cbRamo = new ComboBox<>();
        tfClassif = new TextField();
        ckAcessoEstagio = new CheckBox();

        btnEnviar = new Button("Adicionar");

        tfNAluno.setPrefSize(125, 25);
        tfNome.setPrefSize(125, 25);
        tfEmail.setPrefSize(250, 25);
        cbCurso.setPrefSize(125,25);
        cbRamo.setPrefSize(125,25);
        tfClassif.setPrefSize(125,25);
        ckAcessoEstagio.setPrefSize(25,25);
        btnEnviar.setPrefSize(100,25);

        cbCurso.setItems(FXCollections.observableArrayList(Aluno.cursos));
        cbRamo.setItems(FXCollections.observableArrayList(Aluno.ramos));

        GridPane gp = new GridPane();
        gp.setVgap(4);
        gp.setHgap(10);
        gp.addRow(0, lbNAluno, lbNome);
        gp.addRow(1, tfNAluno, tfNome);
        gp.addRow(2, lbEmail);
        gp.addRow(3, tfEmail);
        gp.addRow(4, lbSiglaCurso, lbSiglaRamos);
        gp.addRow(5, cbCurso, cbRamo);
        gp.addRow(6, lbClassif, lbAcessoEstagio);
        gp.addRow(7, tfClassif, ckAcessoEstagio);
        gp.addRow(8, btnEnviar);

        GridPane.setColumnSpan(tfEmail, 2);
        GridPane.setColumnSpan(btnEnviar, 2);
        GridPane.setHalignment(btnEnviar, HPos.RIGHT);
        this.setPadding(new Insets(20));

        this.setCenter(gp);
    }

    private void registerHandlers(){
        btnEnviar.setOnAction(actionEvent -> {

            boolean errors = false;

            tfNAluno.getStyleClass().remove("error");
            tfNome.getStyleClass().remove("error");
            tfEmail.getStyleClass().remove("error");
            cbCurso.getStyleClass().remove("error");
            cbRamo.getStyleClass().remove("error");
            tfClassif.getStyleClass().remove("error");

            if(tfNAluno.getText().isBlank()) {
                tfNAluno.getStyleClass().add("error");
                errors = true;
            }
            if(tfNome.getText().isBlank()) {
                tfNome.getStyleClass().add("error");
                errors = true;
            }
            if(tfEmail.getText().isBlank()) {
                tfEmail.getStyleClass().add("error");
                errors = true;
            }
            if(cbCurso.getSelectionModel().getSelectedItem() == null) {
                cbCurso.getStyleClass().add("error");
                errors = true;
            }
            if(cbRamo.getSelectionModel().getSelectedItem() == null) {
                cbRamo.getStyleClass().add("error");
                errors = true;
            }
            if(tfClassif.getText().isBlank()) {
                tfClassif.getStyleClass().add("error");
                errors = true;
            }

            try {
                long l = Long.parseLong(tfNAluno.getText());
            } catch (NumberFormatException nfe) {
                tfNAluno.getStyleClass().add("error");
                errors = true;
            }

            try {
                double d = Double.parseDouble(tfClassif.getText());
            } catch (NumberFormatException nfe) {
                tfClassif.getStyleClass().add("error");
                errors = true;
            }

            if(!errors){
                if(fsm.adicionarDados(tfNAluno.getText(), tfNome.getText(), tfEmail.getText(),
                        cbCurso.getSelectionModel().getSelectedItem(), cbRamo.getSelectionModel().getSelectedItem(),
                        tfClassif.getText(), String.valueOf(ckAcessoEstagio.isSelected()))){
                    this.getScene().getWindow().hide();
                }else {
                    switch (ErrorOccurred.getInstance().getLastError()){

                        case DUPLICATED_NUMERO_ALUNO, INVALID_NUMERO_ALUNO -> tfNAluno.getStyleClass().add("error");
                        case DUPLICATED_EMAIL -> tfEmail.getStyleClass().add("error");
                        case INVALID_CURSO -> cbCurso.getStyleClass().add("error");
                        case INVALID_RAMO -> cbRamo.getStyleClass().add("error");
                        case INVALID_CLASSIFICACAO -> tfClassif.getStyleClass().add("error");

                    }
                }
            }
        });
    }

    private void update() {

    }

    public void setData(){
        Aluno editarAluno = (Aluno) this.getUserData();
        if(editarAluno != null){
            tfNAluno.setText(String.valueOf(editarAluno.getNAluno()));
            tfNome.setText(String.valueOf(editarAluno.getNome()));
            tfEmail.setText(String.valueOf(editarAluno.getEmail()));
            cbCurso.getSelectionModel().select(editarAluno.getSiglaCurso());
            cbRamo.getSelectionModel().select(editarAluno.getSiglaRamo());
            tfClassif.setText(String.valueOf(editarAluno.getClassificacao()));
            ckAcessoEstagio.setSelected(editarAluno.isAcessoEstagio());
        }
    }
}
