package presenter;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.MainView;
import view.NewSamplePopUp;
import view.SampleView;

import java.io.IOException;

public class SamplePresenter {

    private NewSamplePopUpPresenter newSamplePopUpPresenter;

    private SampleView sampleView;

    public SamplePresenter(NewSamplePopUpPresenter newSamplePopUpPresenter) {
        this.newSamplePopUpPresenter = newSamplePopUpPresenter;
    }

    public SamplePresenter(SampleView sampleView) {
        this.sampleView = sampleView;
    }

    public void openSamplePane() throws IOException {
        NewSamplePopUp newSamplePopUp = new NewSamplePopUp(newSamplePopUpPresenter);

        Stage samplePopUp = new Stage();
        FXMLLoader loader = new FXMLLoader(MainView.class.getResource("newSamplePopUp.fxml"));

        loader.setController(newSamplePopUp);
        Parent root = loader.load();

        samplePopUp.setTitle("New Sample");
        samplePopUp.setScene(new Scene(root, 600, 250));
        samplePopUp.show();
    }

    public SampleView getSampleView() {
        return sampleView;
    }
}
