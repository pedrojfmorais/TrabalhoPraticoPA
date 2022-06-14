package pt.isec.pa.apoio_poe.ui.gui.fase2.candidatura;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import pt.isec.pa.apoio_poe.model.data.Candidatura;
import pt.isec.pa.apoio_poe.model.errorHandling.ErrorOccurred;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEContext;
import pt.isec.pa.apoio_poe.ui.gui.resources.CSSManager;

import java.util.ArrayList;

public class AdicionarOuEditarCandidaturaGUI extends BorderPane {
    ApoioPoEContext fsm;

    Label lbNAluno, lbPropostas;

    TextField tfNAluno;

    final static int MAX_PROPOSTAS = 5;
    VBox vBoxPropostas;
    ArrayList<TextField> tfPropostas;
    Button btnAdd;

    Button btnEnviar;

    boolean editar;

    public AdicionarOuEditarCandidaturaGUI(ApoioPoEContext fsm){
        this.fsm = fsm;

        createViews();
        registerHandlers();
        update();

    }

    private void createViews(){

        CSSManager.applyCSS(this,"verificacaoInputs.css");

        lbNAluno = new Label("Número Aluno:");
        lbPropostas = new Label("Propostas:");

        tfNAluno = new TextField();

        tfPropostas = new ArrayList<>();
        tfPropostas.add(new TextField());

        btnAdd = new Button("+");
        btnEnviar = new Button("Adicionar");

        vBoxPropostas = new VBox(tfPropostas.get(0), btnAdd);
        vBoxPropostas.setAlignment(Pos.CENTER);
        vBoxPropostas.setSpacing(5);

        tfNAluno.setPrefSize(125, 25);
        btnEnviar.setPrefSize(100,25);

        btnAdd.setUserData(1);

        GridPane gp = new GridPane();
        gp.setVgap(4);
        gp.setHgap(10);
        gp.addRow(0, lbNAluno, tfNAluno);
        gp.addRow(1, lbPropostas);
        gp.addRow(2, vBoxPropostas);

        gp.addRow(3, btnEnviar);

        GridPane.setColumnSpan(lbPropostas, 2);
        GridPane.setColumnSpan(vBoxPropostas, 2);
        GridPane.setColumnSpan(btnEnviar, 2);

        GridPane.setHalignment(lbPropostas, HPos.CENTER);
        GridPane.setHalignment(btnEnviar, HPos.RIGHT);

        this.setPadding(new Insets(20));

        this.setCenter(gp);
    }

    private void registerHandlers(){

        btnAdd.setOnAction(event -> {
            int nPropostas = (int) btnAdd.getUserData();

            if(nPropostas < MAX_PROPOSTAS){
                vBoxPropostas.getChildren().remove(btnAdd);
                tfPropostas.add(new TextField());
                vBoxPropostas.getChildren().add(tfPropostas.get(nPropostas));
                btnAdd.setUserData(nPropostas+1);

                if(nPropostas + 1 < MAX_PROPOSTAS)
                    vBoxPropostas.getChildren().add(btnAdd);
            }
        });

        btnEnviar.setOnAction(actionEvent -> {

            boolean errors = false;
            ArrayList<String> propostas = new ArrayList<>();

            tfNAluno.getStyleClass().remove("error");

            for(var tfProposta : tfPropostas)
                tfProposta.getStyleClass().remove("error");

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

            if(tfPropostas.get(0).getText().isBlank()){
                tfPropostas.get(0).getStyleClass().add("error");
                errors = true;
            }

            for (int i = 0; i < tfPropostas.size(); i++) {
                if(!tfPropostas.get(i).getText().isBlank())
                    propostas.add(tfPropostas.get(i).getText());
            }

            String[] dados = new String[propostas.size() + 1];

            dados[0] = tfNAluno.getText();

            for (int i = 1; i <= propostas.size(); i++) {
                dados[i] = propostas.get(i-1);
            }


            if(!errors) {
                boolean res = false;
                if (!editar) //adicionar
                    res = fsm.adicionarDados(dados);
                else //editar
                    res = fsm.editarDados(dados);

                if (res)
                    this.getScene().getWindow().hide();
                else {
                    switch (ErrorOccurred.getInstance().getLastError()) {

                        case INVALID_ID_PROPOSTA -> {
                            for (TextField tfProposta : tfPropostas) {
                                if (fsm.getProposta(tfProposta.getText()) == null)
                                    tfProposta.getStyleClass().add("error");
                            }
                        }
                        case SEM_PROPOSTAS_ESPECIFICADAS -> tfPropostas.get(0).getStyleClass().add("error");

                        case DUPLICATED_ID_PROPOSTA -> {
                            for (int i = tfPropostas.size() - 1; i >= 0; i--) {
                                for (int j = 0; j < i; j++) {
                                    if (tfPropostas.get(i).getText().equals(tfPropostas.get(j).getText()))
                                        tfPropostas.get(i).getStyleClass().add("error");
                                }
                            }
                        }

                        case PROPOSTA_JA_TEM_ALUNO_ASSOCIADO -> {
                            for (TextField tfProposta : tfPropostas) {
                                if (fsm.getProposta(tfProposta.getText()).getNAlunoAssociado() != 0)
                                    tfProposta.getStyleClass().add("error");
                            }
                        }

                        case INVALID_NUMERO_ALUNO, ALUNO_JA_TEM_CANDIDATURA, ALUNO_NAO_TEM_CANDIDATURA,
                                ALUNO_PROPOSTA_AREA_NAO_CORRESPONDEM, ALUNO_JA_TEM_PROPOSTA -> tfNAluno.getStyleClass().add("error");
                    }
                }
            }
        });
    }

    private void update() {

        tfNAluno.setDisable(false);

        editar = false;
        btnEnviar.setText("Adicionar");
    }

    public void setData(){
        Candidatura editarCandidatura = (Candidatura) this.getUserData();
        if(editarCandidatura != null){
            tfNAluno.setText(String.valueOf(editarCandidatura.getNAluno()));

            vBoxPropostas.getChildren().clear();
            tfPropostas.clear();

            for(int i = 0; i < editarCandidatura.getIdPropostas().size(); i++) {
                tfPropostas.add(new TextField());
                tfPropostas.get(i).setText(editarCandidatura.getIdPropostas().get(i));
                vBoxPropostas.getChildren().add(tfPropostas.get(i));
            }

            if(editarCandidatura.getIdPropostas().size() < 5)
                vBoxPropostas.getChildren().add(btnAdd);

            btnAdd.setUserData(editarCandidatura.getIdPropostas().size());

            tfNAluno.setDisable(true);

            btnEnviar.setText("Editar");
            editar = true;
        }
    }
}
