package pt.isec.pa.apoio_poe.ui.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEContext;

public class MainJFX extends Application {
    ApoioPoEContext fsm;
//
//    public MainJFX(ApoioPoEContext fsm) {
//        this.fsm = fsm;
//    }
    public MainJFX() {
        this.fsm = new ApoioPoEContext();
    }

    @Override
    public void init() throws Exception {
        super.init();
    }

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new Inicio(fsm), 500, 500, Color.BLACK);
        stage.setScene(scene);
        stage.setTitle("Maven");
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }
}
