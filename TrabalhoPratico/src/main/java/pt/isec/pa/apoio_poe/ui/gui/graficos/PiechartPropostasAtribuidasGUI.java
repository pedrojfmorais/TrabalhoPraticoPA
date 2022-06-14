package pt.isec.pa.apoio_poe.ui.gui.graficos;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEContext;

import java.util.ArrayList;
import java.util.List;

public class PiechartPropostasAtribuidasGUI extends BorderPane {

    ApoioPoEContext fsm;

    PieChart pcPropostasRamos;

    final static ArrayList<String> nomePie = new ArrayList<>(List.of("Atribuídas", "Não Atribuídas"));
    ArrayList<Integer> dadosPieChart;

    public PiechartPropostasAtribuidasGUI(ApoioPoEContext fsm) {
        this.fsm = fsm;

        createViews();
        registerHandlers();
        update();
    }

    private void createViews(){

        pcPropostasRamos = new PieChart();

        pcPropostasRamos.setTitle("Propostas Atribuidas");

        pcPropostasRamos.setClockwise(true);
        pcPropostasRamos.setLabelsVisible(true);
        pcPropostasRamos.setLegendVisible(true);

        pcPropostasRamos.setLabelsVisible(false);

        update();

        this.setCenter(pcPropostasRamos);
    }

    private void registerHandlers(){
        fsm.addPropertyChangeListener(ApoioPoEContext.PROP_FASE, evt -> update());
        fsm.addPropertyChangeListener(ApoioPoEContext.PROP_PROPOSTA_ATRIBUIDA, evt -> update());

        pcPropostasRamos.setOnMouseEntered(e -> {

            for (int i = 0; i < pcPropostasRamos.getData().size(); i++) {
                int finalI = i;
                pcPropostasRamos.getData().get(i).getNode().setOnMouseEntered(ev -> {

                    Label caption = new Label("");
                    caption.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, Insets.EMPTY)));

                    this.setTop(caption);

                    caption.setTranslateX(ev.getSceneX() - caption.getLayoutX());
                    caption.setTranslateY(ev.getSceneY() - caption.getLayoutY());
                    caption.setText(Math.round(dadosPieChart.get(finalI)) + " " + nomePie.get(finalI) + " ("
                            + Math.round(Double.valueOf(dadosPieChart.get(finalI))/Double.valueOf(dadosPieChart.get(2))*100) +"%)");
                });
            }
        });
    }

    private void update(){

        ArrayList<PieChart.Data> dados = new ArrayList<>();
        dadosPieChart = fsm.propostasAtribuidas_NaoAtribuidas_Total();

        for (int i = 0; i < dadosPieChart.size()-1; i++) {
            dados.add(new PieChart.Data(nomePie.get(i), dadosPieChart.get(i)));
        }

        pcPropostasRamos.setData(FXCollections.observableArrayList(dados));
    }
}
