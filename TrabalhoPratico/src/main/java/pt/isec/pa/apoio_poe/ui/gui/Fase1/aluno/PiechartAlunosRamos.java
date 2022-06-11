package pt.isec.pa.apoio_poe.ui.gui.Fase1.aluno;

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

public class PiechartAlunosRamos extends BorderPane {

    ApoioPoEContext fsm;

    PieChart pcAlunosRamos;

    public PiechartAlunosRamos(ApoioPoEContext fsm) {
        this.fsm = fsm;

        createViews();
        registerHandlers();
        update();
    }

    private void createViews(){

        pcAlunosRamos = new PieChart();

        pcAlunosRamos.setTitle("Alunos por Ramo");

        pcAlunosRamos.setClockwise(true);
        pcAlunosRamos.setLabelsVisible(true);
        pcAlunosRamos.setLegendVisible(true);

        ArrayList<PieChart.Data> dados = new ArrayList<>();

        for(var ramo : Aluno.ramos)
            dados.add(new PieChart.Data(ramo, fsm.nAlunosPorRamo(ramo)));

        pcAlunosRamos.setData(FXCollections.observableArrayList(dados));

        this.setCenter(pcAlunosRamos);
    }

    private void registerHandlers(){
        fsm.addPropertyChangeListener(ApoioPoEContext.PROP_FASE, evt -> update());
        fsm.addPropertyChangeListener(ApoioPoEContext.PROP_ALUNO, evt -> update());

        pcAlunosRamos.setOnMouseEntered(e -> {
            for (var data : pcAlunosRamos.getData()) {
                data.getNode().setOnMouseEntered(ev -> {

                    Label caption = new Label("");
                    caption.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, Insets.EMPTY)));

                    this.setTop(caption);

                    caption.setTranslateX(ev.getSceneX() - caption.getLayoutX());
                    caption.setTranslateY(ev.getSceneY() - caption.getLayoutY());
                    caption.setText(Math.round(data.getPieValue()) + " alunos");
                });
            }
        });
    }

    private void update(){

        ArrayList<PieChart.Data> dados = new ArrayList<>();

        for(var ramo : Aluno.ramos)
            dados.add(new PieChart.Data(ramo, fsm.nAlunosPorRamo(ramo)));

        pcAlunosRamos.setData(FXCollections.observableArrayList(dados));
    }
}
