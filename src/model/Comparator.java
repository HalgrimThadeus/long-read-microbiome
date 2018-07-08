package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Comparator {

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
}
