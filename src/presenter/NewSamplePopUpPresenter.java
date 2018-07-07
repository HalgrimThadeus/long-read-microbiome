package presenter;

import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import model.Project;
import model.Read;
import model.Sample;
import model.services.NewSampleService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class NewSamplePopUpPresenter {

    private ObservableList<Sample> samples;

    public NewSamplePopUpPresenter(ObservableList<Sample> samples) {
        this.samples = samples;
    }

    public void addSampleToProject(File fastaFile, File gffFile, File csvFile, String sampleName) {


        Service newSampleService = new NewSampleService(fastaFile, gffFile, csvFile);

        newSampleService.setOnSucceeded(event1 -> {
            Sample sample = ((NewSampleService)event1.getSource()).getValue();
            sample.setName(sampleName);
            samples.add(sample);
        });

        newSampleService.setOnFailed(event1 -> {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Loading of Sample FAILED", ButtonType.CANCEL);
            alert.show();
        });

        newSampleService.start();
    }
}
