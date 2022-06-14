package pt.isec.pa.apoio_poe.ui.gui.graficos;

import javafx.collections.FXCollections;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEContext;

import java.util.HashMap;

public class BarChartDocentesOrientacoesGUI extends BorderPane {

    ApoioPoEContext fsm;
    BarChart<Number, String> bcDocentesOrientacoes;
    CategoryAxis yAxis;

    public BarChartDocentesOrientacoesGUI(ApoioPoEContext fsm) {
        this.fsm = fsm;

        createViews();
        registerHandlers();
        update();
    }

    private void createViews(){

        yAxis = new CategoryAxis();
        NumberAxis xAxis = new NumberAxis();
        bcDocentesOrientacoes = new BarChart<Number, String>(xAxis, yAxis);
        bcDocentesOrientacoes.setTitle("Orientações por Docente (Top 5)");

        bcDocentesOrientacoes.setLegendVisible(false);

        yAxis.setLabel("Docente");
        xAxis.setLabel("Nº Orientações");

        update();

        this.setCenter(bcDocentesOrientacoes);
    }

    private void registerHandlers(){
        fsm.addPropertyChangeListener(ApoioPoEContext.PROP_FASE, evt -> update());
        fsm.addPropertyChangeListener(ApoioPoEContext.PROP_PROPOSTA_ATRIBUIDA, evt -> update());
    }

    private void update(){

        bcDocentesOrientacoes.getData().clear();

        XYChart.Series series1 = new XYChart.Series();

        HashMap<String, Number> docentes = fsm.top5DocentesOrientacoes();

        yAxis.getCategories().clear();
        yAxis.setCategories(FXCollections.observableArrayList(docentes.keySet()));

        for (int i = 0; i < docentes.size(); i++) {
            series1.getData().add(new XYChart.Data(docentes.get(docentes.keySet().toArray()[i]), docentes.keySet().toArray()[i]));
        }

        bcDocentesOrientacoes.getData().add(series1);
    }
}
