package view;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.shape.Rectangle;
import model.GffEntry;
import model.Read;
import presenter.ReadChartViewPresenter;

import java.net.URL;
import java.util.ArrayList;
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

    private IntegerProperty barWidth = new SimpleIntegerProperty(20);
    private DoubleProperty zoomFactor = new SimpleDoubleProperty(1);



    private final int BASIC_WIDTH = 740;


    public void addReads(List<Read> reads){
        choseReadChoiceBox.getItems().addAll(reads);
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

    }


    /**
     * If the addReadButton is clicked, a new Sequence + Genes is drawed if there is something selected in the
     * choseReadChoiceBox.
     * @param actionEvent
     */
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
     * @param gffEntries
     * @param oldUpperBound
     */
    private AnchorPane[] addGenes(List<GffEntry> gffEntries, double oldUpperBound){
        AnchorPane genes = new AnchorPane();
        AnchorPane genesReversed = new AnchorPane();

        genes.prefHeightProperty().bind(barWidth);
        genesReversed.prefHeightProperty().bind(barWidth);

        for(int j = 0; j < gffEntries.size(); j++){

            int start =gffEntries.get(j).getStart();
            int end = gffEntries.get(j).getEnd();
            boolean isReversed = (gffEntries.get(j).getStrand() == '+') ? false : true;


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
                genesReversed.getChildren().add(rectangle);

            }
            else{
                rectangle.setFill(Color.rgb(178,34,34, 0.5));
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
            String tooltipMessage = gffEntries.get(j).getAttributes().get(" Name") + "\n"
                    + "Start: " + start + " End: " + end + "\n"
                    + "Strand: " + gffEntries.get(j).getStrand();
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
        label.prefHeightProperty().bind(barWidth.multiply(3).add(3));
        names.setSpacing(10);
        names.getChildren().add(label);

    }

    private void searchGen(String genName, List<GffEntry> gffEntries){

        //Setting up Data;
        int minStart = Integer.MAX_VALUE;
        int minEnd = Integer.MIN_VALUE;
        final int SPACE_FROM_START_END = (minEnd-minStart)/2;
        List<GffEntry> toDraw = new ArrayList<>();

        for(int i = 0; i < gffEntries.size(); i++){
            String name = gffEntries.get(i).getAttributes().get(" Name");
            int start = gffEntries.get(i).getStart();
            int end = gffEntries.get(i).getEnd();
            if(name.equals(genName.trim())){
                toDraw.add(gffEntries.get(i));
                if(start < minStart){
                    minStart = start;
                }
                if(end > minStart){
                    minEnd = end;
                }
            }
        }

        //Setting up xAxis
        double oldUpperBound = xAxis.getUpperBound();
        double oldLowerBound = xAxis.getLowerBound();

        xAxis.setLowerBound(minStart-SPACE_FROM_START_END);
        xAxis.setUpperBound(minEnd+SPACE_FROM_START_END);

    }
}
