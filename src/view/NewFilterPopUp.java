package view;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.FilterBuilder;
import presenter.NewFilterPopUpPresenter;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class NewFilterPopUp implements Initializable {

    /**
     * Save button
     */
    @FXML
    private Button saveNewFilter;
    /**
     * All texfields to get the values
     */


    @FXML
    ChoiceBox<String> lengthCompareChoice = new ChoiceBox();
    @FXML
    ChoiceBox GCCompareChoice = new ChoiceBox();
    @FXML
    ChoiceBox scoreCompareChoice = new ChoiceBox();



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

    FilterBuilder filterBuilder = new FilterBuilder();

    private NewFilterPopUpPresenter newFilterPopUpPresenter;
    public NewFilterPopUp(NewFilterPopUpPresenter newFilterPopUpPresenter){
        this.newFilterPopUpPresenter = newFilterPopUpPresenter;
    }


    @FXML
    public void saveFilterBtnClicked(){
        Stage stage = (Stage) saveNewFilter.getScene().getWindow();



        List<String> usedKeys = new ArrayList<>();
        List<String> usedValues = new ArrayList<>();
        List<String> usedCompare = new ArrayList<>();
        if(!filtername.getText().equals("")){
        if(filtername.getText().contains("##########")){
            Alert alert = new Alert(Alert.AlertType.WARNING,"Name not allowed to contain: ########## ",ButtonType.OK);
            alert.show();
            return;
        }
            /**Builds the filterBuilder with the predicates needed to fit the criterias e.g "ands" one of the existing
             * predicates in FilterBuilder (model) to it's main PredicateField, with the user-given filter input
             * **/

            //Adds gen name predicate to the filter if a name is given
            if(!genname.getText().equals("")){
                usedKeys.add("Gen");
                usedValues.add(genname.getText());
                usedCompare.add("=");
            }

            //adds gc content predicate to filter if a gc content is given
            if(!gccontent.getText().equals("")) {
                //adds smallereq predicate if the textfield contains a <
                if (isDouble(gccontent.getText())) {
                    usedKeys.add("GC");
                    usedValues.add(gccontent.getText());
                    usedCompare.add((String) GCCompareChoice.getSelectionModel().getSelectedItem());
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Please put in a Number", ButtonType.OK);
                    alert.show();
                    return;
                }
            }
             if(!lengthvalue.getText().equals("")) {
                 if (isDigit(lengthvalue.getText())) {
                     usedKeys.add("Length");
                     usedValues.add(lengthvalue.getText());
                     usedCompare.add((String) lengthCompareChoice.getSelectionModel().getSelectedItem());
                 } else {
                     Alert alert = new Alert(Alert.AlertType.WARNING, "Please put in an Integer", ButtonType.OK);
                     alert.show();
                     return;
                 }
             }
             if(!scorevalue.getText().equals("")) {
                 if (isDigit(scorevalue.getText())) {
                     usedKeys.add("Score");
                     usedValues.add(scorevalue.getText());
                     usedCompare.add((String) scoreCompareChoice.getSelectionModel().getSelectedItem());
                 } else {
                     Alert alert = new Alert(Alert.AlertType.WARNING,"Please put in a Number",ButtonType.OK);
                     alert.show();
                     return;
                 }
             }
             if(!taxaid.getText().equals(null)){
                usedKeys.add("Taxa");
                usedValues.add(taxaid.getText());
                usedCompare.add("=");
            }

            newFilterPopUpPresenter.updateFilterList(filtername.getText(),usedKeys,usedValues,usedCompare);

            stage.close();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING,"Please set a Name",ButtonType.OK);
            alert.show();
            return;
            }


    }
    //Setters for the saved Filters
    @FXML
    public void setFilterName(String name){
        filtername.setText(name);
    }
    public void setGenname(String gen){
        genname.setText(gen);
    }
    public void setLengthvalue(String length){
        lengthvalue.setText(length);
    }
    public void setGccontent(String gc){
        gccontent.setText(gc);
    }
    public void setTaxaid(String taxa){
        taxaid.setText(taxa);
    }
    public void setScorevalue(String score){
        scorevalue.setText(score);
    }
    public void setLengthCompareChoice(String choice){
        lengthCompareChoice.setValue(choice);
    }
    public void setGCCompareChoice(String choice){
        GCCompareChoice.setValue(choice);
    }
    public void setscoreCompareChoice(String choice){
        scoreCompareChoice.setValue(choice);
    }






    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lengthCompareChoice.getItems().addAll("<","=",">");
        scoreCompareChoice.getItems().addAll("<","=",">");
        GCCompareChoice.getItems().addAll("<","=",">");
    }
    //Checks if a String is a Int
    private boolean isDigit(String test) {

        boolean isDigit;
        try {
            Integer.parseInt(test);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }


    }

    private boolean isDouble(String test) {

        try {
            Double.parseDouble(test);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }


    }
}
