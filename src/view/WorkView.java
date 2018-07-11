package view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TabPane;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.HTMLEditor;
import presenter.WorkViewPresenter;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WorkView extends TabPane implements Initializable {

    public static DataFormat FILTER = new DataFormat("Filter");
    public static DataFormat SAMPLE = new DataFormat("Sample");

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        //Set drop listeners for the pane
        workView.setOnDragOver((DragEvent event) -> {
            System.out.println("Drag is now  over the workview " + this);

            event.acceptTransferModes(TransferMode.ANY);
            event.consume();
        });

        workView.setOnDragDropped((DragEvent event) -> {
            String filterName = (String) event.getDragboard().getContent(FILTER);
            String sampleName = (String) event.getDragboard().getContent(SAMPLE);

            if(filterName != null) {
                workViewPresenter.setNewFilterToWorkView(filterName);
                System.out.println("Filter-Item: "+ filterName + " dropped in " + this);
            } else if(sampleName != null) {
                workViewPresenter.setNewSampleToWorkView(sampleName);
                System.out.println("Sample-Item: "+ sampleName + " dropped in " + this);
            }

            event.setDropCompleted(true);
            event.consume();
        });

        try {
            workViewPresenter.addReadChartView(workView.getTabs().get(0));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
