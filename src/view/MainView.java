package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import presenter.MainPresenter;

import java.io.File;
import java.io.IOException;

public class MainView {

    /**
     * Important Containers
     */

    @FXML
    private SplitPane mainSplitPain;

    /**
     * Important Buttons
     */
    @FXML
    private Button toolbarBtnAddSamplePage;

    /**
     * Menues
     */
    @FXML
    private MenuItem addNewFilterContextMenu;


    /**
     * with these attributes you could access the fxcontroller of the subviews and the subview itself
     * !!!DONT CHANGE THE ATTRIBUTES NAMES!!! (they get automatically bound by fxml)
     */

    //TODO Which this bindings the workViewContainer is somehow empty so i changed this.
    @FXML
    private WorkViewContainer workViewContainer;
    @FXML
    private FilterView filterViewController;
    @FXML
    private SampleView sampleViewController;

    private MainPresenter mainPresenter;

    public void setMainPresenter(MainPresenter mainPresenter) {
        this.mainPresenter = mainPresenter;
    }


    public SampleView getSampleView() {
        return sampleViewController;
    }

    public FilterView getFilterView() {
        return this.filterViewController;
    }

    public WorkViewContainer getWorkViewContainer() {
        return workViewContainer;
    }

    @FXML
    public void onAddSamplePageToolbarButtonClicked(ActionEvent event) throws IOException {
        ((WorkViewContainer) workViewContainer).onNewMainTabViewButtonClicked();
    }

    @FXML
    public void onAddNewFilterContextMenuClicked(ActionEvent event) throws IOException {
        mainPresenter.getFilterPresenter().openNewFilterDialog();
    }


    @FXML
    public void onStartComperatorButtonClicked(ActionEvent clickEvent) throws Exception {
        try {
            mainPresenter.openStartComparatorPopup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Save and Load Project----------;
    @FXML
    public void onSaveProjectMenuItemClicked(ActionEvent clickEvent) throws Exception {
        try {
            mainPresenter.getSaveProjectPresenter().saveProject();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }




    @FXML
    public void onLoadProjectMenuItemClicked(ActionEvent clickEvent) throws Exception {
        //Choose File here:


        FileChooser fileChooser = new FileChooser();

        fileChooser.setInitialDirectory(new File
                (System.getProperty("user.home") ));
        fileChooser.setTitle("Open Project File");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Project", "*.project"));
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile == null) {
            return;
        }


        try {
            mainPresenter.getSaveProjectPresenter().loadProject(selectedFile);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    //-----------------------------;

    public void onCloseMenuItemClicked(ActionEvent e) {
        System.exit(0);
    }
}
