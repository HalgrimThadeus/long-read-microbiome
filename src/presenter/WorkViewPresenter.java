package presenter;


import javafx.beans.InvalidationListener;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ObjectPropertyBase;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ToolBar;
import model.FilteredSample;
import model.Project;
import model.Sample;
import view.WorkView;

import java.util.ArrayList;
import java.util.List;

public class WorkViewPresenter {

    private Project project;
    private WorkView workView;
    private FilteredSample filteredSample;

    public WorkViewPresenter(Project project, WorkView workView){
        this.project = project;
        this.workView = workView;

        this.filteredSample = new FilteredSample();
        this.filteredSample.getSample().addListener((observable, oldValue, newValue) -> {
            this.workView.setTextTab(getFastaFileHtmlCode(newValue));
            this.workView.setChartTab(newValue.getReads());
        });
    }


    public void setNewSampleToTabView(String sampleName) {
        Sample sample4Presenting = null;

        for (Sample sample: project.getSamples()) {
            if(sample.getName().equals(sampleName))
                sample4Presenting = sample;
        }

        this.filteredSample.setSample(sample4Presenting);
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
