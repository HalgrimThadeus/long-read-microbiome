package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.GffEntry;
import model.Read;
import model.Sample;
import model.io.SampleReader;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class ReadChartView implements Initializable {

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

    private final int BAR_WIDTH = 20;

    private List<Read> filteredReads;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Sample sample = SampleReader.read("res/ehec/ehec.f1000000-ex.fasta", "res/ehec/ehec.f1000000-all.gff", "res/ehec/ehec.f1000000-readname2taxonid.txt");

            choseReadChoiceBox.getItems().addAll(sample.getReads());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(5, 60);
        barWidthSpinner.setValueFactory(valueFactory);
        barWidthSpinner.getValueFactory().setValue(BAR_WIDTH);

    }


    /**
     * If the addReadButton is clicked, a new Sequence + Genes is drawed if there is something selected in the
     * choseReadChoiceBox.
     * @param actionEvent
     */
    public void addReadButtonClicked(ActionEvent actionEvent) {
        if(choseReadChoiceBox.getSelectionModel().getSelectedItem() == null){
            return;
            //TODO Error pop up nothing selected or somthing else
        }else{
            Read read = (Read)choseReadChoiceBox.getSelectionModel().getSelectedItem();
            filteredReads.add(read);
            int length = read.getSequence().length();
            double oldUpperBound = xAxis.getUpperBound();
            List<GffEntry> gffEntries = read.getGFFEntries();

            //Has do be done like this, because the getDisplayedValue can only be calculated when the xAxis is drawn...
            if(xAxisSetHigherBoundIfNeeded(length)){
                addSequenceLength(length, oldUpperBound);
                addGenes(gffEntries, oldUpperBound);
            }
            else{
                addSequenceLength(length, -1);
                addGenes(gffEntries, -1);
            }

            addName(read.getTaxonomicId() + "");


        }




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
    private void addSequenceLength(int length, double oldUpperBound){
        Rectangle newSequence = new Rectangle();
        newSequence.setHeight(BAR_WIDTH);

        if(oldUpperBound == -1)
            newSequence.setWidth(xAxis.getDisplayPosition(length));
        else{
            newSequence.setWidth(xAxis.getDisplayPosition(length* (oldUpperBound/xAxis.getUpperBound())));
        }


        newSequence.setFill(Color.GRAY);
        sequences.getChildren().add(newSequence);

        xAxis.widthProperty().addListener((obs, oldVal, newVal) -> {

            newSequence.setWidth(newSequence.getWidth()* (newVal.doubleValue()/oldVal.doubleValue()));
        });

        xAxis.upperBoundProperty().addListener((obs, oldVal, newVal) -> {
            newSequence.setWidth((oldVal.doubleValue()/newVal.doubleValue()) * newSequence.getWidth());
        });

        barWidthSpinner.valueProperty().addListener(((observable, oldValue, newValue) -> {
            newSequence.setHeight(newValue);
        }));

        Tooltip t = new Tooltip("Länge: " + length);
        Tooltip.install(newSequence, t);
    }

    /**
     * Add all Genes to the plot
     * @param gffEntries
     * @param oldUpperBound
     */
    private void addGenes(List<GffEntry> gffEntries, double oldUpperBound){
        AnchorPane pane = new AnchorPane();
        pane.setMaxHeight(BAR_WIDTH);

        for(int j = 0; j < gffEntries.size(); j++){
            int start =gffEntries.get(j).getStart();
            int end = gffEntries.get(j).getEnd();
            Rectangle rectangle = new Rectangle();
            if(oldUpperBound == -1){
                rectangle.setWidth(xAxis.getDisplayPosition(end)-xAxis.getDisplayPosition(start));
                rectangle.setX(xAxis.getDisplayPosition(start));
            }
            else{
                rectangle.setWidth(xAxis.getDisplayPosition(end * (oldUpperBound/xAxis.getUpperBound()))-xAxis.getDisplayPosition(start * (oldUpperBound/xAxis.getUpperBound())));
                rectangle.setX(xAxis.getDisplayPosition(start * (oldUpperBound/xAxis.getUpperBound())));
            }

            rectangle.setHeight(BAR_WIDTH);

            rectangle.setFill(Color.rgb(178,34,34,0.5));
            rectangle.setStrokeWidth(0.5);
            rectangle.setStroke(Color.BLACK);

            Tooltip t = new Tooltip(gffEntries.get(j).getAttributes().get(" Name"));
            Tooltip.install(rectangle, t);

            xAxis.widthProperty().addListener((obs, oldVal, newVal) -> {
                rectangle.setWidth(rectangle.getWidth()* (newVal.doubleValue()/oldVal.doubleValue()));
                rectangle.setX(rectangle.getX() * (newVal.doubleValue()/oldVal.doubleValue()) );
            });

            xAxis.upperBoundProperty().addListener((obs, oldVal, newVal) -> {
                rectangle.setWidth(rectangle.getWidth()* (oldVal.doubleValue()/newVal.doubleValue()));
                rectangle.setX(rectangle.getX() * (oldVal.doubleValue()/newVal.doubleValue()) );
            });

            barWidthSpinner.valueProperty().addListener(((observable, oldValue, newValue) -> {
                rectangle.setHeight(newValue);
            }));


            pane.getChildren().add(rectangle);

        }

        sequences.getChildren().add(pane);
    }

    /**
     * Add the names to the Plot
     * @param name
     */
    private void addName(String name){
        //TODO Transform them to names add listener to spinner
        Label label = new Label(name);
        names.getChildren().add(label);
        names.setSpacing(BAR_WIDTH*1.25);
    }
}
