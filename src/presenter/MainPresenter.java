package presenter;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Project;
import view.ComparatorPopUp;
import view.MainView;
import view.SampleView;

import java.io.IOException;

public class MainPresenter {

    private Project project;
    private MainView mainView;

    private FilterPresenter filterPresenter;
    private SaveProjectPresenter saveProjectPresenter;
    private SamplePresenter samplePresenter;
    private WorkViewPresenter workViewPresenter;
    private WorkViewContainerPresenter workViewContainerPresenter;
    private NewSamplePopUpPresenter newSamplePopUpPresenter;
    private NewFilterPopUpPresenter newFilterPopUpPresenter;
    private ComparatorPopUpPresenter comparatorPopUpPresenter;
    //... more Presenter

    public MainPresenter(Project project, MainView mainView){
        this.mainView = mainView;
        this.project = project;

        this.newFilterPopUpPresenter = new NewFilterPopUpPresenter();

        this.newSamplePopUpPresenter = new NewSamplePopUpPresenter(project.getSamples());

        this.filterPresenter = this.mainView.getFilterView().getFilterPresenter();
        //todo initiliaize model to presenter

        this.saveProjectPresenter = new SaveProjectPresenter();

        this.samplePresenter = this.mainView.getSampleViewController().getSamplePresenter();
        this.samplePresenter.initialize(this.newSamplePopUpPresenter, project.getSamples());

        this.workViewPresenter = new WorkViewPresenter();

        this.workViewContainerPresenter = this.mainView.getWorkViewContainer().getWorkViewContainerPresenter();
        this.comparatorPopUpPresenter = new ComparatorPopUpPresenter();

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

    public WorkViewPresenter getWorkViewPresenter() {
        return workViewPresenter;
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

    public ComparatorPopUpPresenter getComparatorPopUpPresenter() {
        return comparatorPopUpPresenter;
    }

    public void openStartComperatorPopup() throws IOException {
        ComparatorPopUp comparatorPopUp = new ComparatorPopUp(comparatorPopUpPresenter);

        Stage comperatorPopUp = new Stage();
        FXMLLoader loader = new FXMLLoader(MainView.class.getResource("comparatorPopUp.fxml"));

        loader.setController(comparatorPopUp);
        Parent root = loader.load();

        comperatorPopUp.setTitle("New Comperator");
        comperatorPopUp.setScene(new Scene(root, 600, 250));
        comperatorPopUp.show();
    }

    //TODO add stuff, that all Controllers share
}
