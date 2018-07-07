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
     *
     */
    @FXML
    private WorkViewContainer workViewContainer;
    @FXML
    private FilterView filterView;

    /**
     * this is the SampleView, which is in this case a fx:controller
     */
    @FXML
    private SampleView sampleViewController;

    private MainPresenter mainPresenter;

    public void setMainPresenter(MainPresenter mainPresenter) {
        this.mainPresenter = mainPresenter;
    }

    /**
     * TODO refactor method names by our convention
     */

    public SampleView getSampleViewController() {
        return sampleViewController;
    }

    public FilterView getFilterView() {
        return this.filterView;
    }

    public WorkViewContainer getWorkViewContainer() {
        return workViewContainer;
    }

    @FXML
    public void toolbarBtnAddSamplePage(ActionEvent event) throws IOException {
        ((WorkViewContainer) workViewContainer).addNewMainTabView();
    }

    @FXML
    public void addNewFilterContextMenuClicked(ActionEvent event) throws IOException {
        mainPresenter.getNewFilterPopUpPresenter().openNewFilterDialog();
    }

    @FXML
    public void saveProjectButtonClicked(ActionEvent clickEvent) throws Exception {
        try {
            mainPresenter.getSaveProjectPresenter().saveProject();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void onStartComperatorButtonClicked(ActionEvent clickEvent) throws Exception{
        try {
            mainPresenter.openStartComperatorPopup();
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
    public void loadSamplesClicked(ActionEvent clickEvent) throws Exception {
        mainPresenter.getSaveProjectPresenter().readConfigFile();
    }
}