package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import org.w3c.dom.html.HTMLAnchorElement;
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
    @FXML
    private SampleView sampleView;

    private MainPresenter mainPresenter;


    /**
     * TODO refactor method names by our convention
     */

    public SampleView getSampleView() {
        return (SampleView) sampleView;
    }

    public FilterView getFilterView() {
        return (FilterView) this.filterView;
    }

    @FXML
    public void toolbarBtnAddSamplePage(ActionEvent event) throws IOException {
        ((WorkViewContainer) workViewContainer).addNewMainTabView(mainSplitPain);
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
