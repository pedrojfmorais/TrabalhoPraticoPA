package pt.isec.pa.apoio_poe.ui.gui.fase3.propostaAtribuida;

import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import pt.isec.pa.apoio_poe.model.data.pessoas.alunos.Aluno;
import pt.isec.pa.apoio_poe.model.data.propostas.Proposta;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEContext;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEState;

import java.util.ArrayList;

public class AtribuicaoAutomatica extends BorderPane {
    ApoioPoEContext fsm;

    Label lbAluno, lbProposta;
    ComboBox cbAluno, cbProposta;
    Button btnResolverConflito;

    ArrayList<Long> nAlunos;

    public AtribuicaoAutomatica(ApoioPoEContext fsm){
        this.fsm = fsm;

        createViews();
        registerHandlers();
        update();
    }

    private void createViews(){
        lbAluno = new Label("Escolha o Aluno:");
        lbProposta = new Label("Escolha a Proposta:");

        cbAluno = new ComboBox();
        cbProposta = new ComboBox();
        btnResolverConflito = new Button("Resolver Conflito");

        cbAluno.setPrefSize(125, 30);
        cbProposta.setPrefSize(75, 30);
        btnResolverConflito.setPrefSize(150, 30);

        GridPane gp = new GridPane();

        gp.setVgap(15);
        gp.setHgap(10);
        gp.addRow(0, lbAluno, lbProposta);
        gp.addRow(1, cbAluno, cbProposta);
        gp.addRow(2, btnResolverConflito);

        GridPane.setColumnSpan(btnResolverConflito, 2);
        GridPane.setHalignment(btnResolverConflito, HPos.CENTER);

        gp.setAlignment(Pos.CENTER);

        this.setPadding(new Insets(20));

        this.setCenter(gp);
    }

    private void registerHandlers(){
        fsm.addPropertyChangeListener(ApoioPoEContext.PROP_FASE, evt -> update());

        cbAluno.getSelectionModel().selectedItemProperty().addListener(ev ->{
            if(cbAluno.getSelectionModel().getSelectedItem() != null)
                setComboBoxPropostas((Long) cbAluno.getSelectionModel().getSelectedItem());
        });

        btnResolverConflito.setOnAction(event -> {
            if(fsm.adicionarDados(String.valueOf(cbAluno.getSelectionModel().getSelectedItem()),
                    String.valueOf(cbProposta.getSelectionModel().getSelectedItem())))
                executaAutomaticamente();
        });
    }

    private void update(){

        if(fsm != null && fsm.getState() == ApoioPoEState.Fase3AtribuicaoAutomatica) {
            this.setVisible(true);
            executaAutomaticamente();
        }else
            this.setVisible(false);
    }

    private void setComboBoxPropostas(long nAluno){
        ArrayList<Proposta> propostas = fsm.consultarPropostas(nAluno == nAlunos.get(0));
        ArrayList<String> idPropostas = new ArrayList<>();

        for (var proposta : propostas)
            idPropostas.add(proposta.getId());

        cbProposta.setItems(FXCollections.observableArrayList(idPropostas));
    }

    private void executaAutomaticamente(){
        if(!fsm.atribuicaoAutomaticaPropostasDisponiveis()) {

            ArrayList<Aluno> alunos = fsm.consultarAlunos();
            nAlunos = new ArrayList<>();

            for (var aluno : alunos)
                nAlunos.add(aluno.getNAluno());

            cbAluno.setItems(FXCollections.observableArrayList(nAlunos));
            cbAluno.getSelectionModel().select(nAlunos.get(0));

            setComboBoxPropostas(nAlunos.get(0));
            return;
        }
        fsm.regressarFase();
    }
}
