package view;

import javafx.beans.property.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import presenter.ReadChartViewPresenter;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class ReadChartView implements Initializable {


    private ReadChartViewPresenter readChartViewPresenter;



    @FXML
    private VBox sequences;

    @FXML
    private VBox names;

    @FXML
    private NumberAxis xAxis;

    @FXML
    private Button addReadButton;

    @FXML
    private ChoiceBox choseReadChoiceBox;

    @FXML
    private Spinner<Integer> barWidthSpinner;

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

    private IntegerProperty barWidth = new SimpleIntegerProperty(10);
    private DoubleProperty zoomFactor = new SimpleDoubleProperty(1);
    private ObjectProperty<Paint> markColor = new SimpleObjectProperty<>();

    private String searchedGene = "";



    private final int BASIC_WIDTH = 740;





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


    }
    //Setters/Getters-----------------------------------------------------------------------------------------------


    public void setReadChartViewPresenter(ReadChartViewPresenter readChartViewPresenter){
        this.readChartViewPresenter = readChartViewPresenter;
    }

    //Listeners------------------------------------------------------------------------------------------------------

    @FXML
    private void onSearchGeneButtonClicked(){
        String geneName = this.searchGeneTextField.getText();

        //Resize the Rectangle Paints, to standard
        if(!geneName.equals(searchedGene)){
            markColor.set(Color.WHEAT);
        }
        searchedGene = geneName;
        markColor.set(Color.rgb(255, 215 , 0, 0.5));

    }

    @FXML
    private void onZoomGeneButtonClicked(){
        System.out.println("Hehe");
    }

