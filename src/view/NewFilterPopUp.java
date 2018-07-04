package view;


import model.Filter;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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

    @FXML
    public void addtoListoffilters(){

        List<String> keys = new ArrayList<>();
        keys.add("Gen");
        keys.add("Length");
        keys.add("TaxaId");
        keys.add("GCContent");
        keys.add("Score");

        List<String> values = new ArrayList<>();
        values.add(genname.getText());
        values.add(lengthvalue.getText());
        values.add(taxaid.getText());
        values.add(gccontent.getText());
        values.add(scorevalue.getText());

        Filter newfilter = new Filter(filtername.getText(),keys,values);

        listoffilters.add(newfilter);


    }
}
