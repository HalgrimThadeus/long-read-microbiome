package view;

import javafx.fxml.FXML;
import presenter.ComparatorPopUpPresenter;

public class ComparatorPopUp {

    private ComparatorPopUpPresenter comparatorPopUpPresenter;

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
}
