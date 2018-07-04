package presenter;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.SplitPane;
import view.MainView;
import view.WorkView;
import view.WorkViewContainer;

import java.io.IOException;

/**
 * Contains all the WorkViews
 */

public class WorkViewContainerPresenter {

    private WorkViewContainer workViewContainer;

    public WorkViewContainerPresenter(WorkViewContainer workViewContainer) {
        this.workViewContainer = workViewContainer;
    }


    public void addNewMainTabView() throws IOException {
        WorkView newSampleTabPane;
        FXMLLoader loader = new FXMLLoader(MainView.class.getResource("workView.fxml"));
        //loader.setController(this.workViewContainer);

        newSampleTabPane = loader.load();

        ((SplitPane)(workViewContainer.getChildren().get(0))).getItems().add(newSampleTabPane);
    }

}
