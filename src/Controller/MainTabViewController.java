package Controller;

import View.MainView;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;

import java.io.IOException;

public class MainTabViewController {

    public void addNewMainTabView(SplitPane mainSplitPain) throws IOException {
        TabPane newSampleTabPane = FXMLLoader.load(MainView.class.getResource("tabPane.fxml"));
        mainSplitPain.getItems().add(newSampleTabPane);
    }
}
