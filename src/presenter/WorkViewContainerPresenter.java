package presenter;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import model.Sample;
import view.MainView;
import view.WorkView;
import view.WorkViewContainer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains all the WorkViews
 */

public class WorkViewContainerPresenter {

    private WorkViewContainer workViewContainer;

    private ObservableList<Sample> samples;

    private List<WorkView> workViewController = new ArrayList<WorkView>();


    public WorkViewContainerPresenter(WorkViewContainer workViewContainer) {
        this.workViewContainer = workViewContainer;
    }

    public void initialize( ObservableList<Sample> samples){
        this.samples = samples;
    }


    public WorkView addNewMainTabView() throws IOException {
        WorkView newSampleTabPane;
        FXMLLoader loader = new FXMLLoader(MainView.class.getResource("workView.fxml"));

        newSampleTabPane = loader.load();

        workViewController.add(loader.getController());
        ((WorkView) loader.getController()).setWorkViewContainerPresenter(this);

        return newSampleTabPane;
    }

    public List<Object> addNewSampleToMainTabView(String name){
        List<Object> sampleData = new ArrayList<>();
        Sample sample = null;
        for(int i = 0; i < samples.size(); i++){
            if(samples.get(i).getName().equals(name)){
                sample = samples.get(i);
            }
        }

        sampleData.add(sample.getReads());
        sampleData.add(getFastaFileHtmlCode(sample));

        return sampleData;
    }

    private String getFastaFileHtmlCode(Sample sample){

        StringBuilder builder = new StringBuilder();

        for(int i = 0; i < sample.getReads().size(); i++){
            builder.append(sample.getReads().get(i).getHeader());
            builder.append( "<br/>");
            builder.append(sample.getReads().get(i).getSequence());
            builder.append("<br/>");
        }

        return builder.toString();
    }

}
