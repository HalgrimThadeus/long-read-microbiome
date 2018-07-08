package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import presenter.ComparatorPopUpPresenter;

import java.net.URL;
import java.util.ResourceBundle;

public class ComparatorPopUp implements Initializable {

    private ComparatorPopUpPresenter comparatorPopUpPresenter;

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


    public ComparatorPopUp(ComparatorPopUpPresenter comparatorPopUpPresenter){
        this.comparatorPopUpPresenter = comparatorPopUpPresenter;
    }

    @FXML
    public void onCompareButtonClicked(){
        try {
            comparatorPopUpPresenter.openComparatorView();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> comparisonModeChoices = FXCollections.observableArrayList("GC content", "length", "number of genes", "gene density");
        comparisonModeChoiceBox.setItems(comparisonModeChoices);
        comparisonModeChoiceBox.getSelectionModel().selectFirst();

        //comparisonModeChoiceBox.getSelectionModel().selectedItemProperty().addListener(comparisonModeChangeListener);

    }
}
