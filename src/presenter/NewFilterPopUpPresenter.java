package presenter;

import javafx.collections.ObservableList;
import model.Filter;

import java.security.Provider;
import java.util.List;


public class NewFilterPopUpPresenter {
    private ObservableList<Filter> listOfFilter;

    public NewFilterPopUpPresenter(ObservableList<Filter> listOfFilter){
        this.listOfFilter = listOfFilter;
    }

    public void updateFilterList(String name,List<String> keys, List<String> values){

        Filter newFilter = new Filter(name,keys,values);

        listOfFilter.add(newFilter);

    }




}
