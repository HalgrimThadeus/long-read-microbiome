package model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class FilteredSample {

    /**
     * actual sample to be filtered
     */
    private ObjectProperty<Sample> sample = new SimpleObjectProperty<>();
    /**
     * actual given filter that has to be applied on the actual sample
     */
    private ObjectProperty<Filter> filter = new SimpleObjectProperty<>();

    /**
     * creates a new empty FilteredSample
     */
    public FilteredSample() {
    }

    /**
     * creates a new FilteredSample, given a sample and a filter to be applied
     * @param sample
     * @param filter
     */
    public FilteredSample(Sample sample, Filter filter) {
        this.sample.setValue(sample);
        this.filter.setValue(filter);
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
        if(this.filter.getValue() != null) {
            sample.setReads(this.filter.getValue().suitable(sample));
        }
        this.sample.setValue(sample);
    }

    public void setFilter(Filter filter) {
        Sample sample = this.sample.getValue();
        if(filter != null) {
            sample.setReads(this.filter.getValue().suitable(sample));
        }
        this.filter.setValue(filter);
        //maybe theres a better way to trigger onchangelisteners
        //todo listerner for reads
        this.sample.setValue(null);
        this.sample.setValue(sample);
    }
}
