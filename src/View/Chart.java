package View;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import Model.IO.CsvIO;
import Model.IO.FastAIO;
import Model.IO.GffIO;
import Model.IO.SampleReader;
import Model.Sample;
import Model.Tax.TaxIO;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Chart extends Application {

    final int showInChart = 11;

    @Override
    public void start(Stage stage) throws Exception {
        Sample sample = SampleReader.read("res/ehec/ehec.f1000000-ex.fasta", "res/ehec/ehec.f1000000-all.gff", "res/ehec/ehec.f1000000-readname2taxonid.txt");

        List<String> seqNames = new ArrayList<String>();
        List<Integer> seqLength = new ArrayList<Integer>();

        for(int i = 0; i < showInChart; i++){
            String name = sample.getReads().get(i).getTaxonomicId()+"";
            for(int j = 0; j < i; j++){
                if(name.equals(seqNames.get(j))){
                    name += "'";
                }
            }

            int length = sample.getReads().get(i).getSequence().length();

            seqNames.add(name);
            seqLength.add(length);
        }

        //Defining the x axis
        CategoryAxis yAxis = new CategoryAxis();

        yAxis.setCategories(FXCollections.<String>observableArrayList(seqNames));
        yAxis.setLabel("Sequences");
        yAxis.setGapStartAndEnd(true);

        //Defining the y axis
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Sequence-Length in bp");

        //Creating the Bar chart
        BarChart<Number, String> BarChart = new BarChart<>(xAxis, yAxis);
        BarChart.setTitle("Sequences with Reads");


        //Prepare XYChart.Series objects by setting data
        XYChart.Series<Number, String> sequences = new XYChart.Series<>();
        sequences.setName("Sequences");
        for(int i = 0; i < seqLength.size(); i++){
            sequences.getData().add(new XYChart.Data<Number, String>(seqLength.get(i), seqNames.get(i)));

        }




        BarChart.getData().addAll(sequences);

        //Creating a Group object
        Group root = new Group(BarChart);

        //Creating a scene object
        Scene scene = new Scene(root);


        //Setting title to the Stage
        stage.setTitle("stackedBarChart");

        //Adding scene to the stage
        stage.setScene(scene);

        scene.getStylesheets().add("View/style.css");

        //Displaying the contents of the stage
        stage.show();

        Node chartPlotArea = BarChart.lookup(".chart-plot-background");
        double chartZeroX = chartPlotArea.getLayoutX();
        double chartZeroY = chartPlotArea.getLayoutY();


        for(int i = 0; i < seqLength.size(); i++){
            for(int j = 0; j < sample.getReads().get(i).getGFFEntries().size(); j++){
                int start = sample.getReads().get(i).getGFFEntries().get(j).getStart();
                int end = sample.getReads().get(i).getGFFEntries().get(j).getEnd();
                Rectangle rectangle = new Rectangle();
                rectangle.setWidth(xAxis.getDisplayPosition(end)-xAxis.getDisplayPosition(start));
                rectangle.setHeight(yAxis.getCategorySpacing()/4);
                rectangle.setX(yAxis.getWidth() + yAxis.getLayoutX()+yAxis.getScaleX() + 4 + xAxis.getDisplayPosition(start));
                rectangle.setY(yAxis.getDisplayPosition(seqNames.get(i)) + yAxis.getCategorySpacing());
                rectangle.setFill(Color.rgb(178,34,34,0.5));
                rectangle.setStrokeWidth(0.5);
                rectangle.setStroke(Color.BLACK);
                root.getChildren().add(rectangle);

            }
        }



    }



    public static void main(String[] args) {
        launch(args);
    }


}