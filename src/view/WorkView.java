package view;

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
import presenter.WorkViewContainerPresenter;
import presenter.WorkViewPresenter;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class WorkView extends TabPane implements Initializable {

    public WorkView workView;

    public AnchorPane readChartView;

    public HTMLEditor fastaEditor;

    private WorkViewPresenter workViewPresenter;

    private WorkViewContainerPresenter workViewContainerPresenter;


    public WorkView(){
        workViewPresenter = new WorkViewPresenter(this);
    }

    public void setWorkViewContainerPresenter(WorkViewContainerPresenter workViewContainerPresenter){
        this.workViewContainerPresenter = workViewContainerPresenter;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TODO make drag and drop work

        workView.setOnDragOver((DragEvent event) -> {
            event.acceptTransferModes(TransferMode.ANY);
            event.consume();
        });

        workView.setOnDragDropped((DragEvent event) -> {
            TitledPane source = (TitledPane) event.getGestureSource();
            String sampleName = source.getText();

            List<Object> sampleData = workViewContainerPresenter.addNewSampleToMainTabView(sampleName);

            List<Read> reads = (List<Read>)sampleData.get(0);
            String fastaFile = (String)sampleData.get(1);


            ((ChoiceBox)((ToolBar)this.readChartView.getChildren().get(0)).getItems().get(0)).getItems().addAll(reads);

            fastaEditor.setHtmlText(fastaFile);


            event.setDropCompleted(true);
            event.consume();
        });

    }


}
