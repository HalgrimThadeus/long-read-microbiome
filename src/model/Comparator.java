package model;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Comparator {
    String comparisonMode;
    FilteredSample filteredSample1;
    FilteredSample filteredSample2;
    List<Double> data1;
    List<Double> data2;
    public Property<Integer> numberOfBins;
    //public Property<Double> sizeOfRanges;
    public Property<ObservableList<String>> categories;
    public Property<ObservableList<Integer>> counts1;
    public Property<ObservableList<Integer>> counts2;


    public Comparator(FilteredSample filteredSample1, FilteredSample filteredSample2, String comparisonMode) {
        this.comparisonMode = comparisonMode;
        this.filteredSample1 = filteredSample1;
        this.filteredSample2 = filteredSample2;
        createDataWithInput();
        this.numberOfBins = new SimpleObjectProperty<>();
        //this.sizeOfRanges = new SimpleObjectProperty<>();
        this.categories = new SimpleObjectProperty<>(FXCollections.observableArrayList());
        this.counts1 = new SimpleObjectProperty<>(FXCollections.observableArrayList());
        this.counts2 = new SimpleObjectProperty<>(FXCollections.observableArrayList());
    }

    public String getComparisonMode() {
        return comparisonMode;
    }

    public FilteredSample getFilteredSample1() {
        return filteredSample1;
    }

    public FilteredSample getFilteredSample2() {
        return filteredSample2;
    }

    public List<Double> getData1() {
        return data1;
    }

    public List<Double> getData2() { return data2; }

    //gc-content
    private ArrayList<Double> calculateDataCGContent(FilteredSample filteredSample){
        ArrayList<Double> data = new ArrayList<Double>(filteredSample.getFilteredReads().size());
        for(Read read : filteredSample.getFilteredReads()){
            data.add(read.calculateGCContent());
        }
        return data;
    }

    //length of the genes
    private ArrayList<Double> calculateDataLength (FilteredSample filteredSample){
        ArrayList<Double> data = new ArrayList<Double>(filteredSample.getFilteredReads().size());
        for(Read read : filteredSample.getFilteredReads()){
            data.add((double)read.getSequence().length());
        }
        return data;
    }

    //number of genes
    private ArrayList<Double> calculateDataNumberOfGenes (FilteredSample filteredSample){
        ArrayList<Double> data = new ArrayList<Double>(filteredSample.getFilteredReads().size());
        for(Read read : filteredSample.getFilteredReads()){
            data.add((double)read.getGFFEntries().size());
        }
        return data;
    }

    //gene density
    private ArrayList<Double> calculateDataGeneDensity (FilteredSample filteredSample){
        ArrayList<Double> data = new ArrayList<Double>(filteredSample.getFilteredReads().size());
        for(Read read : filteredSample.getFilteredReads()){
            int numberOfGenes = read.getGFFEntries().size();
            int length = read.getSequence().length();
            data.add((numberOfGenes/(double)length)*1000.0); //average number of genes per 1000 basepairs
        }
        return data;
    }

    private void createDataWithInput() {
        switch (comparisonMode) {
            case "GC content":
                data1 = calculateDataCGContent(filteredSample1);
                data2 = calculateDataCGContent(filteredSample2);
                break;
            case "length":
                data1 = calculateDataLength(filteredSample1);
                data2 = calculateDataLength(filteredSample2);
                break;
            case "number of genes":
                data1 = calculateDataNumberOfGenes(filteredSample1);
                data2 = calculateDataNumberOfGenes(filteredSample2);
                break;
            case "gene density":
                data1 = calculateDataGeneDensity(filteredSample1);
                data2 = calculateDataGeneDensity(filteredSample2);
                break;
            default:
                System.err.println("no string as input");
                break;
        }
    }




    ////////////////////////////// Freedman-Diaconis rule:
    public static int calculateNumberOfBins(ArrayList<Double> data1, ArrayList<Double> data2){
        double optimizedNumberOfBins1 = 2*(interquartileRange(data1)/Math.pow(data1.size(),1.0/3.0));
        double optimizedNumberOfBins2 = 2*(interquartileRange(data2)/Math.pow(data2.size(),1.0/3.0));
        return (int) (Math.max(optimizedNumberOfBins1, optimizedNumberOfBins2));
    }
    public static double interquartileRange(List<Double> data){
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
        return q3-q1;
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

    public double calculateBoundaries(int numberOfBins) {
        //get maximum and minimum of the data and divide them by the given number of bins to get the size of the rages
        double minValue = 0;
        double maxValue = 0;
        if(data1.isEmpty()&&data2.isEmpty()){
            minValue = 0;
            maxValue = Double.MAX_VALUE;
        }
        else if(data1.isEmpty()){
            minValue = Collections.min(data2);
            maxValue = Collections.max(data2);
        }
        else if(data2.isEmpty()){
            minValue = Collections.min(data1);
            maxValue = Collections.max(data1);
        }
        else {
            minValue = Math.min(Collections.min(data1), Collections.min(data2));
            maxValue = Math.max(Collections.max(data1), Collections.max(data2));
        }
        return ((maxValue-minValue)/numberOfBins);
    }

    public void setCategoryNames(int numberOfBins){
        double sizeOfRanges = calculateBoundaries(numberOfBins);
        double minValue = 0;
        if(data1.isEmpty()&&data2.isEmpty()){
            minValue = 0;
        }
        else if(data1.isEmpty()){
            minValue = Collections.min(data2);
        }
        else if(data2.isEmpty()){
            minValue = Collections.min(data1);
        }
        else {
            minValue = Math.min(Collections.min(data1), Collections.min(data2));
        }
        List<String> categoriesList = new ArrayList<String>(numberOfBins);
        for(int i=0; i<numberOfBins; i++){
            DecimalFormat df = new DecimalFormat("#.00");
            categoriesList.add(df.format(i*sizeOfRanges+minValue) + "-" + df.format((i+1)*sizeOfRanges+minValue));
            //categoriesList.add(String.format( "%.2f",(i*sizeOfRanges+minValue)) + "-" + String.format( "%.2f",((i+1)*sizeOfRanges+minValue)));
        }
        categories.getValue().clear();
        categories.getValue().addAll(categoriesList);
    }

    public void groupData(int numberOfBins){
        double minValue = 0;
        if(data1.isEmpty()&&data2.isEmpty()){
            minValue = 0;
        }
        else if(data1.isEmpty()){
            minValue = Collections.min(data2);
        }
        else if(data2.isEmpty()){
            minValue = Collections.min(data1);
        }
        else {
            minValue = Math.min(Collections.min(data1), Collections.min(data2));
        }
        counts1.getValue().clear();
        counts2.getValue().clear();
        counts1.getValue().addAll(group(data1,minValue, numberOfBins));
        counts2.getValue().addAll(group(data2,minValue, numberOfBins));
    }

    public List<Integer> group(List<Double> data, double minValue, int numberOfBins){
        double sizeOfRanges = calculateBoundaries(numberOfBins);
        //sort data in right ranges - how many values fall into a specific range?
        List<Integer> counts = new ArrayList<Integer>();
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
}
