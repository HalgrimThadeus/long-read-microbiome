package presenter;

import model.Filter;
import model.Project;

import java.util.List;



public class NewFilterPopUpPresenter {

    private Project project;

    public NewFilterPopUpPresenter(Project project){
        this.project = project;
    }

    public void updateFilterList(String name, List<String> keys,List<String> values,List<String> compare){
        this.project.addOrSetFilter(new Filter(name,keys,values,compare));
    }


}
