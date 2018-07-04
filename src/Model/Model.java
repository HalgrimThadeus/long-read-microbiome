package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class Model {

    /**
     * Map with all Samples by Name.
     */
    private ObservableMap<String, Sample> samples;

    /**
     * List of all Filters
     */
    private ObservableList<Filter> filters;

    /**
     * Initialize them
     */
    public Model(){
        this.samples = FXCollections.observableHashMap();
        this.filters = FXCollections.observableArrayList();
    }

    /**
     * Returns the observableMap
     * @return
     */
    public ObservableMap<String, Sample> getSamples() {
        return samples;
    }

    /**
     * Returns sample by name
     * @param name
     * @return
     */
    public Sample getSampleByName(String name){
        return this.samples.get(name);
    }

    /**
     * get the List of filters
     * @return
     */
    public ObservableList<Filter> getFilters() {
        return filters;
    }

    /**
     * Adds a new Sample
     * @param name
     * @param sample
     */
    public void addSample(String name, Sample sample){
        this.samples.put(name, sample);
    }
}
