package pt.isec.pa.apoio_poe.ui.gui;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEContext;
import pt.isec.pa.apoio_poe.ui.gui.fase1.Fase1;
import pt.isec.pa.apoio_poe.ui.gui.fase1.Fase1Bloqueada;
import pt.isec.pa.apoio_poe.ui.gui.fase1.aluno.GerirAluno;
import pt.isec.pa.apoio_poe.ui.gui.fase1.docente.GerirDocente;
import pt.isec.pa.apoio_poe.ui.gui.fase1.proposta.GerirProposta;
import pt.isec.pa.apoio_poe.ui.gui.fase2.Fase2;
import pt.isec.pa.apoio_poe.ui.gui.fase2.Fase2Bloqueada;
import pt.isec.pa.apoio_poe.ui.gui.fase2.candidatura.GerirCandidatura;
import pt.isec.pa.apoio_poe.ui.gui.fase3.Fase3;
import pt.isec.pa.apoio_poe.ui.gui.fase3.Fase3MasFase2Aberta;
import pt.isec.pa.apoio_poe.ui.gui.fase3.propostaAtribuida.AtribuicaoAutomatica;
import pt.isec.pa.apoio_poe.ui.gui.fase3.propostaAtribuida.GestaoManualAtribuicoes;
import pt.isec.pa.apoio_poe.ui.gui.fase4.Fase4;
import pt.isec.pa.apoio_poe.ui.gui.fase4.docentesOrientadores.GestaoManualOrientacoes;

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
                new Fase1Bloqueada(fsm),
                new GerirAluno(fsm),
                new GerirDocente(fsm),
                new GerirProposta(fsm),
                new Fase2(fsm),
                new Fase2Bloqueada(fsm),
                new GerirCandidatura(fsm),
                new Fase3(fsm),
                new Fase3MasFase2Aberta(fsm),
                new GestaoManualAtribuicoes(fsm),
                new AtribuicaoAutomatica(fsm),
                new Fase4(fsm),
                new GestaoManualOrientacoes(fsm)
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
