package pt.isec.pa.apoio_poe;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEContext;
import pt.isec.pa.apoio_poe.ui.text.ApoioPoEUI;

public class Main extends Application {

    public static void main(String[] args) {

        ApoioPoEContext fsm = new ApoioPoEContext();
        ApoioPoEUI ui = new ApoioPoEUI(fsm);

        ui.start();

        launch(args);
    }


    //teste com maven
    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new Pane(), 500, 500, Color.BLACK);
        stage.setScene(scene);
        stage.setTitle("Maven");
        stage.show();
    }
}
