package view;

import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import presenter.ReadChartViewPresenter;

import java.net.URL;
import java.util.List;
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
    public Button addReadButton;

    @FXML
    public ChoiceBox choseReadChoiceBox;

    @FXML
    public Spinner<Integer> barWidthSpinner;

    @FXML Slider zoomSlider;

    @FXML
    AnchorPane readChartPane;

    @FXML
    ScrollPane scrollPane;

    @FXML
    ToolBar toolBar;

    @FXML
    Button searchGeneButton;

    @FXML
    TextField searchGeneTextField;

    private IntegerProperty barWidth = new SimpleIntegerProperty(10);
    private DoubleProperty zoomFactor = new SimpleDoubleProperty(1);
    private ObjectProperty<Paint> markColor = new SimpleObjectProperty<>();

    private String searchedGene = "";



    private final int BASIC_WIDTH = 740;


    public void drawReads(List<ReadChartViewPresenter.SequenceData> reads){

        //choseReadChoiceBox.getItems().addAll(reads);
        double oldUpperBound = xAxis.getUpperBound();
        int maxLength = reads.get(0).length;

        xAxis.setUpperBound((maxLength + 1000)/1000 * 1000);
        xAxis.setTickUnit(1000);


        for(int i = 0; i < reads.size(); i++){
            int length = reads.get(i).length;
            String id = reads.get(i).seqId;
            String taxId = reads.get(i).taxId + "";
            List<ReadChartViewPresenter.GeneData> geneData = reads.get(i).geneData;

            VBox readBox = new VBox();

            readBox.getChildren().add(addGenes(geneData, oldUpperBound)[0]);
            readBox.getChildren().add(addSequenceLength(id, length, oldUpperBound));
            readBox.getChildren().add(addGenes(geneData, oldUpperBound)[1]);


            addName(taxId);
            sequences.getChildren().add(readBox);

        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Spiner is initialized
        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(5, 200);
        barWidthSpinner.setValueFactory(valueFactory);
        barWidthSpinner.getValueFactory().setValue((int)barWidth.get());
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


    /**
     * If the addReadButton is clicked, a new Sequence + Genes is drawed if there is something selected in the
     * choseReadChoiceBox.
     * @param actionEvent
     */
    public void onAddReadButtonClicked(ActionEvent actionEvent) {
        /*
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
    */



    }

    public void setReadChartViewPresenter(ReadChartViewPresenter readChartViewPresenter){
        this.readChartViewPresenter = readChartViewPresenter;
    }

    /**
     * If you add a Sequenze longer then the xAxis, the xAxis is getting bigger
     * @param length
     * @return
     */
    private boolean xAxisSetHigherBoundIfNeeded(int length){
        boolean wasNeeded = false;
        if(xAxis.getUpperBound() < length){
            wasNeeded = true;
            xAxis.setUpperBound((length + 1000)/1000 * 1000);
            xAxis.setTickUnit(1000);
        }

        return wasNeeded;
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
     * @param geneData
     * @param oldUpperBound
     */
    private AnchorPane[] addGenes(List<ReadChartViewPresenter.GeneData> geneData, double oldUpperBound){
        AnchorPane genes = new AnchorPane();
        AnchorPane genesReversed = new AnchorPane();


        genes.prefHeightProperty().bind(barWidth);
        genes.minHeightProperty().bind(barWidth);
        genesReversed.prefHeightProperty().bind(barWidth);
        genesReversed.minHeightProperty().bind(barWidth);

        for(int j = 0; j < geneData.size(); j++){

            String name = "" + geneData.get(j).name;
            int start =geneData.get(j).start;
            int end = geneData.get(j).end;
            boolean isReversed = geneData.get(j).isReversed;

            System.out.println(name);

            Rectangle rectangle = new Rectangle();
            if(oldUpperBound == -1){
                rectangle.setWidth(xAxis.getDisplayPosition(end)-xAxis.getDisplayPosition(start));
                rectangle.setX(xAxis.getDisplayPosition(start));


            }
            else{
                rectangle.setWidth(xAxis.getDisplayPosition(end * (oldUpperBound/xAxis.getUpperBound()))-xAxis.getDisplayPosition(start * (oldUpperBound/xAxis.getUpperBound())));
                rectangle.setX(xAxis.getDisplayPosition(start * (oldUpperBound/xAxis.getUpperBound())));
            }

            LinearGradient lg1;

            //Genes with start reds the get blue
            if(isReversed){
                rectangle.setFill(Color.rgb(34,34,178, 0.5));
                markColor.addListener((observable, oldValue, newValue) -> {
                    System.out.println(name);
                    System.out.println(searchedGene);
                    if(!name.equals(searchedGene)){
                        rectangle.setFill(Color.rgb(34,34,178, 0.5));
                    }
                    else{
                        rectangle.setFill(newValue);
                    }
                });
                genesReversed.getChildren().add(rectangle);

            }
            else{
                rectangle.setFill(Color.rgb(178,34,34, 0.5));
                markColor.addListener((observable, oldValue, newValue) -> {
                    if(!name.equals(searchedGene)){
                        rectangle.setFill(Color.rgb(178,34,34, 0.5));
                    }
                    else{
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
                rectangle.setWidth(rectangle.getWidth()* (newVal.doubleValue()/oldVal.doubleValue()));
                rectangle.setX(rectangle.getX() * (newVal.doubleValue()/oldVal.doubleValue()) );

            });

            xAxis.upperBoundProperty().addListener((obs, oldVal, newVal) -> {
                rectangle.setWidth(rectangle.getWidth()* (oldVal.doubleValue()/newVal.doubleValue()));
                rectangle.setX(rectangle.getX() * (oldVal.doubleValue()/newVal.doubleValue()) );

            });

            //Add tooltip
            String tooltipMessage = geneData.get(j).name + "\n"
                    + "Start: " + start + " End: " + end + "\n"
                    + "Strand: " + ((geneData.get(j).isReversed)?"+" : "-");
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
     * Add the names to the Plot
     * @param name
     */
    private void addName(String name){
        //TODO Transform them to names add listener to spinner
        Label label = new Label(name);
        label.prefHeightProperty().bind(barWidth.multiply(3));
        names.getChildren().add(label);

    }

    @FXML
    private void onSearchGeneButtonClicked(){
        String geneName = this.searchGeneTextField.getText();
        if(!geneName.equals(searchedGene)){
            markColor.set(Color.WHEAT);
        }
        searchedGene = geneName;
        markColor.set(Color.rgb(255, 215 , 0, 0.5));

    }
}
