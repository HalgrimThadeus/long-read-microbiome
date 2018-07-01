package View;

import Controller.FilterController;
import Controller.MainTabViewController;
import Controller.SaveProjectController;
import Model.FastAEntry;
import Model.IO.FastAIO;
import Model.Sample;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import Controller.SampleController;


public class MainView implements ProjectChangedListener {

    /**
     * Important Containers
     */
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
    public Button saveProjectBtn;

    /**
     * Menues
     */
    @FXML
    public MenuItem addNewFilterContextMenu;

    @FXML
    private Accordion sampleAccordion;

    /**
     *needed controller in this case
     */
    private MainTabViewController mainTabViewController = new MainTabViewController();
    private FilterController filterController = new FilterController();
    private SaveProjectController saveProjectController = new SaveProjectController();

    @FXML
    public void toolbarBtnAddSamplePage(ActionEvent event) throws IOException {
        mainTabViewController.addNewMainTabView(mainSplitPain);
    }

    @FXML
    public void addNewFilterContextMenuClicked(ActionEvent event) throws IOException {
        filterController.openNewFilterDialog();
    }

    @FXML
    public void saveProjectButtonClicked(ActionEvent clickEvent) throws Exception {
        try {
            saveProjectController.saveProject();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * Method to add multiple samples form a config (.lrcfg) File
     * @param clickEvent
     * @throws Exception
     */
    @FXML
    public void loadSamplesClicked(ActionEvent clickEvent) throws Exception {
        saveProjectController.readConfigFile();
    }

    //########################################################################################################################################
    //SAMPLE PANE PART
    //########################################################################################################################################

    @FXML
    public void newSampleBtnClicked(ActionEvent event) throws IOException {
        openSamplePane();
    }

    private void openSamplePane() throws IOException {
        NewSampleDialog newSampleDialog = new NewSampleDialog(this);

        Stage filterPopUp = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addNewSampleView.fxml"));

        loader.setController(newSampleDialog);
        Parent root = loader.load();

        filterPopUp.setTitle("New Sample");
        filterPopUp.setScene(new Scene(root, 600, 250));
        filterPopUp.show();
    }

    @Override
    public void sampleAdded(String sampleName, String fastaFileName, String gffFileName, List<String> readHeaders) {

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


        newAccordionPane.setOnDragDetected(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                /* drag was detected, start a drag-and-drop gesture*/
                /* allow any transfer mode */
                Dragboard db = newAccordionPane.startDragAndDrop(TransferMode.ANY);

                /* Put a string on a dragboard */
                ClipboardContent content = new ClipboardContent();
                content.putString(newAccordionPane.getText());
                db.setContent(content);

                event.consume();
            }
        });

        sampleAccordion.getPanes().add(newAccordionPane);
    }
}
