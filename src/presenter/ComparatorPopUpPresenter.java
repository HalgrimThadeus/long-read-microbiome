package presenter;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.ComparatorPopUp;
import view.ComparatorView;
import view.MainView;

import java.io.IOException;

public class ComparatorPopUpPresenter {

    private ComparatorViewPresenter comparatorViewPresenter;
    private ComparatorPopUp comparatorPopUp;

    public ComparatorPopUpPresenter(){
    }

    public void openComparatorView() throws IOException {
        ComparatorView newComparatorView = new ComparatorView(comparatorViewPresenter);

        Stage comparatorView = new Stage();
        FXMLLoader loader = new FXMLLoader(MainView.class.getResource("ComperatorView.fxml"));
        loader.setController(newComparatorView);
        Parent root = loader.load();

        comparatorView.setTitle("Compararison");
        comparatorView.setScene(new Scene(root, 600, 250));
        comparatorView.show();



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
}
