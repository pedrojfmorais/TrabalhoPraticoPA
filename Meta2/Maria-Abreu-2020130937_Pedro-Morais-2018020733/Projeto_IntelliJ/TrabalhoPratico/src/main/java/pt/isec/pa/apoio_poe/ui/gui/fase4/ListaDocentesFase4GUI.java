package pt.isec.pa.apoio_poe.ui.gui.fase4;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import pt.isec.pa.apoio_poe.model.data.pessoas.Docente;
import pt.isec.pa.apoio_poe.model.fsm.ApoioPoEContext;
import pt.isec.pa.apoio_poe.ui.gui.resources.ImageManager;

import java.util.ArrayList;

public class ListaDocentesFase4GUI extends BorderPane {
    ApoioPoEContext fsm;

    Button btnProcurar;

    Label lbMax, lbMin, lbMedia, lbMaxDados, lbMinDados, lbMediaDados;

    VBox vBoxMaxMinMedia;

    TableView<Docente> tDocente;
    TextField tfFiltros;

    public ListaDocentesFase4GUI(ApoioPoEContext fsm){
        this.fsm = fsm;

        createViews();
        registerHandlers();
        update();
    }

    private void createViews(){
        btnProcurar = new Button(null, ImageManager.getImageView("lupa.png",20));
        lbMax = new Label("Máximo orientações por docente: ");
        lbMin = new Label("Minimo orientações por docente: ");
        lbMedia = new Label("Média orientações por docente: ");
        lbMaxDados = new Label();
        lbMinDados = new Label();
        lbMediaDados = new Label();

        tDocente = new TableView<>();
        tfFiltros = new TextField();

        btnProcurar.setPrefSize(30,30);
        btnProcurar.setMinWidth(30);

        lbMax.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        lbMin.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        lbMedia.setFont(Font.font("Arial", FontWeight.BOLD, 13));

        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(10);
        hbox.setPadding(new Insets(10));
        hbox.getChildren().addAll(tfFiltros, btnProcurar);
        this.setTop(hbox);

        vBoxMaxMinMedia = new VBox(
                new HBox(lbMax, lbMaxDados),
                new HBox(lbMin, lbMinDados),
                new HBox(lbMedia, lbMediaDados)
        );

        for (int i = 0; i < vBoxMaxMinMedia.getChildren().size(); i++) {
            HBox hBox = (HBox) vBoxMaxMinMedia.getChildren().get(i);
            hBox.setAlignment(Pos.CENTER);
        }

        vBoxMaxMinMedia.setAlignment(Pos.CENTER);
        vBoxMaxMinMedia.setSpacing(10);
        vBoxMaxMinMedia.setPadding(new Insets(10));

        setTabelaDocente(tDocente);

        this.setPadding(new Insets(20));
        this.setCenter(new VBox(vBoxMaxMinMedia, tDocente));
    }

    private void registerHandlers(){
        fsm.addPropertyChangeListener(ApoioPoEContext.PROP_FASE, evt -> update());
        fsm.addPropertyChangeListener(ApoioPoEContext.PROP_DOCENTE, evt -> update());
        fsm.addPropertyChangeListener(ApoioPoEContext.PROP_PROPOSTA_ATRIBUIDA, evt -> update());

        tfFiltros.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER)
                btnProcurar.fire();
        });

        btnProcurar.setOnAction(actionEvent -> procurarDocente(tfFiltros.getText()));
    }

    private void update(){
        atualizaDadosGerais();
        procurarDocente("");
    }

    private void atualizaDadosGerais(){
        ArrayList<String> dados = fsm.consultarDocentes("");
        if(dados != null) {
            lbMaxDados.setText(dados.get(0));
            lbMinDados.setText(dados.get(1));
            lbMediaDados.setText(dados.get(2));
        }
    }

    private void procurarDocente(String filtro){

        if(filtro.isBlank()){
            tDocente.getItems().clear();
            for(var docente : fsm.getDocentes()) {
                tDocente.getItems().add(docente);
                tfFiltros.setText("");
            }
        } else {
            Docente docente = fsm.getDocente(filtro);

            if(docente!=null) {
                tDocente.getItems().clear();
                tDocente.getItems().add(docente);
                tfFiltros.setText("");
            }
        }
    }

    public void setTabelaDocente(TableView<Docente> tDocente) {
        TableColumn<Docente,String> tcNome = new TableColumn("Nome");
        tcNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        TableColumn<Docente,String> tcEmail = new TableColumn("Email");
        tcEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        TableColumn<Docente,String> tcOrientacoes = new TableColumn("Nº Orientações");
        tcOrientacoes.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(
                String.valueOf(fsm.calculaNumeroOrientacoesDocente(p.getValue().getEmail())))
        );

        tDocente.setPlaceholder(new Label("Ainda não existem Docentes Orientadores"));
        tDocente.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tDocente.getColumns().clear();
        tDocente.getColumns().addAll(tcNome,tcEmail, tcOrientacoes);
    }
}
