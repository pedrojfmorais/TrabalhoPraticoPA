package pt.isec.pa.apoio_poe.ui.gui;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEContext;
import pt.isec.pa.apoio_poe.ui.gui.aluno.GerirAlunos;

public class ApoioPoEGUI extends BorderPane {
    ApoioPoEContext fsm;

    public ApoioPoEGUI(ApoioPoEContext fsm) {
        this.fsm = fsm;

        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        StackPane stackPane = new StackPane(
                new Inicio(fsm), new Fase1(fsm), new GerirAlunos(fsm)
        );
        this.setTop(new AppMenu(fsm));
        this.setCenter(stackPane);
        this.setBottom(new Rodape());
    }

    private void registerHandlers() { }

    private void update() { }
}
