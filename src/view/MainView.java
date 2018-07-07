package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import presenter.MainPresenter;

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
     *  with these attributes you could access the fxcontroller of the subviews and the subview itself
     *  !!!DONT CHANGE THE ATTRIBUTES NAMES!!! (they get automatically bound by fxml)
     */
    @FXML
    private WorkViewContainer workViewContainerController;
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
        return workViewContainerController;
    }

    @FXML
    public void onAddSamplePageToolbarButtonClicked(ActionEvent event) throws IOException {
        ((WorkViewContainer) workViewContainerController).addNewMainTabView();
    }

    @FXML
    public void onAddNewFilterContextMenuClicked(ActionEvent event) throws IOException {
        mainPresenter.getNewFilterPopUpPresenter().openNewFilterDialog();
    }

    @FXML
    public void onSaveProjectButtonClicked(ActionEvent clickEvent) throws Exception {
        try {
            mainPresenter.getSaveProjectPresenter().saveProject();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void onStartComperatorButtonClicked(ActionEvent clickEvent) throws Exception{
        try {
            mainPresenter.openStartComparatorPopup();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Method to add multiple samples form a config (.lrcfg) File
     * @param clickEvent
     * @throws Exception
     */
    @FXML
    public void onLoadSamplesContextMenuClicked(ActionEvent clickEvent) throws Exception {
        mainPresenter.getSaveProjectPresenter().readConfigFile();
    }
}
