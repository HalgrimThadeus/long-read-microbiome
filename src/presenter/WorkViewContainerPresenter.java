package presenter;

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


    public WorkView addNewMainTabView() throws IOException {
        WorkView newSampleTabPane = new WorkView();

        FXMLLoader loader = new FXMLLoader(MainView.class.getResource("workView.fxml"));
        loader.setController(newSampleTabPane);
        //TODO HOW CHANGE this , so that workviews are presented in splited panes???
        //loader.load();
        //TODO Just for showing
        ((SplitPane)(workViewContainer.getChildren().get(0))).getItems().add(loader.load());

        //initiliazie new sampletabpane with null so nothing to see...
        WorkViewPresenter workViewPresenter = new WorkViewPresenter(this.project, newSampleTabPane);
        newSampleTabPane.setWorkViewPresenter(workViewPresenter);

        return newSampleTabPane;
    }



}
