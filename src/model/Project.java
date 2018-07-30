package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.services.ReadInTaxTreeService;
import model.tax.TaxTree;

/**
 * This is a big container/storage class of the Samples for the the whole Project (of a Session)
 * All Samples get added to the list of Samples in here
 */

public class Project {

    private ObservableList<Sample> samples = FXCollections.observableArrayList();
    private ObservableList<Filter> filters = FXCollections.observableArrayList();

    public static IntegerProperty treeLoadingStatus = new SimpleIntegerProperty();
    public static TaxTree tree = new TaxTree();

    public static final int LOADING =1;
    public static final int LOADED =2;
    public static final int LOADING_FAILED =0;

    public void loadTaxTree() {
        ReadInTaxTreeService readInTaxTreeService = new ReadInTaxTreeService("res/TaxDump/nodes.dmp","res/TaxDump/names.dmp");

        readInTaxTreeService.setOnSucceeded(event1 -> {
            tree = ((ReadInTaxTreeService)event1.getSource()).getValue();
            treeLoadingStatus.setValue(Project.LOADED);
        });

        readInTaxTreeService.setOnFailed(event1 -> {
            event1.getSource().getException().printStackTrace();
            treeLoadingStatus.setValue(Project.LOADING_FAILED);
        });

        treeLoadingStatus.setValue(Project.LOADING);
        readInTaxTreeService.start();
    }


    /**
     * Add a new Sample
     *
     * @param sample
     */
    public void addSamples(Sample sample) {
        this.samples.add(sample);
    }

    /**
     * adds a filter if one with same name doesnot exist. If it exists, old filter is overwritten.
     * @param filter
     */
    public void addOrSetFilter(Filter filter){

        boolean contained = false;
        for(int i = 0; i <  this.filters.size(); i++){
            if(this.filters.get(i).getName().equals(filter.getName())){
                contained = true;
                filters.set(i,filter);
            }
        }

        if(!contained) {
            filters.add(filter);
        }
    }

    /**
     * returns null if filter not found with this name
     * @param filterName
     * @return
     */
    public Filter getFilterByName(String filterName) {
        for (Filter filter: filters) {
            if(filter.getName().equals(filterName))
                return filter;
        }

        return null;
    }

    /**
     * returns null if sample not found with this name
     * @param sampleName
     * @return
     */
    public Sample getSampleByName(String sampleName) {
        for (Sample sample: samples) {
            if(sample.getName().equals(sampleName))
                return sample;
        }

        return null;
    }

    public ObservableList<Sample> getSamples() {
        return this.samples;
    }

    public ObservableList<Filter> getFilters() {
        return this.filters;
    }

    public void clear() {
        this.samples.clear();
        this.filters.clear();
    }

    @Override
    public String toString() {
        String res = "Version 1.0" + "\n";
        for (Sample sample : samples) {
            res += sample.toString();
        }
        res += "##########" + '\n'; //10 # for seperating the samples from the filters
        for (Filter filter: filters){
            res += filter.toString();
        }
        return res;
    }
}
