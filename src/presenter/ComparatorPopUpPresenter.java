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
import model.Filter;
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
    private ObservableMap<String, Filter> obeservableFilterMap  = FXCollections.observableHashMap();
    private ComparatorViewPresenter comparatorViewPresenter;
    private ComparatorPopUp comparatorPopUp;
    private LineChart<String, Number> vBox;
    private ObservableList<Sample> samples;
    private ObservableList<Filter> filters;

    public ComparatorPopUpPresenter(MainPresenter mainPresenter){
        this.mainPresenter = mainPresenter;
    }

    public void initialize(ObservableList<Sample> samples, ObservableList<Filter> filters, ComparatorPopUp comparatorPopUp){
        this.samples = samples;
        this.filters = filters;

        setComparatorPopUp(comparatorPopUp);

        ObservableList<String> sampleNames = FXCollections.observableArrayList();
        ObservableList<String> filterNames = FXCollections.observableArrayList();

        for (Sample sample: this.samples){
            observableSampleMap.put(sample.getName(),sample);
            sampleNames.add(sample.getName());
        }

        for (Filter filter: this.filters){
            obeservableFilterMap.put(filter.getName(),filter);
            filterNames.add(filter.getName());
        }

        comparatorPopUp.setChoiceBoxes(sampleNames, filterNames);

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

    public void openComparatorView() throws IOException, InterruptedException {
        ComparatorView newComparatorView = new ComparatorView(mainPresenter.getComparatorViewPresenter());
        Stage comparatorView = new Stage();
        FXMLLoader loader = new FXMLLoader(MainView.class.getResource("ComparatorView.fxml"));
        loader.setController(newComparatorView);
        Parent root = loader.load();
        Scene scene = new Scene(root, 700, 600);
        comparatorView.setTitle("Comparison");
        comparatorView.setScene(scene);
        comparatorView.show();
    }

    public void calculateViewableResults(String sample1, String filter1, String sample2, String filter2){

    }
}
