package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Comparator {
    String comparisonMode;
    Sample filteredSample1;
    Sample filteredSample2;
    List<List<Double>> data = new ArrayList<>();

    public Comparator(Sample filteredSample1, Sample filteredSample2, String comparisonMode) {
        this.comparisonMode = comparisonMode;
        this.filteredSample1 = filteredSample1;
        this.filteredSample2 = filteredSample2;
        createDataWithInput();
    }

    public List<List<Double>> getData() {
        return data;
    }

    //gc-content
    private ArrayList<Double> calculateDataCGContent(Sample filteredSample){
        ArrayList<Double> data = new ArrayList<Double>(filteredSample.getReads().size());
        for(Read read : filteredSample.getReads()){
            data.add(read.calculateGCContent());
        }
        return data;
    }

    //length of the genes
    private ArrayList<Double> calculateDataLength (Sample filteredSample){
        ArrayList<Double> data = new ArrayList<Double>(filteredSample.getReads().size());
        for(Read read : filteredSample.getReads()){
            data.add((double)read.getSequence().length());
        }
        return data;
    }

    //number of genes
    private ArrayList<Double> calculateDataNumberOfGenes (Sample filteredSample){
        ArrayList<Double> data = new ArrayList<Double>(filteredSample.getReads().size());
        for(Read read : filteredSample.getReads()){
            data.add((double)read.getGFFEntries().size());
        }
        return data;
    }

    //gene density
    private ArrayList<Double> calculateDataGeneDensity (Sample filteredSample){
        ArrayList<Double> data = new ArrayList<Double>(filteredSample.getReads().size());
        for(Read read : filteredSample.getReads()){
            int numberOfGenes = read.getGFFEntries().size();
            int length = read.getSequence().length();
            data.add((double)numberOfGenes/length); //average number of genes per basepair
        }
        return data;
    }

    private void createDataWithInput(){
        List<Double> data1 = new ArrayList<>();
        List<Double> data2 = new ArrayList<>();
        switch (comparisonMode){
            case "GC content": data1 = calculateDataCGContent(filteredSample1);
                                data2 = calculateDataCGContent(filteredSample2);
                                break;
            case "length": data1 = calculateDataLength(filteredSample1);
                            data2 = calculateDataLength(filteredSample2);
                            break;
            case "number of genes": data1 = calculateDataNumberOfGenes(filteredSample1);
                                    data2 = calculateDataNumberOfGenes(filteredSample2);
                                    break;
            case "gene density": data1 = calculateDataGeneDensity(filteredSample1);
                data2 = calculateDataGeneDensity(filteredSample2);
                break;
            default: System.err.println("no string as input");
                break;
        }
        this.data.add(data1);
        this.data.add(data2);
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

    public static double calculateBoundaries(ArrayList<Double> data1, ArrayList<Double> data2, int numberOfBins) {
        //get maximum and minimum of the data and divide them by the given number of bins to get the size of the rages
        double sizeOfRanges;
        double minValue = Math.min(Collections.min(data1),Collections.min(data2));
        double maxValue = Math.max(Collections.max(data1),Collections.max(data2));
        return (maxValue-minValue)/numberOfBins;
    }

    public static ArrayList<String> setCategoryNames(ArrayList<Double> data1, ArrayList<Double> data2, int numberOfBins, double sizeOfRanges){
        double minValue = Math.min(Collections.min(data1),Collections.min(data2));
        ArrayList<String> categories = new ArrayList<String>(numberOfBins);
        for(int i=0; i<numberOfBins; i++){
            //categories.add(i*sizeOfRanges+minValue + "-" + ((i+1)*sizeOfRanges+minValue));
            categories.add(String.format( "%.2f",i*sizeOfRanges+minValue) + "-" + String.format( "%.2f",(i+1)*sizeOfRanges+minValue));
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
}
