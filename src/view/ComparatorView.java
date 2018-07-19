package view;

import javafx.application.Platform;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
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

public class ComparatorView {

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

    private ComparatorViewPresenter comparatorViewPresenter;

    private final int minNumberOfBins = 0;
    private final int maxNumberOfBins = 100;
    private Property<Integer> numberOfBins = new SimpleObjectProperty<>(10);
    //private int numberOfBins = calculateNumberOfBins(data1,data2);
    private Property<ObservableList<String>> categories = new SimpleObjectProperty<>(FXCollections.observableArrayList());
    private Property<ObservableList<Integer>> counts1 = new SimpleObjectProperty<>(FXCollections.observableArrayList());
    private Property<ObservableList<Integer>> counts2 = new SimpleObjectProperty<>(FXCollections.observableArrayList());
    private Property<Boolean> redraw = new SimpleBooleanProperty(false);

    public ComparatorView(){
    }

    public void setRedraw(){
        redraw.setValue(!(redraw.getValue()));
    }

    public void setComparatorViewPresenter(ComparatorViewPresenter comparatorViewPresenter){
        this.comparatorViewPresenter = comparatorViewPresenter;
    }

    public Property<ObservableList<String>> getCategoriesProperty() {
        return categories;
    }

    public Property<ObservableList<Integer>> getCounts1Property() {
        return counts1;
    }

    public Property<ObservableList<Integer>> getCounts2Property() {
        return counts2;
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


    public void start(String name1, String name2, String filter1, String filter2, String comparisonMode){

        //chart:
        xAxis.setLabel(comparisonMode + " " + getScaleUnit(comparisonMode));
        yAxis.setLabel("number of reads");
        barChart.setTitle("compare " + comparisonMode + " " + name1 + ", " + filter1 + " & " + name2 + ", " + filter2);
        xAxis.setAutoRanging(true);
        yAxis.setAutoRanging(true);
        xAxis.setCategories(categories.getValue());
        barChart.setBarGap(0);
        barChart.setCategoryGap(0.1);
        //sample 1
        XYChart.Series series1 = new XYChart.Series();
        series1.setName(name1 + ", " + filter1);
        //sample 2
        XYChart.Series series2 = new XYChart.Series();
        series2.setName(name2 + ", " + filter2);
        barChart.getData().addAll(series1, series2);

        //slider:
        slider.setMin(minNumberOfBins);
        slider.setMax(maxNumberOfBins);
        slider.setValue(numberOfBins.getValue());
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setBlockIncrement(10);

        numberOfBinsLabel.setText(Integer.toString(numberOfBins.getValue()));

        slider.valueChangingProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(!newValue.booleanValue()) {
                    numberOfBins.setValue((int) slider.getValue());
                    numberOfBinsLabel.setText(Integer.toString(numberOfBins.getValue()));
                    comparatorViewPresenter.recalculateData((int) slider.getValue());
                }
            }
        });

        redraw.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
//                        System.out.println("view chart: categories : " + categories.getValue());
//                        System.out.println("view chart: counts1 : " + counts1.getValue());
//                        System.out.println("view chart: counts2 : " + counts2.getValue());
//                        System.out.println("view" + numberOfBins.getValue());
//                        System.out.println("start drawing");
                        //sample 1
                        series1.getData().clear();
                        //series1.getData().addAll(new XYChart.Data(categories.getValue(),counts1.getValue()));
                        for (int i = 0; i < numberOfBins.getValue(); i++) {
                            series1.getData().add(new XYChart.Data(categories.getValue().get(i), counts1.getValue().get(i)));
                        }
                        //sample 2
                        series2.getData().clear();
                        //series2.getData().addAll(new XYChart.Data(categories.getValue(),counts2.getValue()));
                        for (int i = 0; i < numberOfBins.getValue(); i++) {
                            series2.getData().add(new XYChart.Data(categories.getValue().get(i), counts2.getValue().get(i)));
                        }
//                        System.out.println("end drawing");
                    }
                });
            }
        });
              /* @Override
                public void onChanged(Change<? extends Integer> c) {
                   while (c.next()) {
                       if (c.wasAdded()) {
                           //sample 1
                           series1.getData().clear();
                           for (int i = 0; i < numberOfBins.getValue(); i++) {
                               series1.getData().add(new XYChart.Data(categories.getValue().get(i), counts1.getValue().get(i)));
                           }
                           //sample 2
                           series2.getData().clear();
                           for (int i = 0; i < numberOfBins.getValue(); i++) {
                               series2.getData().add(new XYChart.Data(categories.getValue().get(i), counts2.getValue().get(i)));
                           }
                           //xAxis.setAutoRanging(true);
                           //xAxis.setCategories(categories.getValue());
                       }
                   }
               }
           }
        );*/
    }


}
