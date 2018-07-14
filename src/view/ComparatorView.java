package view;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import presenter.ComparatorViewPresenter;

import java.util.ArrayList;

import static model.Comparator.*;

public class ComparatorView {

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
    private int numberOfBins = 10;


    public ComparatorView(){
    }

    public void setComparatorViewPresenter(ComparatorViewPresenter comparatorViewPresenter){
        this.comparatorViewPresenter = comparatorViewPresenter;
    }

    public String getScaleUnit(String comparisonMode){
        String scaleUnit = "";
        switch (comparisonMode) {
            case "GC content":
                scaleUnit = "[%]";
                break;
            case "length":
                scaleUnit = "[bp]";
                break;
            case "number of genes":
                scaleUnit = "";
                break;
            case "gene density":
                scaleUnit = "[bp / kb]";
                break;
            default:
                System.err.println("no string as input");
                break;
        }
        return scaleUnit;
    }

//    public void recalculateData(int numberOfBins){
//    }

    public void start(ArrayList<Double> data1, ArrayList<Double> data2, String name1, String name2, String comparisonMode){

        //numberOfBins = 10;
        //numberOfBins = calculateNumberOfBins(data1,data2);
        double sizeOfRanges = calculateBoundaries(data1, data2 , numberOfBins);
        ObservableList<String> categories = FXCollections.observableArrayList(setCategoryNames(data1, data2, numberOfBins, sizeOfRanges));
        ObservableList<Integer> counts1 = FXCollections.observableArrayList(groupData(data1, numberOfBins, sizeOfRanges));
        ObservableList<Integer> counts2 = FXCollections.observableArrayList(groupData(data2, numberOfBins, sizeOfRanges));

        //chart:
        xAxis.setLabel(comparisonMode + " " + getScaleUnit(comparisonMode));
        yAxis.setLabel("number of reads");
        barChart.setTitle("compare " + comparisonMode + " " + name1 + " , " + name2);
        xAxis.setAutoRanging(true);
        yAxis.setAutoRanging(true);
        xAxis.setCategories(categories);
        barChart.setBarGap(0);
        barChart.setCategoryGap(0.1);
        //sample 1
        XYChart.Series series1 = new XYChart.Series();
        series1.setName(name1);
        for(int i=0; i<numberOfBins; i++){
            series1.getData().add(new XYChart.Data(categories.get(i) , counts1.get(i)));
        }
        //sample 2
        XYChart.Series series2 = new XYChart.Series();
        series2.setName(name2);
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
                            //xAxis.setCategories(categories);

                            //sample 1
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
