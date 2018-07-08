package view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Slider;
import presenter.ComparatorViewPresenter;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import static model.Comparator.*;

public class ComparatorView implements Initializable {

    ComparatorViewPresenter comparatorViewPresenter;
    @FXML
    CategoryAxis xAxis;
    @FXML
    NumberAxis yAxis;
    @FXML
    LineChart<String, Number> lineChart;
    @FXML
    Slider slider;


    public ComparatorView(ComparatorViewPresenter comparatorViewPresenter){
        this.comparatorViewPresenter = comparatorViewPresenter;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources){

        //example:
        String name1 = "Sample1";
        ArrayList<Double> data1
                = new ArrayList<>(Arrays.asList(5.0,10.0,10.0,20.0,25.0,25.0,30.0,30.0,30.0,40.0,40.0,40.0,40.0,40.0,45.0,45.0,50.0,55.0,60.0,80.0));
        String name2 = "Sample2";
        ArrayList<Double> data2
                = new ArrayList<>(Arrays.asList(0.0,10.0,20.0,25.0,30.0,30.0,39.5,35.5,40.0,40.0,45.5,45.0,45.0,44.5,44.5,50.0,50.0,60.0,70.0,80.0,100.0));
        int numberOfBins = 10;
        //int numberOfBins = calculateNumberOfBins(data1,data2);
        double sizeOfRanges = calculateBoundaries(data1, data2 , numberOfBins);
        ArrayList<String> categories = setCategoryNames(data1, data2, numberOfBins, sizeOfRanges);
        ArrayList<Integer> counts1 = groupData(data1, numberOfBins, sizeOfRanges);
        ArrayList<Integer> counts2 = groupData(data2, numberOfBins, sizeOfRanges);

        //chart:
        xAxis.setLabel("GC content [%]");
        yAxis.setLabel("number of reads");
        lineChart.setTitle("Comparison GC content " + name1 + " , " + name2);
        //sample 1
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Sample 1");
        for(int i=0; i<numberOfBins; i++){
            series1.getData().add(new XYChart.Data(categories.get(i) , counts1.get(i)));
        }
        //sample 2
        XYChart.Series series2 = new XYChart.Series();
        series2.setName("Sample 2");
        for(int i=0; i<numberOfBins; i++){
            series2.getData().add(new XYChart.Data(categories.get(i) , counts2.get(i)));
        }
        lineChart.getData().addAll(series1, series2);


        //slider:
        slider.setMin(0);
        slider.setMax(100);
        slider.setValue(numberOfBins);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(50);
        slider.setMinorTickCount(5);
        slider.setBlockIncrement(10);

    }

}
