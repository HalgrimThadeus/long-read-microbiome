package view;


import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.FilterBuilder;
import presenter.NewFilterPopUpPresenter;

public class NewFilterPopUp {

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
            FilterBuilder filter = new FilterBuilder();

            filterBuilder = filter;

            //Adds gen name predicate to the filter if a name is given
            if(!genname.getText().equals("")){
                filter.addMainPredicate(FilterBuilder.isGen(genname.getText()));
                filter.addKeyValue("Gen",genname.getText());
            }

            //adds gc content predicate to filter if a gc content is given
            if(!gccontent.getText().equals("")){
                //adds smallereq predicate if the textfield contains a <
                filter.addKeyValue("GC",gccontent.getText());
                if(gccontent.getText().contains("<")){
                    String gc_Content = gccontent.getText().substring(1);
                    if(isDouble(gc_Content)){
                        filter.addMainPredicate(FilterBuilder.isGCContentLowerEq(Double.parseDouble(gc_Content)));
                    }
                    //warning if input is not a number
                    else {
                        Alert alter = new Alert(Alert.AlertType.WARNING,"Please enter a Number as GC-content",ButtonType.OK);
                        alter.show();
                        return;
                    }
                }
                //adds greatereq to filter if the textfield contains a >
                if(gccontent.getText().contains(">")){
                    String gc_Content = gccontent.getText().substring(1);
                    if(isDouble(gc_Content)){
                        filter.addMainPredicate(FilterBuilder.isGCContentHigherEq(Double.parseDouble(gc_Content)));
                    }
                    else {
                        Alert alter = new Alert(Alert.AlertType.WARNING,"Please enter a Number as GC-content",ButtonType.OK);
                        alter.show();
                        return;
                    }
                }
                //adds equal predicate if nothing but the number is given
               if(isDigit(gccontent.getText())){
                   filter.addMainPredicate(FilterBuilder.isGCContentEqual(Double.parseDouble(gccontent.getText())));
               }
               else {
                   Alert alter = new Alert(Alert.AlertType.WARNING,"Please enter a Number as GC-content",ButtonType.OK);
                   alter.show();
                   return;
               }
            }
            if(!lengthvalue.getText().equals("")) {
                if (isDigit(lengthvalue.getText())) {
                    filter.addKeyValue("Length",lengthvalue.getText());
                    filter.addMainPredicate(FilterBuilder.isLengthEqual(Integer.parseInt(lengthvalue.getText())));
                } else {
                    Alert alter = new Alert(Alert.AlertType.WARNING, "Please enter a Number as length", ButtonType.OK);
                    alter.show();
                    return;
                }
            }
            if(!scorevalue.getText().equals("")){
                filter.addKeyValue("Score",scorevalue.getText());
                if(isDigit(scorevalue.getText())){
                        filter.addMainPredicate(FilterBuilder.isScoreEqual(Integer.parseInt(scorevalue.getText())));
                    }
                    else {
                        Alert alter = new Alert(Alert.AlertType.WARNING,"Please enter a Number as score",ButtonType.OK);
                        alter.show();
                        return;
                    }
            }
            if(!taxaid.getText().equals("")){
                filter.addKeyValue("Tax",taxaid.getText());
                if(isDigit(taxaid.getText())){
                    filter.addMainPredicate(FilterBuilder.isTaxaId(Integer.parseInt(taxaid.getText())));
                }
                //else case is filter by name (atm idk how)
            }

            newFilterPopUpPresenter.updateFilterList(filtername.getText(),filter);

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


}
