package presenter;

import javafx.collections.ObservableList;
import model.Filter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class NewFilterPopUpPresenter {
    private ObservableList<Filter> listOfFilter;
    private Map<String,Filter> filterMap = new HashMap<>();
    public NewFilterPopUpPresenter(ObservableList<Filter> listOfFilter){
        this.listOfFilter = listOfFilter;
    }

    public void updateFilterList(String name, List<String> keys,List<String> values,List<String> compare){
        Filter newFilter = new Filter(name,keys,values,compare);
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
