package model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ProgressBar;

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
    public FilteredSample() {
        this.sample =  new SimpleObjectProperty<>();
        this.filter = new SimpleObjectProperty<>();

        this.filteredReads = FXCollections.observableArrayList();
    }

    private void applyFilter() {
        if(Project.treeLoadingStatus.getValue() == Project.LOADED) {
            if (this.filter.getValue() != null && this.sample.getValue() != null) {
                filteredReads.setAll(sample.getValue().getReads().filtered(this.filter.getValue().getFilterPredicate()));
            } else if (this.filter.getValue() == null && this.sample.getValue() != null) {
                filteredReads.setAll(sample.getValue().getReads());
            }
        }
        else{
            Project.treeLoadingStatus.addListener((observable, oldValue, newValue) -> {
                if(newValue.intValue() == Project.LOADED)
                    applyFilter();
            });
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

    public void setSample(Sample sample)  {
        this.sample.setValue(sample);
        applyFilter();
    }

    public void setFilter(Filter filter) {
        this.filter.setValue(filter);
        applyFilter();
    }

    public Filter addFilter(Filter filter) {
        Filter combinedFilter = filter;
        if(this.filter.getValue() != null) {
            combinedFilter = Filter.combineFilter(this.filter.getValue(), filter);
        }
        this.filter.setValue(combinedFilter);
        applyFilter();
        return combinedFilter;
    }

    public ObservableList<Read> getFilteredReads() {
        return filteredReads;
    }
}
