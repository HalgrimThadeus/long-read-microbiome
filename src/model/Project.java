package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This is a big container/storage class of the Samples for the the whole Project (of a Session)
 * All Samples get added to the list of Samples in here
 */

public class Project {

    private ObservableList<Sample> samples = FXCollections.observableArrayList();
    private ObservableList<Filter> filters = FXCollections.observableArrayList();

    /**
     * Add a new Sample
     *
     * @param sample
     */
    public void addSamples(Sample sample) {
        samples.add(sample);
    }

    public void addFilter(Filter filter){
        this.filters.add(filter);
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
        String res = "";
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
