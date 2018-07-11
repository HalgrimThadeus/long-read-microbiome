package presenter;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TitledPane;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.stage.Stage;
import model.Sample;
import view.MainView;
import view.NewSamplePopUp;
import view.SampleView;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SamplePresenter {

    private NewSamplePopUpPresenter newSamplePopUpPresenter = null;
    private SampleView sampleView;
    private ObservableList<Sample> samples;
    private Map<Sample,TitledPane> sample2View;

    public SamplePresenter(SampleView sampleView) {
        this.sampleView = sampleView;
        this.sample2View = new HashMap<>();
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
            while(change.next()) {
                if(change.wasAdded()) {
                    List<Sample> addedSamples = (List<Sample>) change.getAddedSubList();

                    for (Sample sample:addedSamples) {
                        this.addSample(sample);
                    }
                } else if(change.wasRemoved()) {
                    List<Sample> removedSamples = (List<Sample>) change.getRemoved();

                    for (Sample sample:removedSamples) {
                        this.removeSample(sample);
                    }
                }

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

    private void addSample(Sample sample) {
        TitledPane newSampleAccordionPane = sampleView.addSampleAccordionPane(sample.getName(),
                sample.getFastaFileName(), sample.getGffFileName(), sample.getReadNames());

        newSampleAccordionPane.setOnDragDetected(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                /* drag was detected, start a drag-and-drop gesture*/
                /* allow any transfer mode */
                Dragboard db = newSampleAccordionPane.startDragAndDrop(TransferMode.ANY);

                /* Put a string on a dragboard */
                ClipboardContent content = new ClipboardContent();
                content.putString(newSampleAccordionPane.getText());
                db.setContent(content);

                event.consume();
            }
        });

        this.sample2View.put(sample, newSampleAccordionPane);
    }

    private void removeSample(Sample sample) {
        this.sampleView.removeSampleAccordionPane(this.sample2View.get(sample));
    }

}
