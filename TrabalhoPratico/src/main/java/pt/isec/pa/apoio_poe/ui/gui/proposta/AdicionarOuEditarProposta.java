package pt.isec.pa.apoio_poe.ui.gui.proposta;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import pt.isec.pa.apoio_poe.model.data.pessoas.alunos.Aluno;
import pt.isec.pa.apoio_poe.model.data.propostas.Estagio;
import pt.isec.pa.apoio_poe.model.data.propostas.Projeto;
import pt.isec.pa.apoio_poe.model.data.propostas.Proposta;
import pt.isec.pa.apoio_poe.model.errorHandling.ErrorOccurred;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEContext;
import pt.isec.pa.apoio_poe.ui.gui.resources.CSSManager;

public class AdicionarOuEditarProposta extends BorderPane {

    ApoioPoEContext fsm;

    Label lbTipoProposta;
    ComboBox<String> cbTipoProposta;

    Label lbId, lbTitulo, lbNAlunoAssociado;
    TextField tfId, tfTitulo, tfNAlunoAssociado;

    //Projeto
    GridPane gpProjeto;
    HBox hBoxProjeto;
    Label lbRamos, lbEmailDocente;
    ComboBox<String> cbRamos1, cbRamos2, cbRamos3;
    TextField tfEmailDocente;
    Button addRamo;

    //Estagio
    GridPane gpEstagio;
    HBox hBoxEstagio;
    Label lbArea, lbEntidadeAcolhimento;
    ComboBox<String> cbArea1, cbArea2, cbArea3;
    TextField tfEntidadeAcolhimento;
    Button addArea;

    GridPane gp;

    Button btnEnviar;

    boolean editar;

    public AdicionarOuEditarProposta(ApoioPoEContext fsm) {
        this.fsm = fsm;

        createViews();
        registerHandlers();
        update();
    }

    private void createViews(){
        CSSManager.applyCSS(this,"verificacaoInputs.css");

        lbTipoProposta = new Label("Tipo Proposta: ");
        cbTipoProposta = new ComboBox<>();

        lbId = new Label("Identificador Proposta: ");
        lbTitulo = new Label("Titulo Proposta: ");
        lbNAlunoAssociado = new Label("Número do aluno associado à Proposta: ");
        tfId = new TextField();
        tfTitulo = new TextField();
        tfNAlunoAssociado = new TextField();

        gpProjeto = new GridPane();
        lbRamos = new Label("Selecione Ramo:");
        lbEmailDocente = new Label("Insira o email do docente:");
        cbRamos1 = new ComboBox<>();
        cbRamos2 = new ComboBox<>();
        cbRamos3 = new ComboBox<>();
        tfEmailDocente = new TextField();
        addRamo = new Button("+");
        hBoxProjeto = new HBox();

        gpEstagio = new GridPane();
        lbArea = new Label("Selecione Área:");
        lbEntidadeAcolhimento = new Label("Insira o nome da entidade de acolhimento:");
        cbArea1 = new ComboBox<>();
        cbArea2 = new ComboBox<>();
        cbArea3 = new ComboBox<>();
        tfEntidadeAcolhimento = new TextField();
        addArea = new Button("+");
        hBoxEstagio = new HBox();

        btnEnviar = new Button("Adicionar");

        cbTipoProposta.setPrefSize(125, 25);
        tfId.setPrefSize(125, 25);
        tfTitulo.setPrefSize(125, 25);
        tfNAlunoAssociado.setPrefSize(125, 25);
        cbRamos1.setPrefSize(65, 25);
        cbRamos2.setPrefSize(65, 25);
        cbRamos3.setPrefSize(65, 25);
        tfEmailDocente.setPrefSize(125, 25);
        cbArea1.setPrefSize(65, 25);
        cbArea2.setPrefSize(65, 25);
        cbArea3.setPrefSize(65, 25);
        tfEntidadeAcolhimento.setPrefSize(125, 25);

        btnEnviar.setPrefSize(100,25);

        cbTipoProposta.getItems().addAll("Estágio", "Projeto", "Autoproposto");
        cbTipoProposta.setValue("Estágio");

        cbArea1.getItems().addAll(Aluno.ramos);
        cbArea2.getItems().addAll(Aluno.ramos);
        cbArea3.getItems().addAll(Aluno.ramos);
        cbRamos1.getItems().addAll(Aluno.ramos);
        cbRamos2.getItems().addAll(Aluno.ramos);
        cbRamos3.getItems().addAll(Aluno.ramos);

        addRamo.setUserData("1");
        addArea.setUserData("1");

        hBoxProjeto.getChildren().addAll(lbRamos, cbRamos1, addRamo);
        hBoxProjeto.setSpacing(25);

        gpProjeto.setVgap(10);
        gpProjeto.setHgap(4);
        gpProjeto.addRow(0, hBoxProjeto);
        gpProjeto.addRow(1, lbEmailDocente, tfEmailDocente);
        gpProjeto.setAlignment(Pos.CENTER);

        hBoxEstagio.getChildren().addAll(lbArea, cbArea1, addArea);
        hBoxEstagio.setSpacing(25);

        gpEstagio.setVgap(10);
        gpEstagio.setHgap(4);
        gpEstagio.addRow(0, hBoxEstagio);
        gpEstagio.addRow(1, lbEntidadeAcolhimento, tfEntidadeAcolhimento);
        gpEstagio.setAlignment(Pos.CENTER);

        gp = new GridPane();
        gp.setVgap(4);
        gp.setHgap(10);
        gp.addRow(0, lbTipoProposta, cbTipoProposta);
        gp.addRow(1, lbId, tfId);
        gp.addRow(2, lbTitulo, tfTitulo);
        gp.addRow(3, lbNAlunoAssociado, tfNAlunoAssociado);
        gp.addRow(4, gpEstagio);
        gp.addRow(5, btnEnviar);

        gp.setAlignment(Pos.CENTER);

        GridPane.setColumnSpan(hBoxProjeto, 2);
        GridPane.setColumnSpan(hBoxEstagio, 2);
        GridPane.setColumnSpan(gpProjeto, 2);
        GridPane.setColumnSpan(gpEstagio, 2);
        GridPane.setColumnSpan(btnEnviar, 2);

        GridPane.setHalignment(btnEnviar, HPos.RIGHT);

        this.setPadding(new Insets(20));

        this.setCenter(gp);
    }

