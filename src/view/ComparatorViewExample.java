package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class ComparatorViewExample extends Application {


    @Override public void start(Stage stage) {

        /*
        int numberOfBins; //given as default/Freedman–Diaconis rule or user input (can be changed) - determines size of counts & categories list
        final int minNumberOfBins = 0;
        final int maxNumberOfBins = 100;
        double sizeOfRanges; //calculated by concrete data values (smallest-biggest) & number of bins
        ArrayList<Double> data; //data array - stores calculated data for each read in the sample
        ArrayList<Integer> counts = new ArrayList<Integer>(); //count array - stores number of reads within one range in one field
        ArrayList<String> categories = new ArrayList<String>(); //string array - stores names for categories/ranges
        */

        String name1 = "Sample1";
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

        System.out.println(numberOfBins);
        System.out.println(sizeOfRanges);
        System.out.println(categories);
        System.out.println(counts1);
        System.out.println(counts2);

        /////////////////////////////////////////////////

        stage.setTitle("Comparison");
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
        stage.setScene(scene);
        stage.show();
    }

//    public static void main(String[] args) {
//
//        launch(args);
//    }

    //////////////////////////////////// Freedman–Diaconis rule:
    public static int calculateNumberOfBins(ArrayList<Double> data){
        return (int)(2*(interquantileRange(data)/Math.pow(data.size(),1.0/3.0)));
    }
    public static int calculateNumberOfBins(ArrayList<Double> data1, ArrayList<Double> data2){
        return (Math.max(calculateNumberOfBins(data1), calculateNumberOfBins(data2)));
    }
    public static double interquantileRange(List<Double> data){
        double q1 = 0;
        double q3 = 0;
        if(data.size()%2 == 0) {
            q1 = median(data.subList(0, data.size() / 2 -1));
            q3 = median(data.subList(data.size() / 2, data.size()-1));
        }
        else{
            q1 = median(data.subList(0, (data.size()-1) / 2 -1));
            q3 = median(data.subList((data.size()+1) / 2, data.size()-1));
        }
        double interquantileRange = q3-q1;
        return interquantileRange;
    }
    public static double median(List<Double> data){
        double median;
        Collections.sort(data);
        if(data.size()%2 == 1) {
            median = data.get((data.size()-1) / 2);
        }
        else{
            median = (data.get(data.size()/2 -1) + data.get(data.size()/2)) / 2;
        }
        return median;
    }
    ////////////////////////////////////

    public static double calculateBoundaries(ArrayList<Double> data, int numberOfBins){
        double minValue = Collections.min(data);
        double maxValue = Collections.max(data);
        return (maxValue-minValue)/numberOfBins;
    }
    public static double calculateBoundaries(ArrayList<Double> data1, ArrayList<Double> data2, int numberOfBins) {
        //get maximum and minimum of the data and divide them by the given number of bins to get the size of the rages
        double sizeOfRanges;
        double minValue = Math.min(Collections.min(data1),Collections.min(data2));
        double maxValue = Math.max(Collections.max(data1),Collections.max(data2));
        return (maxValue-minValue)/numberOfBins;
    }

    public static ArrayList<String> setCategoryNames(ArrayList<Double> data, int numberOfBins, double sizeOfRanges){
        double minValue = Collections.min(data);
        ArrayList<String> categories = new ArrayList<String>(numberOfBins);
        for(int i=0; i<numberOfBins; i++){
            categories.add(i*sizeOfRanges+minValue + "-" + ((i+1)*sizeOfRanges+minValue));
        }
        return categories;
    }
    public static ArrayList<String> setCategoryNames(ArrayList<Double> data1, ArrayList<Double> data2, int numberOfBins, double sizeOfRanges){
        double minValue = Math.min(Collections.min(data1),Collections.min(data2));
        ArrayList<String> categories = new ArrayList<String>(numberOfBins);
        for(int i=0; i<numberOfBins; i++){
            categories.add(i*sizeOfRanges+minValue + "-" + ((i+1)*sizeOfRanges+minValue));
        }
        return categories;
    }

    public static ArrayList<Integer> groupData(ArrayList<Double> data, int numberOfBins, double sizeOfRanges){
        //sort data in right ranges - how many values fall into a specific range?
        double minValue = Collections.min(data);
        ArrayList<Integer> counts = new ArrayList<Integer>(numberOfBins);
        for(int i = 0; i<numberOfBins; i++){ counts.add(0); }
        for(double d : data){
            for(int i=0; i<numberOfBins; i++){
                if (d>=i*sizeOfRanges+minValue && d<=(i+1)*sizeOfRanges+minValue){
                    counts.set(i, counts.get(i)+1); //number of reads with value in this range increases (+1)
                }
            }
        }
        return counts;
    }


    /*
    //gc-content
    public ArrayList<Double> getDataCGContent(Sample sample){
        ArrayList<Double> data = new ArrayList<Double>(sample.getReads().size());
        for(Read read : sample.getReads()){
            data.add(read.calculateGCContent());
        }
        return data;
    }

    //length of the genes
    public ArrayList<Double> getDataLength (Sample sample){
        ArrayList<Double> data = new ArrayList<Double>(sample.getReads().size());
        for(Read read : sample.getReads()){
            data.add((double)read.getSequence().length());
        }
        return data;
    }

    //number of genes
    public ArrayList<Double> getDataNumberOfGenes (Sample sample){
        ArrayList<Double> data = new ArrayList<Double>(sample.getReads().size());
        for(Read read : sample.getReads()){
            data.add((double)read.getGFFEntries().size());
        }
        return data;
    }

    //gene density
    public ArrayList<Double> getDataGeneDensity (Sample sample){
        ArrayList<Double> data = new ArrayList<Double>(sample.getReads().size());
        for(Read read : sample.getReads()){
            int numberOfGenes = read.getGFFEntries().size();
            int length = read.getSequence().length();
            data.add((double)numberOfGenes/length); //average number of genes per basepair
        }
        return data;
    }
    */
}
