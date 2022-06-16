package pt.isec.pa.apoio_poe.ui.gui;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEContext;

public class ComumFasesGUI {
    public static void alertaAvancarFase(Button btnAvancar, ApoioPoEContext fsm) {
        btnAvancar.setOnAction(event -> {
            Alert alert = new Alert(
                    Alert.AlertType.CONFIRMATION,
                    "",
                    ButtonType.YES, ButtonType.NO
            );
            alert.setTitle("Avançar Fase");
            alert.setHeaderText("Pretende bloquear a fase?");

            alert.showAndWait().ifPresent(response -> {
                switch (response.getButtonData()){
                    case YES -> {
                        boolean result = fsm.avancarFase(true);
                        if(!result) {
                            Alert errorMessage = new Alert(
                                    Alert.AlertType.ERROR,
                                    "",
                                    ButtonType.OK
                            );

                            errorMessage.setTitle("Erro Bloquear Fase");

                            switch (fsm.getState()){
                                case FASE1 -> errorMessage.setHeaderText("Não existem propostas sufecientes para cada Ramo!");
                                case FASE2 -> errorMessage.setHeaderText("Fase 1 encontra-se aberta!");
                                case FASE3 -> errorMessage.setHeaderText("Existem alunos com candidatura sem Proposta Atribuída!");
                            }
                            errorMessage.showAndWait();
                        }

                    }
                    case NO -> fsm.avancarFase(false);
                }
            });
        });
    }
}
