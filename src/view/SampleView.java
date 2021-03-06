package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import presenter.SamplePresenter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SampleView extends AnchorPane {


    @FXML
    private Accordion sampleAccordion;

    private SamplePresenter samplePresenter;

    public SampleView() {
        this.samplePresenter = new SamplePresenter(this);
    }

    @FXML
    public void newSampleBtnClicked(ActionEvent event) throws IOException {
        samplePresenter.openSamplePane();
    }

    public TitledPane addSampleAccordionPane(String sampleName, String fastaFileName, String gffFileName, List<String> readHeaders) {

        //adds the sample information to the accordion pane
        TitledPane newAccordionPane = new TitledPane();
        AnchorPane newAnchorPane = new AnchorPane();
        TreeView newTreeView = new TreeView();

        TreeItem<String> root = new TreeItem<>(sampleName);
        root.setExpanded(true);

        TreeItem<String> fastaChild = new TreeItem<>(fastaFileName);
        fastaChild.setExpanded(false);

        TreeItem<String> gffChild = new TreeItem<>(gffFileName);
        gffChild.setExpanded(false);

        for (String readName: readHeaders) {
            TreeItem<String> readChild = new TreeItem<>(readName);
            readChild.setExpanded(false);
            fastaChild.getChildren().add(readChild);
        }

        root.getChildren().add(fastaChild);
        root.getChildren().add(gffChild);
        newTreeView.setRoot(root);
        newTreeView.setShowRoot(false);


        AnchorPane.setLeftAnchor(newTreeView, 0d);
        AnchorPane.setRightAnchor(newTreeView, 0d);
        AnchorPane.setBottomAnchor(newTreeView, 0d);
        AnchorPane.setTopAnchor(newTreeView, 0d);
        newAnchorPane.getChildren().add(newTreeView);

        newTreeView.setMaxHeight(100);
        newAccordionPane.setMaxHeight(100);
        newTreeView.setMinHeight(100);
        newAccordionPane.setMinHeight(100);
        newAccordionPane.setText(sampleName);
        newAccordionPane.setContent(newAnchorPane);

        sampleAccordion.getPanes().add(newAccordionPane);

        return newAccordionPane;
    }

    public void removeSampleAccordionPane(TitledPane sampleAccordionPane) {
        sampleAccordion.getPanes().remove(sampleAccordionPane);
    }

    public SamplePresenter getSamplePresenter() {
        return samplePresenter;
    }
}
