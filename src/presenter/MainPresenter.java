package presenter;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Project;
import view.ComparatorPopUp;
import view.MainView;

import java.io.IOException;

public class MainPresenter {

    private Project project;
    private MainView mainView;

    private FilterPresenter filterPresenter;
    private SaveProjectPresenter saveProjectPresenter;
    private SamplePresenter samplePresenter;
    private WorkViewContainerPresenter workViewContainerPresenter;
    private NewSamplePopUpPresenter newSamplePopUpPresenter;
    private NewFilterPopUpPresenter newFilterPopUpPresenter;
    //... more Presenter

    public MainPresenter(Project project, MainView mainView){
        this.mainView = mainView;
        this.project = project;

        //loading of the tax tree
        this.project.getTreeLoadingStatus().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue.intValue() == Project.LOADING) {
                        this.mainView.showLoadingBarinStatusBar("Loading taxonomic tree");
                    } else if(newValue.intValue() == Project.LOADED) {
                        this.mainView.showMessageInStatusBar("Taxonomic tree loaded");
                    } else if(newValue.intValue() == Project.LOADING_FAILED) {
                        this.mainView.showLoadingBarinStatusBar("Loading of taxonomic tree failed. Name filtering is not usable!");
                    }
                }
        );
        this.project.loadTaxTree();

        this.newFilterPopUpPresenter = new NewFilterPopUpPresenter(project);

        this.newSamplePopUpPresenter = new NewSamplePopUpPresenter(project.getSamples());

        this.filterPresenter = this.mainView.getFilterView().getFilterPresenter();
        this.filterPresenter.initialize(this.newFilterPopUpPresenter, project);

        this.saveProjectPresenter = new SaveProjectPresenter(project);

        this.samplePresenter = this.mainView.getSampleView().getSamplePresenter();
        this.samplePresenter.initialize(this.newSamplePopUpPresenter, project.getSamples());

        this.workViewContainerPresenter = this.mainView.getWorkViewContainer().getWorkViewContainerPresenter();
        workViewContainerPresenter.initialize(project);

    }


    public FilterPresenter getFilterPresenter() {
        return filterPresenter;
    }

    public SaveProjectPresenter getSaveProjectPresenter() {
        return saveProjectPresenter;
    }

    public SamplePresenter getSamplePresenter() {
        return samplePresenter;
    }

    public WorkViewContainerPresenter getWorkViewContainerPresenter() {
        return workViewContainerPresenter;
    }

    public NewSamplePopUpPresenter getNewSamplePopUpPresenter() {
        return newSamplePopUpPresenter;
    }

    public NewFilterPopUpPresenter getNewFilterPopUpPresenter() {
        return newFilterPopUpPresenter;
    }

    public void openStartComparatorPopup() throws IOException {

        ComparatorPopUp cp = new ComparatorPopUp();
        Stage comparatorPopUp = new Stage();
        FXMLLoader loader = new FXMLLoader(MainView.class.getResource("comparatorPopUp.fxml"));
        loader.setController(cp);
        Parent root = loader.load();

        comparatorPopUp.setTitle("New Comparator");
        comparatorPopUp.setScene(new Scene(root, 450, 300));

        comparatorPopUp.show();
        ComparatorPopUpPresenter comparatorPopUpPresenter = new ComparatorPopUpPresenter(project.getSamples(), project.getFilters(), cp);
        cp.setComparatorPopUpPresenter(comparatorPopUpPresenter);

    }

}
