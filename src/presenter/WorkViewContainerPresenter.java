package presenter;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import view.MainView;

import java.io.IOException;
import java.util.List;

/**
 * Contains all the WorkViews
 */

public class WorkViewContainerPresenter {

    public void addNewMainTabView(SplitPane mainSplitPain) throws IOException {
        TabPane newSampleTabPane = FXMLLoader.load(MainView.class.getResource("workView.fxml"));
        mainSplitPain.getItems().add(newSampleTabPane);
    }

}
