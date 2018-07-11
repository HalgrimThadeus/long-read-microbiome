package view;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.FilterBuilder;
import presenter.NewFilterPopUpPresenter;

import java.net.URL;
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

        if(!filtername.getText().equals("")){

            /**Builds the filterBuilder with the predicates needed to fit the criterias e.g "ands" one of the existing
             * predicates in FilterBuilder (model) to it's main PredicateField, with the user-given filter input
             * **/

            //Adds gen name predicate to the filter if a name is given
            if(!genname.getText().equals("")){
                buildPredicates("Gen",genname.getText());
                filterBuilder.addKeyValue("Gen",genname.getText());

            }

            //adds gc content predicate to filter if a gc content is given
            if(!gccontent.getText().equals("")){
                //adds smallereq predicate if the textfield contains a <
                filterBuilder.addKeyValue("GC",gccontent.getText());
                buildPredicates("GC",gccontent.getText());
            }
            if(!lengthvalue.getText().equals("")) {
                filterBuilder.addKeyValue("Length",lengthvalue.getText());
                buildPredicates("Length",lengthvalue.getText());
            }
            if(!scorevalue.getText().equals("")){
                buildPredicates("Score",scorevalue.getText());
                filterBuilder.addKeyValue("Score",scorevalue.getText());
            }
            if(!taxaid.getText().equals("")){
               buildPredicates("Taxa",taxaid.getText());
               filterBuilder.addKeyValue("Taxa",taxaid.getText());
            }

            newFilterPopUpPresenter.updateFilterList(filtername.getText(),filterBuilder);

            stage.close();
        }
        else {
            Alert alter = new Alert(Alert.AlertType.WARNING,"Please set a Name",ButtonType.OK);
            alter.show();
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


 //Checks if a String is a Int
    private boolean isDigit(String test){

        boolean isDigit;
        try{
            Integer.parseInt(test);
            return true;
        } catch (NumberFormatException e){
            return false;
        }



    }
    private boolean isDouble(String test){

        try{
            Double.parseDouble(test);
            return true;
        } catch (NumberFormatException e){
            return false;
        }



    }

    /**
     * Uses key and value to add a predicate to the filterBuilder
     * @param key
     * @param value
     */

    public void buildPredicates(String key,String value){

        if(key.equals("Gen")){
            filterBuilder.addMainPredicate(FilterBuilder.isGen(value));
        }
        else if(key.equals("GC")) {
            if (isDouble(gccontent.getText())) {
                if (GCCompareChoice.getSelectionModel().getSelectedItem().equals("<")) {
                    filterBuilder.addMainPredicate(FilterBuilder.isGCContentLowerEq(Double.parseDouble(value)));
                }
                //adds greatereq to filter if the textfield contains a >
                else if (GCCompareChoice.getSelectionModel().getSelectedItem().equals(">")) {
                    filterBuilder.addMainPredicate(FilterBuilder.isGCContentHigherEq(Double.parseDouble(value)));
                }
            }
            //adds equal predicate if nothing but the number is given
            else if (GCCompareChoice.getSelectionModel().getSelectedItem().equals("=")) {
                filterBuilder.addMainPredicate(FilterBuilder.isGCContentEqual(Double.parseDouble(value)));
            }
        else {
                Alert alter = new Alert(Alert.AlertType.WARNING, "Please enter a Number as GC-content", ButtonType.OK);
                alter.show();
                return;
            }
        }
        else if(key.equals("Length")){
            if (isDigit(lengthvalue.getText())) {
                if (lengthCompareChoice.getSelectionModel().getSelectedItem().equals("="))
                    filterBuilder.addKeyValue("Length", lengthvalue.getText());
                filterBuilder.addMainPredicate(FilterBuilder.isLengthEqual(Integer.parseInt(value)));
            }
            else if (lengthCompareChoice.getSelectionModel().getSelectedItem().equals(">")){
                filterBuilder.addKeyValue("Length", ">"+lengthvalue.getText());
                filterBuilder.addMainPredicate(FilterBuilder.isLengthGreater(Integer.parseInt(value)));
            }
            else if(lengthCompareChoice.getSelectionModel().getSelectedItem().equals("<")){
                filterBuilder.addKeyValue("Length", "<"+lengthvalue.getText());
                filterBuilder.addMainPredicate(FilterBuilder.isLengthSmaller(Integer.parseInt(value)));
            }
        }
        else if(key.equals("Score")){
            if(isDigit(scorevalue.getText())) {
                if (scoreCompareChoice.getSelectionModel().getSelectedItem().equals("=")) {
                    filterBuilder.addKeyValue("Score",scorevalue.getText());
                    filterBuilder.addMainPredicate(FilterBuilder.isScoreEqual(Integer.parseInt(value)));
                }
                else if (scoreCompareChoice.getSelectionModel().getSelectedItem().equals("<")){
                    filterBuilder.addMainPredicate(FilterBuilder.isScoreLower(Integer.parseInt(value)));
                }
                else if (scoreCompareChoice.getSelectionModel().getSelectedItem().equals(">")){
                    filterBuilder.addMainPredicate(FilterBuilder.isScoreHigher(Integer.parseInt(value)));
                }
            }
            else {
                Alert alter = new Alert(Alert.AlertType.WARNING,"Please enter a Number as score",ButtonType.OK);
                alter.show();
                return;
            }
        }
        else if(key.equals("Taxa")){
            filterBuilder.addKeyValue("Tax",value);
            if(isDigit(taxaid.getText())){
                filterBuilder.addMainPredicate(FilterBuilder.isTaxaId(Integer.parseInt(value)));
            }
            //else case is filter by name (atm idk how)
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lengthCompareChoice.getItems().addAll("<","=",">");
        scoreCompareChoice.getItems().addAll("<","=",">");
        GCCompareChoice.getItems().addAll("<","=",">");
    }
}
