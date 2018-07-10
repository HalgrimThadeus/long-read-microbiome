package presenter;

import javafx.collections.ObservableList;
import model.Filter;
import model.FilterBuilder;


public class NewFilterPopUpPresenter {
    private ObservableList<Filter> listOfFilter;

    public NewFilterPopUpPresenter(ObservableList<Filter> listOfFilter){
        this.listOfFilter = listOfFilter;
    }

    public void updateFilterList(String name, FilterBuilder filterBuilder){
        Filter newFilter = new Filter(name, filterBuilder.getMainPredicate() );
            newFilter.setFilterBuilder(filterBuilder);
            boolean contained = false;
            for(int i = 0; i <  listOfFilter.size(); i++){
                if(listOfFilter.get(i).getName().equals(name)){
                    contained = true;
                    listOfFilter.set(i,newFilter);
                }
            }
            if(!contained) {
                listOfFilter.add(newFilter);
            }
    }


}
