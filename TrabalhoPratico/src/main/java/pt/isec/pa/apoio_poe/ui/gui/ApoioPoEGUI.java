package pt.isec.pa.apoio_poe.ui.gui;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEContext;
import pt.isec.pa.apoio_poe.ui.gui.fase1.Fase1GUI;
import pt.isec.pa.apoio_poe.ui.gui.fase1.Fase1BloqueadaGUI;
import pt.isec.pa.apoio_poe.ui.gui.fase1.aluno.GerirAlunoGUI;
import pt.isec.pa.apoio_poe.ui.gui.fase1.docente.GerirDocenteGUI;
import pt.isec.pa.apoio_poe.ui.gui.fase1.proposta.GerirPropostaGUI;
import pt.isec.pa.apoio_poe.ui.gui.fase2.Fase2GUI;
import pt.isec.pa.apoio_poe.ui.gui.fase2.Fase2BloqueadaGUI;
import pt.isec.pa.apoio_poe.ui.gui.fase2.candidatura.GerirCandidaturaGUI;
import pt.isec.pa.apoio_poe.ui.gui.fase3.Fase3GUI;
import pt.isec.pa.apoio_poe.ui.gui.fase3.Fase3BloqueadaGUI;
import pt.isec.pa.apoio_poe.ui.gui.fase3.Fase3MasFase2AbertaGUI;
import pt.isec.pa.apoio_poe.ui.gui.fase3.propostaAtribuida.AtribuicaoAutomaticaGUI;
import pt.isec.pa.apoio_poe.ui.gui.fase3.propostaAtribuida.GestaoManualAtribuicoesGUI;
import pt.isec.pa.apoio_poe.ui.gui.fase4.Fase4GUI;
import pt.isec.pa.apoio_poe.ui.gui.fase4.docentesOrientadores.GestaoManualOrientacoesGUI;
import pt.isec.pa.apoio_poe.ui.gui.fase5.Fase5GUI;

public class ApoioPoEGUI extends BorderPane {
    ApoioPoEContext fsm;

    public ApoioPoEGUI(ApoioPoEContext fsm) {
        this.fsm = fsm;

        createViews();
        registerHandlers();
    }

    private void createViews() {
        StackPane stackPane = new StackPane(
                new InicioGUI(fsm),
                new Fase1GUI(fsm),
                new Fase1BloqueadaGUI(fsm),
                new GerirAlunoGUI(fsm),
                new GerirDocenteGUI(fsm),
                new GerirPropostaGUI(fsm),
                new Fase2GUI(fsm),
                new Fase2BloqueadaGUI(fsm),
                new GerirCandidaturaGUI(fsm),
                new Fase3GUI(fsm),
                new Fase3BloqueadaGUI(fsm),
                new Fase3MasFase2AbertaGUI(fsm),
                new GestaoManualAtribuicoesGUI(fsm),
                new AtribuicaoAutomaticaGUI(fsm),
                new Fase4GUI(fsm),
                new GestaoManualOrientacoesGUI(fsm),
                new Fase5GUI(fsm)
        );
        this.setTop(new AppMenuGUI(fsm));
        this.setCenter(stackPane);
        this.setBottom(new RodapeGUI());
    }

    private void registerHandlers() {
        fsm.addPropertyChangeListener(ApoioPoEContext.PROP_FASE, evt -> update());
    }

    private void update() {
        Stage stage = (Stage) this.getScene().getWindow();
        String titulo;
        switch(fsm.getState()) {

            case INICIO -> titulo = "Apoio Projeto ou Estágio";
            case FASE1 -> titulo = "Fase 1 - Configuração";
            case FASE1_BLOQUEADA -> titulo = "Fase 1 - Configuração (Bloqueada)";
            case GESTAO_ALUNOS -> titulo = "Gestão Alunos";
            case GESTAO_DOCENTES -> titulo = "Gestão Docentes";
            case GESTAO_PROPOSTAS -> titulo = "Gestão Propostas";
            case FASE2 -> titulo = "Fase 2 - Opções de Candidatura";
            case FASE2_BLOQUEADA -> titulo = "Fase 2 - Opções de Candidatura (Bloqueada)";
            case GESTAO_CANDIDATURAS -> titulo = "Gestão Candidaturas";
            case FASE3, Fase3MasFase2AbertaState -> titulo = "Fase 3 - Atribuição de propostas";
            case FASE3_BLOQUEADA -> titulo = "Fase 3 - Atribuição de propostas (Bloqueada)";
            case GESTAO_MANUAL_ATRIBUICOES -> titulo = "Gestão Manual de Atribuição de Propostas a Alunos";
            case Fase3AtribuicaoAutomatica -> titulo = "Gestão Automática Atribuições";
            case FASE4 -> titulo = "Fase 4 - Atribuição de orientadores";
            case GESTAO_MANUAL_ORIENTADORES -> titulo = "Gestão Manual de Orientadores de Propostas Atribuídas";
            case FASE5 -> titulo = "Fase 5 - Consulta";
            default -> titulo = "";
        }
        stage.setTitle(titulo);
    }
}
