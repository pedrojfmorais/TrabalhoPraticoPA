package pt.isec.pa.apoio_poe;

import javafx.application.Application;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEContext;
import pt.isec.pa.apoio_poe.ui.gui.MainJFX;
import pt.isec.pa.apoio_poe.ui.text.ApoioPoEUI;

public class Main {

    public static void main(String[] args) throws Exception {

        ApoioPoEContext fsm = new ApoioPoEContext();
        ApoioPoEUI ui = new ApoioPoEUI(fsm);

        // ui.start();

        Application.launch(MainJFX.class, args);
    }
}
