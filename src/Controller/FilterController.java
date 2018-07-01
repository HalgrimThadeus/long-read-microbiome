package Controller;

import View.Presenter;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class FilterController {

    public void openNewFilterDialog() throws IOException {
        Stage filterPopUp = new Stage();
        Parent root = FXMLLoader.load(Presenter.class.getResource("filterview2.fxml"));
        filterPopUp.setTitle("New Filter");
        filterPopUp.setScene(new Scene(root, 400, 400));
        filterPopUp.show();
    }
}