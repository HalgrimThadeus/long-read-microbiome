package presenter;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.stage.Stage;
import view.ComparatorPopUp;
import view.ComparatorView;
import view.MainView;

import java.io.IOException;

public class ComparatorPopUpPresenter {
    private MainPresenter mainPresenter;
    private ComparatorViewPresenter comparatorViewPresenter;
    private ComparatorPopUp comparatorPopUp;
    private LineChart<String, Number> vBox;

    public ComparatorPopUpPresenter(MainPresenter mainPresenter){
        this.mainPresenter = mainPresenter;
    }

    public void openComparatorView() throws IOException, InterruptedException {
        ComparatorView newComparatorView = new ComparatorView(mainPresenter.getComparatorViewPresenter());
        Stage comparatorView = new Stage();
        FXMLLoader loader = new FXMLLoader(MainView.class.getResource("ComparatorView.fxml"));
        loader.setController(newComparatorView);
        Parent root = loader.load();
        Scene scene = new Scene(root, 700, 700);
        comparatorView.setTitle("Comparison");
        comparatorView.setScene(scene);
        comparatorView.show();
    }
}
