package presenter;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import model.*;
import view.ReadChartView;
import view.WorkView;

import java.io.IOException;
import java.util.List;


public class WorkViewPresenter {

    private Project project;
    private WorkView workView;
    private FilteredSample filteredSample;

    public WorkViewPresenter(Project project, WorkView workView){
        this.project = project;
        this.workView = workView;

        this.filteredSample = new FilteredSample();

        this.filteredSample.getFilteredReads().addListener((ListChangeListener<? super Read>) change -> {
            while(change.next()) {

                if(change.wasAdded()) {
                    this.workView.setProjectComments(this.project.toString());
                }
            }
            });
    }

    /**
     * Is called by workView, because the Tab has to be initialized before adding something.
     * @param readChartTab
     * @throws IOException
     */
    public void addReadChartView(Tab readChartTab) throws IOException {
        ReadChartView readChartView = new ReadChartView();
        ReadChartViewPresenter readChartViewPresenter = new ReadChartViewPresenter(this.project, this.filteredSample, readChartView);
        FXMLLoader loader = new FXMLLoader(WorkView.class.getResource("readChartView.fxml"));
        loader.setController(readChartView);

        readChartView.setReadChartViewPresenter(readChartViewPresenter);
        readChartTab.setContent(loader.load());
    }


    public void setNewSampleToWorkView(String sampleName) {
        Sample sample4Presenting = this.project.getSampleByName(sampleName);

        this.filteredSample.setSample(sample4Presenting);
    }

    public void addNewFilterToWorkView(String filterName) {
        Filter filter4Applying = this.project.getFilterByName(filterName);

        //adds filter to filtered sample AND concats the new combined filter directly to the list of filters in prpject
        this.project.addOrSetFilter(this.filteredSample.addFilter(filter4Applying));
    }

    private String getFastaFileHtmlCode(List<Read> reads){

        StringBuilder builder = new StringBuilder();

        for (Read r: reads) {
            builder.append(r.getHeader());
            builder.append( "<br/>");
        }

        return builder.toString();
    }
}