//Painting-----------------------------------------------------------------------------------------------------------
            /*

    public void onAddReadButtonClicked(ActionEvent actionEvent) {

        if(choseReadChoiceBox.getSelectionModel().getSelectedItem() == null){
            return;
        }else{
            Read read = (Read)choseReadChoiceBox.getSelectionModel().getSelectedItem();

            int length = read.getSequence().length();
            double oldUpperBound = xAxis.getUpperBound();
            List<GffEntry> gffEntries = read.getGFFEntries();

            VBox readBox = new VBox();

            //Has do be done like this, because the getDisplayedValue can only be calculated when the xAxis is drawn...
            if(xAxisSetHigherBoundIfNeeded(length)){
                readBox.getChildren().add(addGenes(gffEntries, oldUpperBound)[0]);
                readBox.getChildren().add(addSequenceLength(read.getId(), length, oldUpperBound));
                readBox.getChildren().add(addGenes(gffEntries, oldUpperBound)[1]);
            }
            else{
                readBox.getChildren().add(addGenes(gffEntries, -1)[0]);
                readBox.getChildren().add(addSequenceLength(read.getId(), length, -1));
                readBox.getChildren().add(addGenes(gffEntries, -1)[1]);
            }

            addName(read.getTaxonomicId() + "");
            sequences.getChildren().add(readBox);
            System.out.println(readChartViewPresenter);
        }
    }
        */

    /**
     * Draws every Read given by the Presenter
     * @param reads Liste von Sequenzdaten
     */
    public void drawReads(List<ReadChartViewPresenter.SequenceData> reads){

        //choseReadChoiceBox.getItems().addAll(reads);
        double oldUpperBound = xAxis.getUpperBound();
        int maxLength = reads.get(0).length;

        xAxis.setUpperBound((maxLength + 1000)/1000 * 1000);
        xAxis.setTickUnit(1000);


        for (ReadChartViewPresenter.SequenceData read : reads) {
            int length = read.length;
            String id = read.seqId;
            String taxId = read.taxId + "";
            List<ReadChartViewPresenter.GeneData> geneData = read.geneData;

            VBox readBox = new VBox();

            readBox.getChildren().add(addGenes(geneData, oldUpperBound)[0]);
            readBox.getChildren().add(addSequenceLength(id, length, oldUpperBound));
            readBox.getChildren().add(addGenes(geneData, oldUpperBound)[1]);


            addName(taxId);
            sequences.getChildren().add(readBox);

        }
    }


    /**
     * Add the sequenceLengths to the plot
     * @param length
     * @param oldUpperBound
     */
    private Rectangle addSequenceLength(String id, int length, double oldUpperBound){
        Rectangle newSequence = new Rectangle();
        newSequence.heightProperty().bind(barWidth);

        //If the xAxis wanna get repaint at the same time u have to calculate it different
        if(oldUpperBound == -1)
            newSequence.setWidth(xAxis.getDisplayPosition(length));
        else{
            newSequence.setWidth(xAxis.getDisplayPosition(length* (oldUpperBound/xAxis.getUpperBound())));
        }

        newSequence.setFill(Color.GRAY);
        //sequences.getChildren().add(newSequence);
        //Right resizing
        xAxis.widthProperty().addListener((obs, oldVal, newVal) -> {

            newSequence.setWidth(newSequence.getWidth()* (newVal.doubleValue()/oldVal.doubleValue()));
        });

        xAxis.upperBoundProperty().addListener((obs, oldVal, newVal) -> {
            newSequence.setWidth((oldVal.doubleValue()/newVal.doubleValue()) * newSequence.getWidth());
        });

        //Tooltip
        Tooltip t = new Tooltip("Sequence-Id: " + id + "\n" + "Length: " + length);
        Tooltip.install(newSequence, t);

        return newSequence;
    }

    /**
     * Add all Genes to the plot
     * @param geneData Gendaten
     * @param oldUpperBound alter xAxis bound zur Platzierung
     */
    private AnchorPane[] addGenes(List<ReadChartViewPresenter.GeneData> geneData, double oldUpperBound){
        AnchorPane genes = new AnchorPane();
        AnchorPane genesReversed = new AnchorPane();


        genes.prefHeightProperty().bind(barWidth);
        genes.minHeightProperty().bind(barWidth);
        genesReversed.prefHeightProperty().bind(barWidth);
        genesReversed.minHeightProperty().bind(barWidth);

        for (ReadChartViewPresenter.GeneData aGeneData : geneData) {

            String name = "" + aGeneData.name;
            int start = aGeneData.start;
            int end = aGeneData.end;
            boolean isReversed = aGeneData.isReversed;

            System.out.println(name);

            Rectangle rectangle = new Rectangle();
            if (oldUpperBound == -1) {
                rectangle.setWidth(xAxis.getDisplayPosition(end) - xAxis.getDisplayPosition(start));
                rectangle.setX(xAxis.getDisplayPosition(start));


            } else {
                rectangle.setWidth(xAxis.getDisplayPosition(end * (oldUpperBound / xAxis.getUpperBound())) - xAxis.getDisplayPosition(start * (oldUpperBound / xAxis.getUpperBound())));
                rectangle.setX(xAxis.getDisplayPosition(start * (oldUpperBound / xAxis.getUpperBound())));
            }


            //Genes with start reds the get blue
            if (isReversed) {
                rectangle.setFill(Color.rgb(34, 34, 178, 0.5));
                markColor.addListener((observable, oldValue, newValue) -> {
                    System.out.println(name);
                    System.out.println(searchedGene);
                    if (!name.equals(searchedGene)) {
                        rectangle.setFill(Color.rgb(34, 34, 178, 0.5));
                    } else {
                        rectangle.setFill(newValue);
                    }
                });
                genesReversed.getChildren().add(rectangle);

            } else {
                rectangle.setFill(Color.rgb(178, 34, 34, 0.5));
                markColor.addListener((observable, oldValue, newValue) -> {
                    if (!name.equals(searchedGene)) {
                        rectangle.setFill(Color.rgb(178, 34, 34, 0.5));
                    } else {
                        rectangle.setFill(newValue);
                    }
                });
                genes.getChildren().add(rectangle);
            }


            rectangle.heightProperty().bind(barWidth);
            rectangle.setStrokeWidth(0.5);
            rectangle.setStroke(Color.BLACK);

            //Right resizing
            xAxis.widthProperty().addListener((obs, oldVal, newVal) -> {
                rectangle.setWidth(rectangle.getWidth() * (newVal.doubleValue() / oldVal.doubleValue()));
                rectangle.setX(rectangle.getX() * (newVal.doubleValue() / oldVal.doubleValue()));

            });

            xAxis.upperBoundProperty().addListener((obs, oldVal, newVal) -> {
                rectangle.setWidth(rectangle.getWidth() * (oldVal.doubleValue() / newVal.doubleValue()));
                rectangle.setX(rectangle.getX() * (oldVal.doubleValue() / newVal.doubleValue()));

            });

            //Add tooltip
            String tooltipMessage = aGeneData.name + "\n"
                    + "Start: " + start + " End: " + end + "\n"
                    + "Strand: " + ((aGeneData.isReversed) ? "+" : "-");
            Tooltip t = new Tooltip(tooltipMessage);
            Tooltip.install(rectangle, t);


            //genes.getChildren().add(rectangle);

        }

        //sequences.getChildren().add(pane);
        AnchorPane[] allGenes = new AnchorPane[2];
        allGenes[0] = genes;
        allGenes[1] = genesReversed;
        return allGenes;
    }

    /**
     * Add the names to the Plot TODO: TAX TREE INTEGRATION
     * @param name
     */
    private void addName(String name){
        //TODO Transform them to names add listener to spinner
        Label label = new Label(name);
        label.prefHeightProperty().bind(barWidth.multiply(3));
        names.getChildren().add(label);

    }

}
