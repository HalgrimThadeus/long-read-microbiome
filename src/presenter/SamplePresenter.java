package presenter;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Sample;
import view.MainView;
import view.NewSamplePopUp;
import view.SampleView;

import java.io.IOException;
import java.util.List;

public class SamplePresenter {

    private NewSamplePopUpPresenter newSamplePopUpPresenter = null;
    private SampleView sampleView;
    private ObservableList<Sample> samples;
    private boolean initialized = false;


    public SamplePresenter(SampleView sampleView) {
        this.sampleView = sampleView;
    }

    /**
     * sets up the whole sample presenter, so that it can work properly
     * for that the newSamplePopUppresenter must be given, so that new samples could be created out of this presenter
     * @param newSamplePopUpPresenter
     */
    public void initialize(NewSamplePopUpPresenter newSamplePopUpPresenter, ObservableList<Sample> samples) {
        this.newSamplePopUpPresenter = newSamplePopUpPresenter;
        this.samples = samples;

        samples.addListener((ListChangeListener<Sample>) change -> {
            change.next();
            List<Sample> addedSamples = (List<Sample>) change.getAddedSubList();

            for (Sample s:addedSamples) {
                sampleView.sampleAdded(s.getName(), s.getFastaFileName(), s.getGffFileName(), s.getReadNames());
            }
        });
    }

    public boolean isInitialized() {
        return !(newSamplePopUpPresenter == null);
    }

    /**
     * creates the popup to create new Sample
     * @throws IOException
     */
    public void openSamplePane() throws IOException {
        if (isInitialized()) {
            NewSamplePopUp newSamplePopUp = new NewSamplePopUp(newSamplePopUpPresenter);

            Stage samplePopUp = new Stage();
            FXMLLoader loader = new FXMLLoader(MainView.class.getResource("newSamplePopUp.fxml"));

            loader.setController(newSamplePopUp);
            Parent root = loader.load();

            samplePopUp.setTitle("New Sample");
            samplePopUp.setScene(new Scene(root, 600, 250));
            samplePopUp.show();
        } else {
            throw new ExceptionInInitializerError("Could not start NewSamplePopUp, because the NewSamplePopUpPresenter never was initialized in SamplePresenter.");
        }
    }

}
