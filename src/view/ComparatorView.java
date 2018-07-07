package view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import presenter.ComparatorViewPresenter;

import java.net.URL;
import java.util.ResourceBundle;

public class ComparatorView implements Initializable {

    ComparatorViewPresenter comparatorViewPresenter;
    @FXML
    CategoryAxis xAxis;
    @FXML
    NumberAxis yAxis;
    @FXML
    LineChart<String, Number> lineChart;


    public ComparatorView(ComparatorViewPresenter comparatorViewPresenter){
        this.comparatorViewPresenter = comparatorViewPresenter;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources){
        //xAxis = new CategoryAxis();
        //yAxis = new NumberAxis();
        xAxis.setLabel("GC content [%]");
        yAxis.setLabel("number of reads");
        //lineChart = new LineChart<String,Number>(xAxis,yAxis);
        lineChart.setTitle("Comparison GC content");

        //sample 1
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Sample 1");
        series1.getData().add(new XYChart.Data("0-10" , 8));
        series1.getData().add(new XYChart.Data("10-20" , 10));
        series1.getData().add(new XYChart.Data("20-30" , 15));
        series1.getData().add(new XYChart.Data("30-40" , 18));
        series1.getData().add(new XYChart.Data("40-50" , 20));
        series1.getData().add(new XYChart.Data("50-60" , 30));
        series1.getData().add(new XYChart.Data("60-70" , 17));
        series1.getData().add(new XYChart.Data("70-80" , 15));
        series1.getData().add(new XYChart.Data("80-90" , 15));
        series1.getData().add(new XYChart.Data("90-100" , 5));
        //sample 2
        XYChart.Series series2 = new XYChart.Series();
        series2.setName("Sample 2");
        series2.getData().add(new XYChart.Data("0-10" , 6));
        series2.getData().add(new XYChart.Data("10-20" , 8));
        series2.getData().add(new XYChart.Data("20-30" , 10));
        series2.getData().add(new XYChart.Data("30-40" , 16));
        series2.getData().add(new XYChart.Data("40-50" , 31));
        series2.getData().add(new XYChart.Data("50-60" , 25));
        series2.getData().add(new XYChart.Data("60-70" , 20));
        series2.getData().add(new XYChart.Data("70-80" , 20));
        series2.getData().add(new XYChart.Data("80-90" , 16));
        series2.getData().add(new XYChart.Data("90-100" , 7));

        lineChart.getData().addAll(series1, series2);

    }

        /*String name1 = "Sample1";
        ArrayList<Double> data1
                = new ArrayList<>(Arrays.asList(5.0,10.0,10.0,20.0,25.0,25.0,30.0,30.0,30.0,40.0,40.0,40.0,40.0,40.0,45.0,45.0,50.0,55.0,60.0,80.0));
        String name2 = "Sample2";
        ArrayList<Double> data2
                = new ArrayList<>(Arrays.asList(0.0,10.0,20.0,25.0,30.0,30.0,39.5,35.5,40.0,40.0,45.5,45.0,45.0,44.5,44.5,50.0,50.0,60.0,70.0,80.0,100.0));
        String name3 = "Sample3";
        ArrayList<Double> data3
                = new ArrayList<>(Arrays.asList(1.0,2.0,3.0,4.0,5.0,6.0));
        String name4 = "Sample4";
        ArrayList<Double> data4
                = new ArrayList<>(Arrays.asList(1.0,2.0,3.0,4.0,5.0));
        //int numberOfBins = 5;
        int numberOfBins = calculateNumberOfBins(data1,data2);
        double sizeOfRanges = calculateBoundaries(data1, data2 , numberOfBins);
        ArrayList<String> categories = setCategoryNames(data1, data2, numberOfBins, sizeOfRanges);
        ArrayList<Integer> counts1 = groupData(data1, numberOfBins, sizeOfRanges);
        ArrayList<Integer> counts2 = groupData(data2, numberOfBins, sizeOfRanges);

        /////////////////

        comparatorView.setTitle("Comparison");
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("GC content [%]");
        yAxis.setLabel("number of reads");
        final LineChart<String,Number> lineChart = new LineChart<String,Number>(xAxis,yAxis);
        lineChart.setTitle("Comparison GC content - "+name1+", "+name2);

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

        Scene scene  = new Scene(lineChart,800,600);
        lineChart.getData().addAll(series1, series2);
        comparatorView.setScene(scene);
        comparatorView.show();
     */
}
