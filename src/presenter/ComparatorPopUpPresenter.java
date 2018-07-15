package presenter;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.stage.Stage;
import model.Comparator;
import model.Filter;
import model.FilteredSample;
import model.Sample;
import view.ComparatorPopUp;
import view.ComparatorView;
import view.MainView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ComparatorPopUpPresenter {
    private MainPresenter mainPresenter;
    private ObservableMap<String, Sample> observableSampleMap = FXCollections.observableHashMap();
    private ObservableMap<String, Filter> observableFilterMap = FXCollections.observableHashMap();
    private ComparatorViewPresenter comparatorViewPresenter;
    private ComparatorPopUp comparatorPopUp;
    private LineChart<String, Number> vBox;
    private ObservableList<Sample> samples;
    private ObservableList<Filter> filters;

    public ComparatorPopUpPresenter(ObservableList<Sample> samples, ObservableList<Filter> filters, ComparatorPopUp comparatorPopUp){
        this.samples = samples;
        this.filters = filters;
        this.comparatorPopUp = comparatorPopUp;

        for (Sample sample: this.samples){
            observableSampleMap.put(sample.getName(),sample);
        }

        for (Filter filter: this.filters){
            observableFilterMap.put(filter.getName(),filter);
        }

        comparatorPopUp.setChoiceBoxes((FXCollections.observableArrayList(observableSampleMap.keySet())), FXCollections.observableArrayList(observableFilterMap.keySet()));

        //TODO move
        samples.addListener((ListChangeListener<Sample>) change -> {

            change.next();
            List<String> names = new ArrayList<>();
            List<Sample> addedSamples = (List<Sample>) change.getAddedSubList();
            for (Sample sample: addedSamples){
                observableSampleMap.put(sample.getName(),sample);
                names.add(sample.getName());
            }

        });
    }

    public void setComparatorPopUp(ComparatorPopUp comparatorPopUp){
        this.comparatorPopUp = comparatorPopUp;
    }

    public void openComparatorView(String[] selections) throws IOException, InterruptedException {
        Sample sample1 = observableSampleMap.get(selections[0]);
        Filter filter1 = observableFilterMap.get(selections[2]);
        FilteredSample filteredSample1 = new FilteredSample();
        filteredSample1.setSample(sample1);
        filteredSample1.setFilter(filter1);
        Sample sample2 = observableSampleMap.get(selections[1]);
        Filter filter2 = observableFilterMap.get(selections[3]);
        FilteredSample filteredSample2 = new FilteredSample();
        filteredSample2.setSample(sample2);
        filteredSample2.setFilter(filter2);
        Comparator comparator = new Comparator(filteredSample1, filteredSample2, selections[4]);

        ComparatorView cv = new ComparatorView();
        Stage comparatorView = new Stage();
        FXMLLoader loader = new FXMLLoader(MainView.class.getResource("ComparatorView.fxml"));
        loader.setController(cv);
        Parent root = loader.load();
        Scene scene = new Scene(root, 700, 600);
        comparatorView.setTitle("Comparison");
        comparatorView.setScene(scene);

        comparatorView.show();
        ComparatorViewPresenter comparatorViewPresenter = new ComparatorViewPresenter(cv, comparator);
        cv.setComparatorViewPresenter(comparatorViewPresenter);
    }

    public void calculateViewableResults(String sample1, String filter1, String sample2, String filter2){

    }
}
