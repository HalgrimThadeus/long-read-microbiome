package view;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import presenter.ReadChartViewPresenter;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class ReadChartView implements Initializable {


    private ReadChartViewPresenter readChartViewPresenter;



    @FXML
    public VBox sequences;

    @FXML
    public VBox names;

    @FXML
    public NumberAxis xAxis;

    @FXML
    private Button addReadButton;

    @FXML
    public ChoiceBox filterChooseBox;

    @FXML
    public Spinner<Integer> barWidthSpinner;

    @FXML
    private Slider zoomSlider;

    @FXML
    private AnchorPane readChartPane;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private ToolBar toolBar;

    @FXML
    private Button searchGeneButton;

    @FXML
    private TextField searchGeneTextField;

    public IntegerProperty barWidth = new SimpleIntegerProperty(5);
    private DoubleProperty zoomFactor = new SimpleDoubleProperty(1);

    private final int BASIC_WIDTH = 740;

    public ObservableList<AnchorPane> genPane = FXCollections.observableArrayList();
    public ObservableList<Rectangle> reads = FXCollections.observableArrayList();
    public ObservableList<AnchorPane> reversedGenPane = FXCollections.observableArrayList();
    public ObservableList<Label> name = FXCollections.observableArrayList();





    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Spiner is initialized
        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(5, 200);
        barWidthSpinner.setValueFactory(valueFactory);
        barWidthSpinner.getValueFactory().setValue(barWidth.get());
        barWidthSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            barWidth.set(newValue);
        });

        //Bindings
        toolBar.prefWidthProperty().bind(scrollPane.widthProperty().subtract(10));
        zoomFactor.bind(zoomSlider.valueProperty());
        readChartPane.prefHeightProperty().bind(scrollPane.heightProperty());
        readChartPane.prefWidthProperty().bind(zoomFactor.multiply(BASIC_WIDTH));
        readChartPane.maxWidthProperty().bind(zoomFactor.multiply(BASIC_WIDTH));
        readChartPane.minWidthProperty().bind(zoomFactor.multiply(BASIC_WIDTH));


        sequences.setSpacing(10);
        names.setSpacing(10);
        onAction();
    }

    private void onAction(){
        reads.addListener((ListChangeListener<? super Rectangle>) change ->{
            while(change.next()) {

                if(change.wasAdded()) {
                    VBox vBox = new VBox();

                    vBox.getChildren().add(genPane.get(change.getFrom()));
                    vBox.getChildren().add(reads.get(change.getFrom()));
                    vBox.getChildren().add(reversedGenPane.get(change.getFrom()));

                    sequences.getChildren().add(vBox);
                    names.getChildren().add(name.get(change.getFrom()));
                }
            }
        });
    }
    //Setters/Getters-----------------------------------------------------------------------------------------------


    public void setReadChartViewPresenter(ReadChartViewPresenter readChartViewPresenter){
        this.readChartViewPresenter = readChartViewPresenter;
    }

    //Listeners------------------------------------------------------------------------------------------------------

    @FXML
    private void onSearchGeneButtonClicked(){
        String geneName = ""+ this.searchGeneTextField.getText();
        System.out.println(geneName);
        readChartViewPresenter.markSearchedGenes(geneName);
    }

    @FXML
    private void onZoomGeneButtonClicked() throws IOException {
        String geneName = ""+ this.searchGeneTextField.getText();
        readChartViewPresenter.searchForGenByName(geneName);
    }

//Painting-----------------------------------------------------------------------------------------------------------




    /*
    private static int AREA_WIDTH = 500;

    public void drawZoomedReads(List<ReadChartViewPresenter.SequenceData> reads, String genName){
        //TODO Make this different!
        if(reads == null && reads.get(0) == null){
            return;
        }

        double oldUpperBound = xAxis.getUpperBound();

        xAxis.setLowerBound(0);
        xAxis.setUpperBound(2*AREA_WIDTH);


        for (ReadChartViewPresenter.SequenceData read : reads) {

            int length = 2*AREA_WIDTH;
            String id = read.seqId;
            String taxId = read.taxId + "";

            List<ReadChartViewPresenter.GeneData> sortedGens = read.sortGeneData(read.geneData);
            List<ReadChartViewPresenter.GeneData> genesToDraw = new ArrayList<>();

            //If there are more then one, always the first is taken
            int startGen = 0;
            for(ReadChartViewPresenter.GeneData gens : sortedGens){
                if(genName.contains(gens.name)){
                    startGen = gens.start;
                }
            }
            //Area width, where genes are painted
            int lowerBound = startGen-AREA_WIDTH;
            int upperBound = startGen+AREA_WIDTH;

            //Prepare them for paint
            for(ReadChartViewPresenter.GeneData gens : sortedGens){
                if(gens.start >= lowerBound && gens.start <= upperBound){
                    gens.start = gens.start - startGen + AREA_WIDTH;
                    if(gens.end > upperBound) {
                        gens.end = 2*AREA_WIDTH;
                    }else{
                        gens.end = gens.end-startGen + AREA_WIDTH;
                    }
                    System.out.println(gens.start);
                    System.out.println(gens.end);
                    genesToDraw.add(gens);
                }
            }

            VBox readBox = new VBox();

            readBox.getChildren().add(addGenes(genesToDraw, oldUpperBound)[0]);
            readBox.getChildren().add(addSequenceLength(id, length, oldUpperBound));
            readBox.getChildren().add(addGenes(genesToDraw, oldUpperBound)[1]);


            addName(taxId);
            sequences.getChildren().add(readBox);

            xAxis.setPrefWidth(xAxis.getWidth());
            xAxis.setLowerBound(-1000);
            xAxis.setUpperBound(1000);




        }
    }
    */



}
