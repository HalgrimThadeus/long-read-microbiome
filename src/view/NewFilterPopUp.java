package view;


import javafx.stage.Stage;
import model.Filter;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import presenter.NewFilterPopUpPresenter;

import java.util.ArrayList;
import java.util.List;

public class NewFilterPopUp {

    /**
     * List of filters (idk if it belongs here)
     */
    private List<Filter> listoffilters = new ArrayList<>();

    /**
     * Save button
     */
    @FXML
    private Button saveNewFilter;
    /**
     * All texfields to get the values
     */

    @FXML
    TextField filtername;

    @FXML
    TextField genname;

    @FXML
    TextField lengthvalue;

    @FXML
    TextField taxaid;

    @FXML
    TextField gccontent;

    @FXML
    TextField scorevalue;

    private NewFilterPopUpPresenter newFilterPopUpPresenter;
    public NewFilterPopUp(NewFilterPopUpPresenter newFilterPopUpPresenter){
        this.newFilterPopUpPresenter = newFilterPopUpPresenter;
    }

    @FXML
    public void saveFilterBtnClicked(){
        Stage stage = (Stage) saveNewFilter.getScene().getWindow();

        if(!filtername.getText().equals("")){


            List<String> keys = new ArrayList<>();
            List<String> values = new ArrayList<>();

            keys.add("Gen");
            keys.add("Length");
            keys.add("TaxaId");
            keys.add("GC-Content");
            keys.add("Score");

            values.add(genname.getText());
            values.add(lengthvalue.getText());
            values.add(taxaid.getText());
            values.add(gccontent.getText());
            values.add(scorevalue.getText());


            newFilterPopUpPresenter.updateFilterList(filtername.getText(),keys,values);

            stage.close();
        }
        else {
            Alert alter = new Alert(Alert.AlertType.WARNING,"Please set a Name",ButtonType.OK);
            alter.show();
            return;
        }




    }
}
