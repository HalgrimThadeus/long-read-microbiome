package presenter;

import javafx.fxml.FXMLLoader;
import model.Project;
import view.WorkView;
import view.WorkViewContainer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains all the WorkViews
 */

public class WorkViewContainerPresenter {

    private WorkViewContainer workViewContainer;

    private Project project;

    private List<WorkView> workViews = new ArrayList<WorkView>();


    public WorkViewContainerPresenter(WorkViewContainer workViewContainer) {
        this.workViewContainer = workViewContainer;
    }

    public void initialize(Project project){
        this.project = project;
    }


    public void addNewMainTabView() throws IOException {
        WorkView newWorkView = new WorkView();
        WorkViewPresenter workViewPresenter = new WorkViewPresenter(this.project, newWorkView);
        FXMLLoader loader = new FXMLLoader(WorkView.class.getResource("workView.fxml"));
        loader.setController(newWorkView);

        newWorkView.setWorkViewPresenter(workViewPresenter);
        workViewContainer.getItems().add(loader.load());
    }

    public void removeMainTabView(){
        if(workViewContainer.getItems().size() > 0){
            workViewContainer.getItems().remove(workViewContainer.getItems().size()-1);
        }
    }



}
