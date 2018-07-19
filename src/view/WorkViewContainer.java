package view;

import javafx.scene.control.SplitPane;
import presenter.WorkViewContainerPresenter;

import java.io.IOException;

public class WorkViewContainer extends SplitPane {

    private WorkViewContainerPresenter workViewContainerPresenter;

    public WorkViewContainer() {
        this.workViewContainerPresenter = new WorkViewContainerPresenter(this);
    }

    public WorkViewContainerPresenter getWorkViewContainerPresenter() {
        return workViewContainerPresenter;
    }

    public void onNewMainTabViewButtonClicked() throws IOException {
        workViewContainerPresenter.addNewMainTabView();
    }

    public void onRemoveMainTabViewButtonClicked(){
        workViewContainerPresenter.removeMainTabView();
    }
}
