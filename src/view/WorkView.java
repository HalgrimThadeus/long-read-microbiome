package view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TabPane;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToolBar;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.HTMLEditor;
import model.Read;
import presenter.WorkViewPresenter;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class WorkView extends TabPane implements Initializable {

    @FXML
    private WorkView workView;

    @FXML
    private AnchorPane readChartView;

    @FXML
    private HTMLEditor fastaTextShow;

    private WorkViewPresenter workViewPresenter;

    public void setWorkViewPresenter(WorkViewPresenter workViewPresenter) {
        this.workViewPresenter = workViewPresenter;
    }

    public void setTextTab(String fastaFileHtmlCode) {
        this.fastaTextShow.setHtmlText(fastaFileHtmlCode);
    }

    public void setChartTab(List<Read> reads) {
        ((ChoiceBox)((ToolBar)this.readChartView.getChildren().get(0)).getItems().get(0)).getItems().addAll(reads);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        //Set drop listeners for the pane
        workView.setOnDragOver((DragEvent event) -> {
            System.out.println("Drag is now  over the workview " + this);

            event.acceptTransferModes(TransferMode.ANY);
            event.consume();
        });

        workView.setOnDragDropped((DragEvent event) -> {
            TitledPane source = (TitledPane) event.getGestureSource();
            String sampleName = source.getText();

            workViewPresenter.setNewSampleToTabView(sampleName);
            System.out.println("ITem: "+ source.getText() + " dropped in " + this);
            event.setDropCompleted(true);
            event.consume();
        });
    }
}
