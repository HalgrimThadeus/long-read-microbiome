package presenter;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.SplitPane;
import model.Project;
import view.MainView;
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



}
