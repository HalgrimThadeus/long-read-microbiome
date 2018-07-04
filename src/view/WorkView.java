package view;

import javafx.fxml.Initializable;
import javafx.scene.control.TabPane;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class WorkView extends TabPane implements Initializable {

    public WorkView workView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TODO make drag and drop work

        workView.setOnDragOver((DragEvent event) -> {
            event.acceptTransferModes(TransferMode.ANY);
            event.consume();
        });

        workView.setOnDragDropped((DragEvent event) -> {
            TitledPane source = (TitledPane) event.getGestureSource();
            String sample = (String)((TreeView)(((AnchorPane)(source.getContent())).getChildren().get(0))).getRoot().getValue();
            System.out.println(sample);
            event.setDropCompleted(true);
            event.consume();
        });

    }
}
