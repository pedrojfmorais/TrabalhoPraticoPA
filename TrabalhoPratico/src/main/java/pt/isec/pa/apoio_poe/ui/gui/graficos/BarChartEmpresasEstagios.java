package pt.isec.pa.apoio_poe.ui.gui.graficos;

import javafx.collections.FXCollections;
import javafx.scene.chart.*;
import javafx.scene.layout.BorderPane;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEContext;

import java.util.*;

public class BarChartEmpresasEstagios extends BorderPane {

    ApoioPoEContext fsm;
    BarChart<Number, String> bcEmpresasEstagios;
    CategoryAxis yAxis;

    public BarChartEmpresasEstagios(ApoioPoEContext fsm) {
        this.fsm = fsm;

        createViews();
        registerHandlers();
        update();
    }

    private void createViews(){

        yAxis = new CategoryAxis();
        NumberAxis xAxis = new NumberAxis();
        bcEmpresasEstagios = new BarChart<Number, String>(xAxis, yAxis);
        bcEmpresasEstagios.setTitle("Estágios por Empresa (Top 5)");

        bcEmpresasEstagios.setLegendVisible(false);

        yAxis.setLabel("Empresas");
        xAxis.setLabel("Nº Estágios");

        update();

        this.setCenter(bcEmpresasEstagios);
    }

    private void registerHandlers(){
        fsm.addPropertyChangeListener(ApoioPoEContext.PROP_FASE, evt -> update());
        fsm.addPropertyChangeListener(ApoioPoEContext.PROP_PROPOSTA, evt -> update());
    }

    private void update(){

        bcEmpresasEstagios.getData().clear();

        XYChart.Series series1 = new XYChart.Series();

        HashMap<String, Number> empresas = fsm.top5EmpresasEstagio();

        yAxis.getCategories().clear();
        yAxis.setCategories(FXCollections.observableArrayList(empresas.keySet()));

        for (int i = 0; i < empresas.size(); i++) {
            series1.getData().add(new XYChart.Data(empresas.get(empresas.keySet().toArray()[i]), empresas.keySet().toArray()[i]));
        }

        bcEmpresasEstagios.getData().add(series1);
    }
}
