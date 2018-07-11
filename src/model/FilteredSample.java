package model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

public class FilteredSample {

    /**
     * actual sample to be filtered
     */
    private ObjectProperty<Sample> sample;


    private ObservableList<Read> filteredReads;
    /**
     * actual given filter that has to be applied on the actual sample
     */
    private ObjectProperty<Filter> filter;

    /**
     * creates a new empty FilteredSample
     */
    public FilteredSample(Sample sample, Filter filter) {
        this.sample.setValue(sample);
        this.filter.setValue(filter);

        applyFilter();
    }

    public FilteredSample() {
        this.sample =  new SimpleObjectProperty<>();
        this.filter = new SimpleObjectProperty<>();

        this.filteredReads = FXCollections.observableArrayList();
    }

    private void applyFilter() {
        //TODO look that filtering works

        if(this.filter.getValue() != null && this.sample.getValue() != null) {
            filteredReads.clear();
            filteredReads.addAll(sample.getValue().getReads().filtered(this.filter.getValue().getFilterPredicate()));
        } else if(this.filter.getValue() == null && this.sample.getValue() != null) {
            filteredReads.clear();
            filteredReads.addAll(sample.getValue().getReads());
        }
    }

    /**
     * @return actual sample
     */
    public ObjectProperty<Sample> getSample() {
        return sample;
    }
    /**
     * @return actual filter
     */
    public ObjectProperty<Filter> getFilter() {
        return filter;
    }

    public void setSample(Sample sample) {
        this.sample.setValue(sample);
        applyFilter();
    }

    public void setFilter(Filter filter) {
        this.filter.setValue(filter);
        applyFilter();
    }

    public ObservableList<Read> getFilteredReads() {
        return filteredReads;
    }
}
