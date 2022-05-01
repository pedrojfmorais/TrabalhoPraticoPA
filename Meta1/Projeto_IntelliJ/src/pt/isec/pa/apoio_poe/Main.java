package pt.isec.pa.apoio_poe;

import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEContext;
import pt.isec.pa.apoio_poe.ui.text.ApoioPoEUI;

public class Main {

    public static void main(String[] args) {

        ApoioPoEContext fsm = new ApoioPoEContext();
        ApoioPoEUI ui = new ApoioPoEUI(fsm);

        ui.start();

    }
}
