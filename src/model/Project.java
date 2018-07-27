package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import model.services.ReadInTaxTreeService;
import model.tax.TaxTree;

/**
 * This is a big container/storage class of the Samples for the the whole Project (of a Session)
 * All Samples get added to the list of Samples in here
 */

public class Project {

    private ObservableList<Sample> samples = FXCollections.observableArrayList();
    private ObservableList<Filter> filters = FXCollections.observableArrayList();
    public static TaxTree tree = new TaxTree();

    public Project(){
        //TODO write in statusbar that file is loading and no filtering possible
        ReadInTaxTreeService readInTaxTreeService = new ReadInTaxTreeService("res/TreeDumpFiles/nodes.dmp","res/TreeDumpFiles/names.dmp");
        readInTaxTreeService.setOnSucceeded(event1 -> {
            this.tree = ((ReadInTaxTreeService)event1.getSource()).getValue();
            tree.setIsLoaded(true);
        });

        readInTaxTreeService.setOnFailed(event1 -> {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Loading of TaxTree FAILED. Please insert nodes.dmp and names.dmp in res/TreeDumpFiles directory", ButtonType.CANCEL);
            alert.show();
        });
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
