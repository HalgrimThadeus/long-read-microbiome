package presenter;

import javafx.collections.ObservableList;
import model.Filter;
import model.Read;

import java.util.function.Predicate;


public class NewFilterPopUpPresenter {
    private ObservableList<Filter> listOfFilter;

    public NewFilterPopUpPresenter(ObservableList<Filter> listOfFilter){
        this.listOfFilter = listOfFilter;
    }

    public void updateFilterList(String name, Predicate<Read> filterPredicate){

        Filter newFilter = new Filter(name, filterPredicate );

        listOfFilter.add(newFilter);

    }




}
