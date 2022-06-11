package pt.isec.pa.apoio_poe.ui.gui;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEContext;
import pt.isec.pa.apoio_poe.ui.gui.Fase1.Fase1;
import pt.isec.pa.apoio_poe.ui.gui.Fase1.aluno.GerirAluno;
import pt.isec.pa.apoio_poe.ui.gui.Fase1.docente.GerirDocente;
import pt.isec.pa.apoio_poe.ui.gui.Fase1.proposta.GerirProposta;
import pt.isec.pa.apoio_poe.ui.gui.Fase2.Fase2;
import pt.isec.pa.apoio_poe.ui.gui.Fase2.candidatura.GerirCandidatura;

public class ApoioPoEGUI extends BorderPane {
    ApoioPoEContext fsm;

    public ApoioPoEGUI(ApoioPoEContext fsm) {
        this.fsm = fsm;

        createViews();
        registerHandlers();
    }

    private void createViews() {
        StackPane stackPane = new StackPane(
                new Inicio(fsm),
                new Fase1(fsm),
                new GerirAluno(fsm),
                new GerirDocente(fsm),
                new GerirProposta(fsm),
                new Fase2(fsm),
                new GerirCandidatura(fsm)
        );
        this.setTop(new AppMenu(fsm));
        this.setCenter(stackPane);
        this.setBottom(new Rodape());
    }

    private void registerHandlers() {
        fsm.addPropertyChangeListener(ApoioPoEContext.PROP_FASE, evt -> update());
    }

    private void update() {
        Stage stage = (Stage) this.getScene().getWindow();
        String titulo = "";
        switch(fsm.getState()) {

            case INICIO -> titulo = "Apoio Projeto ou Estágio";
            case FASE1 -> titulo = "Fase 1";
            case FASE1_BLOQUEADA -> titulo = "Fase 1 (Bloqueada)";
            case GESTAO_ALUNOS -> titulo = "Gestão Alunos";
            case GESTAO_DOCENTES -> titulo = "Gestão Docentes";
            case GESTAO_PROPOSTAS -> titulo = "Gestão Propostas";
            case FASE2 -> titulo = "Fase 2";
            case FASE2_BLOQUEADA -> titulo = "Fase 2 (Bloqueada)";
            case GESTAO_CANDIDATURAS -> titulo = "Gestão Candidaturas";
            case FASE3, Fase3MasFase2AbertaState -> titulo = "Fase 3";
            case FASE3_BLOQUEADA -> titulo = "Fase 3 (Bloqueada)";
            case GESTAO_MANUAL_ATRIBUICOES -> titulo = "Gestão Manual Atribuições";
            case Fase3AtribuicaoAutomatica -> titulo = "Gestão Automática Atribuições";
            case FASE4 -> titulo = "Fase 4";
            case GESTAO_MANUAL_ORIENTADORES -> titulo = "Gestão Manual Orientadores";
            case FASE5 -> titulo = "Fase 5";
            default -> titulo = "";
        }
        stage.setTitle(titulo);
    }
}