    private void registerHandlers(){

        cbTipoProposta.setOnAction(event -> {

            gp.getChildren().removeAll(gpEstagio, gpProjeto);

            switch (cbTipoProposta.getValue()){
                case "Estágio" -> gp.addRow(4, gpEstagio);
                case "Projeto" -> gp.addRow(4, gpProjeto);
            }
        });

        addRamo.setOnAction(event -> {
            String nRamos = (String) addRamo.getUserData();

            hBoxProjeto.getChildren().clear();
            if(nRamos.equals("1"))
                hBoxProjeto.getChildren().addAll(lbRamos, cbRamos1, cbRamos2, addRamo);
            else if(nRamos.equals("2"))
                hBoxProjeto.getChildren().addAll(lbRamos, cbRamos1, cbRamos2, cbRamos3);

            addRamo.setUserData(String.valueOf(Integer.parseInt(nRamos)+1));
        });

        addArea.setOnAction(event -> {
            String nAreas = (String) addArea.getUserData();

            hBoxEstagio.getChildren().clear();
            if(nAreas.equals("1"))
                hBoxEstagio.getChildren().addAll(lbArea, cbArea1, cbArea2, addArea);
            else if(nAreas.equals("2"))
                hBoxEstagio.getChildren().addAll(lbArea, cbArea1, cbArea2, cbArea3);

            addArea.setUserData(String.valueOf(Integer.parseInt(nAreas)+1));
        });

        btnEnviar.setOnAction(actionEvent -> {

            boolean errors = false;

            tfId.getStyleClass().remove("error");
            tfTitulo.getStyleClass().remove("error");
            tfNAlunoAssociado.getStyleClass().remove("error");

            cbRamos1.getStyleClass().remove("error");
            cbRamos2.getStyleClass().remove("error");
            cbRamos3.getStyleClass().remove("error");
            tfEmailDocente.getStyleClass().remove("error");

            cbArea1.getStyleClass().remove("error");
            cbArea2.getStyleClass().remove("error");
            cbArea3.getStyleClass().remove("error");
            tfEntidadeAcolhimento.getStyleClass().remove("error");

            long nAluno = 0;
            String areas = "", ramos = "";

            if(tfId.getText().isBlank()) {
                tfId.getStyleClass().add("error");
                errors = true;
            }
            if(tfTitulo.getText().isBlank()) {
                tfTitulo.getStyleClass().add("error");
                errors = true;
            }
            if(!tfNAlunoAssociado.getText().isBlank()) {
                try {
                    nAluno = Long.parseLong(tfNAlunoAssociado.getText());
                } catch (NumberFormatException nfe) {
                    tfNAlunoAssociado.getStyleClass().add("error");
                    errors = true;
                }
            }
            switch (cbTipoProposta.getValue()){
                case "Estágio" -> {
                    if(cbArea1.getValue() == null) {
                        cbArea1.getStyleClass().add("error");
                        errors = true;
                    }

                    if(tfEntidadeAcolhimento.getText().isBlank()) {
                        tfEntidadeAcolhimento.getStyleClass().add("error");
                        errors = true;
                    }

                    areas = cbArea1.getValue();

                    if(cbArea2.getValue() != null)
                        if(!areas.contains(cbArea2.getValue()))
                            areas += "|" + cbArea2.getValue();

                    if(cbArea3.getValue() != null)
                        if(!areas.contains(cbArea3.getValue()))
                            areas += "|" + cbArea3.getValue();
                }
                case "Projeto" -> {
                    if(cbRamos1.getValue() == null) {
                        cbRamos1.getStyleClass().add("error");
                        errors = true;
                    }

                    if(tfEmailDocente.getText().isBlank()) {
                        tfEmailDocente.getStyleClass().add("error");
                        errors = true;
                    }

                    ramos = cbRamos1.getValue();

                    if(cbRamos2.getValue() != null)
                        if(!ramos.contains(cbRamos2.getValue()))
                            ramos += "|" + cbRamos2.getValue();

                    if(cbRamos3.getValue() != null)
                        if(!ramos.contains(cbRamos3.getValue()))
                            ramos += "|" + cbRamos3.getValue();
                }
            }

            boolean res = false;

            if(!errors){
                if(!editar) { //adicionar

                    switch (cbTipoProposta.getValue()){
                        case "Estágio" -> res = fsm.adicionarDados("T1", tfId.getText(), tfTitulo.getText(), areas,
                                    tfEntidadeAcolhimento.getText(), String.valueOf(nAluno));

                        case "Projeto" -> res = fsm.adicionarDados("T2", tfId.getText(), tfTitulo.getText(), ramos,
                                    tfEmailDocente.getText(), String.valueOf(nAluno));

                        default -> res = fsm.adicionarDados("T3", tfId.getText(), tfTitulo.getText(), String.valueOf(nAluno));
                    }
                } else { //editar
                    switch (cbTipoProposta.getValue()){
                        case "Estágio" -> res = fsm.editarDados(tfId.getText(), tfTitulo.getText(), areas,
                                tfEntidadeAcolhimento.getText(), String.valueOf(nAluno));

                        case "Projeto" -> res = fsm.editarDados(tfId.getText(), tfTitulo.getText(), ramos,
                                tfEmailDocente.getText(), String.valueOf(nAluno));

                        default -> res = fsm.editarDados(tfId.getText(), tfTitulo.getText(), String.valueOf(nAluno));
                    }
                }

                if(res) {
                    this.getScene().getWindow().hide();
                } else {
                    switch (ErrorOccurred.getInstance().getLastError()) {

                        case DUPLICATED_ID_PROPOSTA, INVALID_ID_PROPOSTA -> tfId.getStyleClass().add("error");
                        case INVALID_NUMERO_ALUNO, ALUNO_JA_TEM_PROPOSTA -> tfNAlunoAssociado.getStyleClass().add("error");
                        case INVALID_RAMO -> {
                            cbArea1.getStyleClass().add("error");
                            cbRamos1.getStyleClass().add("error");
                        }
                        case INVALID_DOCENTE -> tfEmailDocente.getStyleClass().add("error");
                    }
                }
            }
        });
    }

