package View;

import Model.GffEntry;
import Model.IO.SampleReader;
import Model.Read;
import Model.Sample;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import javafx.event.ActionEvent;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TabPaneView implements Initializable {
    @FXML
    public AnchorPane readChartPane;

    @FXML
    public VBox sequences;

    @FXML
    public NumberAxis xAxis;

    @FXML
    public Button addReadButton;

    @FXML
    public ChoiceBox choseReadChoiceBox;

    private final double BAR_WIDTH = 20;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Sample sample = SampleReader.read("res/ehec/ehec.f1000000-ex.fasta", "res/ehec/ehec.f1000000-all.gff", "res/ehec/ehec.f1000000-readname2taxonid.txt");
            choseReadChoiceBox.getItems().addAll(sample.getReads());
        } catch (Exception e) {
            e.printStackTrace();
        }


        xAxis.setLowerBound(0);
        xAxis.setUpperBound(2000);
        xAxis.setTickUnit(200);
        xAxis.setMinorTickLength(1);


        xAxis.setAutoRanging(false);


    }


    public void addReadButtonClicked(ActionEvent actionEvent) {
        if(choseReadChoiceBox.getSelectionModel().getSelectedItem() == null){
            return;
            //TODO Error pop up nothing selected
        }else{
            Read read = (Read)choseReadChoiceBox.getSelectionModel().getSelectedItem();
            int length = read.getSequence().length();
            List<GffEntry> gffEntries = read.getGFFEntries();


            if(xAxisSetHigherBoundIfNeeded(length)){
                //TODO IT IS problematic, that the frame isnt repainted, so the Displaypositions for the first time are not correct
                xAxis.getScene().getRoot().requestLayout();
            }

            readChartPane.requestLayout();

            addSequenceLength(length);
            addGenes(gffEntries);
        }


    }


    private boolean xAxisSetHigherBoundIfNeeded(int length){
        boolean wasNeeded = false;
        if(xAxis.getUpperBound() < length){
            wasNeeded = true;
            xAxis.setUpperBound((length + 1000)/1000 * 1000);
            xAxis.setTickUnit(1000);
        }

        return wasNeeded;
    }

    private void addSequenceLength(int length){


        Rectangle newSequence = new Rectangle();
        newSequence.setHeight(BAR_WIDTH);
        newSequence.setWidth(xAxis.getDisplayPosition(length));
        System.out.println(length);
        newSequence.setFill(Color.GRAY);
        sequences.getChildren().add(newSequence);

        xAxis.widthProperty().addListener((obs, oldVal, newVal) -> {

            newSequence.setWidth(newSequence.getWidth()* (newVal.doubleValue()/oldVal.doubleValue()));
        });

        xAxis.upperBoundProperty().addListener((obs, oldVal, newVal) -> {
            newSequence.setWidth((oldVal.doubleValue()/newVal.doubleValue()) * newSequence.getWidth());
        });

        Tooltip t = new Tooltip("Länge: " + length);
        Tooltip.install(newSequence, t);
    }

    private void addGenes(List<GffEntry> gffEntries){
        AnchorPane pane = new AnchorPane();
        pane.setMaxHeight(BAR_WIDTH);

        for(int j = 0; j < gffEntries.size(); j++){
            int start =gffEntries.get(j).getStart();
            int end = gffEntries.get(j).getEnd();
            Rectangle rectangle = new Rectangle();
            rectangle.setWidth(xAxis.getDisplayPosition(end)-xAxis.getDisplayPosition(start));
            rectangle.setHeight(BAR_WIDTH);
            rectangle.setX(xAxis.getDisplayPosition(start));
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


            pane.getChildren().add(rectangle);

        }

        sequences.getChildren().add(pane);
    }

}
