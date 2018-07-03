package Controller;

import View.View;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;

import java.io.IOException;

public class MainTabViewController extends Controller{

    public void addNewMainTabView(SplitPane mainSplitPain) throws IOException {
        TabPane newSampleTabPane = FXMLLoader.load(View.class.getResource("tabPaneView.fxml"));
        mainSplitPain.getItems().add(newSampleTabPane);
    }
}
