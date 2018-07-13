package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import model.Sample;
import presenter.ComparatorPopUpPresenter;

import java.net.URL;
import java.util.ResourceBundle;

public class ComparatorPopUp extends AnchorPane implements Initializable {

    private ComparatorPopUpPresenter comparatorPopUpPresenter;

    private ObservableList<Sample> samples;

    @FXML
    ChoiceBox<String> sampleChoiceBox1;
    @FXML
    ChoiceBox<String> sampleChoiceBox2;
    @FXML
    ChoiceBox<String> filterChoiceBox1;
    @FXML
    ChoiceBox<String> filterChoiceBox2;
    @FXML
    ChoiceBox<String> comparisonModeChoiceBox;
    //@FXML
    //ListChangeListener<String> comparisonModeChangeListener;


    public ComparatorPopUp(){
    }

    @FXML
    public void onCompareButtonClicked(){
        String selectedSample1 = sampleChoiceBox1.getValue();
        String selectedSample2 = sampleChoiceBox2.getValue();
        String selectedFilter1 = filterChoiceBox1.getValue();
        String selectedFilter2 = filterChoiceBox2.getValue();
        try {
            comparatorPopUpPresenter.openComparatorView();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setComparatorPopUpPresenter(ComparatorPopUpPresenter comparatorPopUpPresenter) {
        this.comparatorPopUpPresenter = comparatorPopUpPresenter;
    }

    public void setChoiceBoxes(ObservableList<String> nameList, ObservableList<String> filterList){
        sampleChoiceBox1.setItems(nameList);
        sampleChoiceBox2.setItems(nameList);
        filterChoiceBox1.setItems(filterList);
        filterChoiceBox2.setItems(filterList);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> comparisonModeChoices = FXCollections.observableArrayList("GC content", "length", "number of genes", "gene density");
        comparisonModeChoiceBox.setItems(comparisonModeChoices);
        comparisonModeChoiceBox.getSelectionModel().selectFirst();
    }
}
