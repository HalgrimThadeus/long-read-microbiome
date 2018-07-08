package view;

import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import presenter.WorkViewContainerPresenter;

import java.io.IOException;

public class WorkViewContainer extends AnchorPane {

    private WorkViewContainerPresenter workViewContainerPresenter;

    public WorkViewContainer() {
        this.workViewContainerPresenter = new WorkViewContainerPresenter(this);
    }

    public WorkViewContainerPresenter getWorkViewContainerPresenter() {
        return workViewContainerPresenter;
    }

    public void onNewMainTabViewButtonClicked() throws IOException {
        WorkView newSampleTabPane = workViewContainerPresenter.addNewMainTabView();
        ((SplitPane)(this.getChildren().get(0))).getItems().add(newSampleTabPane);
    }
}
