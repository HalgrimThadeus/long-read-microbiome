package presenter;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Project;
import view.FilterView;
import view.MainView;
import view.NewSamplePopUp;

import java.io.IOException;


public class FilterPresenter {

    private FilterView filterView;

    public FilterPresenter(FilterView filterView) {
        this.filterView = filterView;
    }

    public void openNewFilterDialog() throws IOException {
        Stage filterPopUp = new Stage();

        FXMLLoader loader = new FXMLLoader(MainView.class.getResource("newFilterPopUp.fxml"));
        loader.setController(filterView);

        Parent root = loader.load();
        filterPopUp.setTitle("New Filter");
        filterPopUp.setScene(new Scene(root, 400, 400));
        filterPopUp.show();
    }



}
