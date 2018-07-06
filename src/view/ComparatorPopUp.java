package view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import presenter.ComparatorPopUpPresenter;

public class ComparatorPopUp {
    @FXML
    private Button onCompareButtonClicked;

    private ComparatorPopUpPresenter comparatorPopUpPresenter;

    public ComparatorPopUp(ComparatorPopUpPresenter comparatorPopUpPresenter){
        this.comparatorPopUpPresenter = comparatorPopUpPresenter;
    }
    @FXML
    public void onCompareButtonClicked(){

    }
}
