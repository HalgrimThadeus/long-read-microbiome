package View;

import Controller.FilterController;
import Controller.MainTabViewController;
import Controller.SaveProjectController;
import Model.FastAEntry;
import Model.IO.FastAIO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import Controller.SampleController;


public class Presenter implements Initializable {

    /**
     * Important Containers
     */
    @FXML
    public Accordion sampleAccordion;

    @FXML
    public AnchorPane tab1;

    @FXML
    public SplitPane mainSplitPain;

    @FXML
    public ListView filterList;

    @FXML
    public HTMLEditor editor;


    /**
     * Important Buttons
     */
    @FXML
    public Button toolbarBtnAddSamplePage;

    @FXML
    public Button newSampleBtn;

    @FXML
    public Button saveProjectBtn;

    /**
     * Menues
     */
    @FXML
    public MenuItem addNewFilterContextMenu;


    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    /**
     * You can open a fastaFile at the Monoment and an item is added to the Accordion TODO Sample pop up???
     * @param event
     * @throws IOException
     */
    @FXML
    public void newSampleBtnClicked(ActionEvent event) throws IOException {

        SampleController controller = new SampleController();

        controller.addNewSampleInAccordion(sampleAccordion);

        File selectedFile = controller.getNewFiles();
        editor.setHtmlText(controller.readInFastaFileInHtml(selectedFile));
    }

    @FXML
    public void toolbarBtnAddSamplePage(ActionEvent event) throws IOException {
        MainTabViewController controller = new MainTabViewController();
        controller.addNewMainTabView(mainSplitPain);
    }

    @FXML
    public void addNewFilterContextMenuClicked(ActionEvent event) throws IOException {
        FilterController controller = new FilterController();

        controller.openNewFilterDialog();
    }

    @FXML
    public void saveProjectButtonClicked(ActionEvent clickEvent) throws Exception {
        SaveProjectController controller = new SaveProjectController();

        try {
            controller.saveProject();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
