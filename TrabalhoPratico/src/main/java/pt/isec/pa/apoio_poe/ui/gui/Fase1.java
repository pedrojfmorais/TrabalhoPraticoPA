package pt.isec.pa.apoio_poe.ui.gui;

import javafx.scene.layout.*;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEContext;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEState;


public class Fase1 extends BorderPane {

    ApoioPoEContext fsm;

    public Fase1(ApoioPoEContext fsm) {

        this.fsm = fsm;

        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {

    }

    private void registerHandlers() {

    }


    private void update() {
        this.setVisible(fsm != null && fsm.getState() == ApoioPoEState.FASE1);
    }

}
