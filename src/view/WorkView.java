package view;

import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.HTMLEditor;
import model.Sample;
import presenter.WorkViewPresenter;

import java.net.URL;
import java.util.ResourceBundle;

public class WorkView extends TabPane implements Initializable {

    public WorkView workView;

    public AnchorPane readChartView;

    public HTMLEditor fastaEditor;

    private WorkViewPresenter workViewPresenter;


    public WorkView(){
        workViewPresenter = new WorkViewPresenter(this);
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

            Sample addedSample = workViewPresenter.addNewSampleToMainPain(sampleName);
            ((ChoiceBox)((ToolBar)this.readChartView.getChildren().get(0)).getItems().get(0)).getItems().addAll(addedSample.getReads());

            fastaEditor.setHtmlText(getFastaFileHtmlCode(addedSample));


            event.setDropCompleted(true);
            event.consume();
        });

    }

    private String getFastaFileHtmlCode(Sample sample){
        String fastaFile = "";

        for(int i = 0; i < sample.getReads().size(); i++){
            fastaFile += sample.getReads().get(i).getHeader() + "<br/>" + sample.getReads().get(i).getSequence() + "<br/>";
        }

        return fastaFile;
    }
}
