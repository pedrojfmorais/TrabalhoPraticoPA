package pt.isec.pa.apoio_poe;

import pt.isec.pa.apoio_poe.model.data.ApoioPOE;
import pt.isec.pa.apoio_poe.model.data.propostas.Autoproposto;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoeContext;
import pt.isec.pa.apoio_poe.ui.text.ApoioPoeUI;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        ApoioPoeContext fsm = new ApoioPoeContext();
        ApoioPoeUI ui = new ApoioPoeUI(fsm);

        ui.start();

    //TODO: tratar exceções do cloneable (Pessoa, Proposta, Candidatura)

    }
}
