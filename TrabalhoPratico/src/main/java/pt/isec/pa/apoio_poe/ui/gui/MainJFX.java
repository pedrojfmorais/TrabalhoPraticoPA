package pt.isec.pa.apoio_poe.ui.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEContext;

public class MainJFX extends Application {
    ApoioPoEContext fsm;

    public MainJFX() {
        this.fsm = ApoioPoEContext.getInstance();
    }

    @Override
    public void init() throws Exception {
        super.init();
    }

    @Override
    public void start(Stage stage) {
        configureGUI(stage);
    }

    private void configureGUI(Stage stage){
        Scene scene = new Scene(new ApoioPoEGUI(fsm), 500, 500, Color.BLACK);
        stage.setScene(scene);
        stage.setTitle("Trabalho Prático");
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }
}