    private void update(){
        tfId.setDisable(false);

        editar = false;
        btnEnviar.setText("Adicionar");
    }

    public void setData(){
        Proposta editarProposta = (Proposta) this.getUserData();
        if(editarProposta != null){

            tfId.setText(editarProposta.getId());
            tfTitulo.setText(editarProposta.getTitulo());
            tfNAlunoAssociado.setText(String.valueOf(editarProposta.getnAlunoAssociado()));

            gp.getChildren().removeAll(gpEstagio, gpProjeto);

            if(editarProposta instanceof Estagio e){
                cbTipoProposta.setValue("Estágio");
                gp.addRow(4, gpEstagio);

                var areas = e.getAreasDestino().split("\\|");

                for (int i = 0; i < areas.length; i++) {
                    switch (i){
                        case 0 -> cbArea1.setValue(areas[i]);
                        case 1 -> cbArea2.setValue(areas[i]);
                        case 2 -> cbArea3.setValue(areas[i]);
                    }
                }

                hBoxEstagio.getChildren().clear();
                switch (areas.length){
                    case 1 -> hBoxEstagio.getChildren().addAll(lbArea, cbArea1, addArea);
                    case 2 -> hBoxEstagio.getChildren().addAll(lbArea, cbArea1, cbArea2, addArea);
                    case 3 -> hBoxEstagio.getChildren().addAll(lbArea, cbArea1, cbArea2, cbArea3);
                }

                addArea.setUserData(String.valueOf(areas.length));

                tfEntidadeAcolhimento.setText(e.getEntidadeAcolhimento());

            } else if(editarProposta instanceof Projeto p){
                cbTipoProposta.setValue("Projeto");
                gp.addRow(4, gpProjeto);

                var ramos = p.getRamosDestino().split("\\|");

                for (int i = 0; i < ramos.length; i++) {
                    switch (i){
                        case 0 -> cbRamos1.setValue(ramos[i]);
                        case 1 -> cbRamos2.setValue(ramos[i]);
                        case 2 -> cbRamos3.setValue(ramos[i]);
                    }
                }

                hBoxProjeto.getChildren().clear();
                switch (ramos.length){
                    case 1 -> hBoxProjeto.getChildren().addAll(lbArea, cbRamos1, addRamo);
                    case 2 -> hBoxProjeto.getChildren().addAll(lbArea, cbRamos1, cbRamos2, addRamo);
                    case 3 -> hBoxProjeto.getChildren().addAll(lbArea, cbRamos1, cbRamos2, cbRamos3);
                }

                addRamo.setUserData(String.valueOf(ramos.length));

                tfEmailDocente.setText(p.getEmailDocente());

            } else
                cbTipoProposta.setValue("Autoproposto");

            cbTipoProposta.setDisable(true);
            tfId.setDisable(true);

            btnEnviar.setText("Editar");
            editar = true;
        }
    }
}
