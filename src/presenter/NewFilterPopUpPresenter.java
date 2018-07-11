package presenter;

import javafx.collections.ObservableList;
import model.Filter;
import model.Project;

import java.util.List;



public class NewFilterPopUpPresenter {
    private ObservableList<Filter> listOfFilter;
    private Project project;
    public NewFilterPopUpPresenter(Project project){
        this.listOfFilter = project.getFilters();
        this.project = project;
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
                project.getFilters().add(newFilter);
            }
    }


}
