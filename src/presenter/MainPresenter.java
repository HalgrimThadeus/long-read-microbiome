package presenter;

import model.Project;
import view.MainView;

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
    //... more Presenter

    public MainPresenter(Project project, MainView mainView){
        this.mainView = mainView;
        this.project = project;

        this.newFilterPopUpPresenter = new NewFilterPopUpPresenter();
        this.newSamplePopUpPresenter = new NewSamplePopUpPresenter();
        this.filterPresenter = this.mainView.getFilterView().getFilterPresenter();
        //todo initiliaize model to presenter
        this.saveProjectPresenter = new SaveProjectPresenter();
        this.samplePresenter = this.mainView.getSampleView().getSamplePresenter();
        //todo initiliaize model to presenter
        this.workViewPresenter = new WorkViewPresenter();
        this.workViewContainerPresenter = this.mainView.getWorkViewContainer().getWorkViewContainerPresenter();

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

    //TODO add stuff, that all Controllers share
}
