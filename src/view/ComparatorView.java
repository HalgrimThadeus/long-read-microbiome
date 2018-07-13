package view;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import presenter.ComparatorViewPresenter;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import static model.Comparator.*;

public class ComparatorView implements Initializable {

    private ComparatorViewPresenter comparatorViewPresenter;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private BarChart<String, Number> barChart;
    @FXML
    private Slider slider;
    @FXML
    private Label numberOfBinsLabel;

    private final int minNumberOfBins = 0;
    private final int maxNumberOfBins = 100;
    private int numberOfBins;


    public ComparatorView(){
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


        numberOfBins = 5;
        //numberOfBins = calculateNumberOfBins(data1,data2);
        double sizeOfRanges = calculateBoundaries(data1, data2 , numberOfBins);
        ObservableList<String> categories = FXCollections.observableArrayList(setCategoryNames(data1, data2, numberOfBins, sizeOfRanges));
        ObservableList<Integer> counts1 = FXCollections.observableArrayList(groupData(data1, numberOfBins, sizeOfRanges));
        ObservableList<Integer> counts2 = FXCollections.observableArrayList(groupData(data2, numberOfBins, sizeOfRanges));

        //chart:
        xAxis.setLabel("GC content [%]");
        yAxis.setLabel("number of reads");
        barChart.setTitle("Comparison GC content " + name1 + " , " + name2);
        xAxis.setCategories(categories);
        xAxis.setAutoRanging(true);
        yAxis.setAutoRanging(true);
        barChart.setBarGap(0);
        barChart.setCategoryGap(0);
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
        barChart.getData().addAll(series1, series2);

        //slider:
        slider.setMin(minNumberOfBins);
        slider.setMax(maxNumberOfBins);
        slider.setValue(numberOfBins);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setBlockIncrement(10);

        //label that shows actual number of bins:
        numberOfBinsLabel.setText(Integer.toString(numberOfBins));

        slider.valueChangingProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(!newValue.booleanValue()) {
                    numberOfBinsLabel.setText(Integer.toString((int) slider.getValue()));
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            numberOfBins = (int) slider.getValue();
                            double sizeOfRanges = calculateBoundaries(data1, data2, numberOfBins);
                            categories.clear();
                            ArrayList<String> categoriesList = setCategoryNames(data1, data2, numberOfBins, sizeOfRanges);
                            categories.addAll(categoriesList);
                            counts1.clear();
                            ArrayList<Integer> countsList1 = groupData(data1, numberOfBins, sizeOfRanges);
                            counts1.addAll(countsList1);
                            counts2.clear();
                            ArrayList<Integer> countsList2 = groupData(data2, numberOfBins, sizeOfRanges);
                            counts2.addAll(countsList2);
                            System.out.println("bins " + numberOfBins);
                            System.out.println("size ranges " + sizeOfRanges);
                            System.out.println("categories " + categories);
                            System.out.println(counts1);
                            System.out.println(counts2);

                            xAxis.setCategories(categories);

                            series1.getData().clear();
                            for (int i = 0; i < numberOfBins; i++) {
                                series1.getData().add(new XYChart.Data(categories.get(i), counts1.get(i)));
                            }
                            //sample 2
                            series2.getData().clear();
                            for (int i = 0; i < numberOfBins; i++) {
                                series2.getData().add(new XYChart.Data(categories.get(i), counts2.get(i)));
                            }
                        }
                    });

                }
            }
        });

    }

}
