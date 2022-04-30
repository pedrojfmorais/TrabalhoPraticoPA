package pt.isec.pa.apoio_poe;

import pt.isec.pa.apoio_poe.model.fsm.ApoioPoeContext;
import pt.isec.pa.apoio_poe.ui.text.ApoioPoEUI;

public class Main {

    public static void main(String[] args) {

        ApoioPoeContext fsm = new ApoioPoeContext();
        ApoioPoEUI ui = new ApoioPoEUI(fsm);

        ui.start();

    }
}
