package pt.isec.pa.apoio_poe.ui.gui.fase1.aluno;

import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import pt.isec.pa.apoio_poe.model.data.pessoas.alunos.Aluno;
import pt.isec.pa.apoio_poe.model.errorHandling.ErrorOccurred;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEContext;
import pt.isec.pa.apoio_poe.ui.gui.resources.CSSManager;

import java.text.DecimalFormat;

public class AdicionarOuEditarAlunoGUI extends BorderPane {
    ApoioPoEContext fsm;

    Label lbNAluno, lbNome, lbEmail, lbSiglaCurso, lbSiglaRamos, lbClassif, lbAcessoEstagio;

    TextField tfNAluno, tfNome, tfEmail, tfClassif;
    Slider slClassif;
    ComboBox<String> cbCurso, cbRamo;
    CheckBox ckAcessoEstagio;

    Button btnEnviar;

    boolean editar;

    public AdicionarOuEditarAlunoGUI(ApoioPoEContext fsm){
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
        slClassif = new Slider(0, 1, 0.01);
        ckAcessoEstagio = new CheckBox();

        btnEnviar = new Button("Adicionar");

        tfNAluno.setPrefSize(125, 25);
        tfNome.setPrefSize(125, 25);
        tfEmail.setPrefSize(250, 25);
        cbCurso.setPrefSize(125,25);
        cbRamo.setPrefSize(125,25);
        tfClassif.setMaxSize(40,25);
        slClassif.setMaxSize(100, 25);
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
        gp.addRow(7, new HBox(tfClassif, slClassif), ckAcessoEstagio);
        gp.addRow(8, btnEnviar);

        GridPane.setColumnSpan(tfEmail, 2);
        GridPane.setColumnSpan(btnEnviar, 2);
        GridPane.setHalignment(btnEnviar, HPos.RIGHT);
        this.setPadding(new Insets(20));

        this.setCenter(gp);
    }

    private void registerHandlers(){

        slClassif.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            DecimalFormat f = new DecimalFormat("#.##");
            tfClassif.setText(f.format(newValue));
        });

        tfClassif.textProperty().addListener((observableValue, oldValue, newValue) -> {

            try {
                slClassif.setValue(Double.parseDouble(tfClassif.getText()));
            }catch (Exception ignored) {

            }
        });

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

            String classficacao = tfClassif.getText().replace(',', '.');
            try {

                double d = Double.parseDouble(classficacao);
            } catch (NumberFormatException nfe) {
                tfClassif.getStyleClass().add("error");
                errors = true;
            }

            if(!errors){
                if(!editar) { //adicionar
                    if (fsm.adicionarDados(tfNAluno.getText(), tfNome.getText(), tfEmail.getText(),
                            cbCurso.getSelectionModel().getSelectedItem(), cbRamo.getSelectionModel().getSelectedItem(),
                            classficacao, String.valueOf(ckAcessoEstagio.isSelected()))) {
                        this.getScene().getWindow().hide();
                    } else {
                        switch (ErrorOccurred.getInstance().getLastError()) {

                            case DUPLICATED_NUMERO_ALUNO, INVALID_NUMERO_ALUNO -> tfNAluno.getStyleClass().add("error");
                            case DUPLICATED_EMAIL -> tfEmail.getStyleClass().add("error");
                            case INVALID_CURSO -> cbCurso.getStyleClass().add("error");
                            case INVALID_RAMO -> cbRamo.getStyleClass().add("error");
                            case INVALID_CLASSIFICACAO -> tfClassif.getStyleClass().add("error");

                        }
                    }
                } else { //editar
                    if (fsm.editarDados(tfNAluno.getText(), tfNome.getText(),
                            cbCurso.getSelectionModel().getSelectedItem(), cbRamo.getSelectionModel().getSelectedItem(),
                            classficacao, String.valueOf(ckAcessoEstagio.isSelected()))) {
                        this.getScene().getWindow().hide();
                    } else {
                        switch (ErrorOccurred.getInstance().getLastError()) {

                            case INVALID_NUMERO_ALUNO -> tfNAluno.getStyleClass().add("error");
                            case INVALID_CURSO -> cbCurso.getStyleClass().add("error");
                            case INVALID_RAMO -> cbRamo.getStyleClass().add("error");
                            case INVALID_CLASSIFICACAO -> tfClassif.getStyleClass().add("error");

                        }
                    }
                }
            }
        });
    }

    private void update() {

        tfNAluno.setDisable(false);
        tfEmail.setDisable(false);

        editar = false;
        btnEnviar.setText("Adicionar");
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

            tfNAluno.setDisable(true);
            tfEmail.setDisable(true);

            btnEnviar.setText("Editar");
            editar = true;
        }
    }
}
