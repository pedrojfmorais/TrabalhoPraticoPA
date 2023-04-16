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
import pt.isec.pa.apoio_poe.model.data.pessoas.alunos.Aluno;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEContext;

import java.util.ArrayList;

public class PiechartPropostasRamosGUI extends BorderPane {

    ApoioPoEContext fsm;

    PieChart pcPropostasRamos;
    Label caption;

    public PiechartPropostasRamosGUI(ApoioPoEContext fsm) {
        this.fsm = fsm;

        createViews();
        registerHandlers();
        update();
    }

    private void createViews(){

        caption = new Label("");

        caption.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, Insets.EMPTY)));


        pcPropostasRamos = new PieChart();

        pcPropostasRamos.setTitle("Propostas por Ramo");

        pcPropostasRamos.setClockwise(true);
        pcPropostasRamos.setLabelsVisible(true);
        pcPropostasRamos.setLegendVisible(true);

        ArrayList<PieChart.Data> dados = new ArrayList<>();

        for(var ramo : Aluno.ramos)
            dados.add(new PieChart.Data(ramo, fsm.propostasPorRamo(ramo)));

        pcPropostasRamos.setData(FXCollections.observableArrayList(dados));

        this.setCenter(pcPropostasRamos);

        this.setTop(caption);
    }

    private void registerHandlers(){
        fsm.addPropertyChangeListener(ApoioPoEContext.PROP_PROPOSTA, evt -> update());

        pcPropostasRamos.setOnMouseEntered(e -> {
            for (var data : pcPropostasRamos.getData()) {
                data.getNode().setOnMouseEntered(ev -> {
                    caption.setTranslateX(ev.getSceneX() - caption.getLayoutX());
                    caption.setTranslateY(ev.getSceneY() - caption.getLayoutY());
                    caption.setText(Math.round(data.getPieValue()) + " propostas");
                });
            }
        });

        this.setOnMouseExited(event -> caption.setText(""));
    }

    private void update(){

        ArrayList<PieChart.Data> dados = new ArrayList<>();

        for(var ramo : Aluno.ramos)
            dados.add(new PieChart.Data(ramo, fsm.propostasPorRamo(ramo)));

        pcPropostasRamos.setData(FXCollections.observableArrayList(dados));
    }
}
