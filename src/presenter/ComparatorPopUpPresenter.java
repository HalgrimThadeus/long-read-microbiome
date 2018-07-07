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
    private LineChart<String, Number> lineChart;

    public ComparatorPopUpPresenter(MainPresenter mainPresenter){
        this.mainPresenter = mainPresenter;
    }

    public void openComparatorView() throws IOException {
        ComparatorView newComparatorView = new ComparatorView(mainPresenter.getComparatorViewPresenter());

        Stage comparatorView = new Stage();
        FXMLLoader loader = new FXMLLoader(MainView.class.getResource("ComparatorViewWaiting.fxml"));
        loader.setController(newComparatorView);
        Parent root = loader.load();

        comparatorView.setTitle("Comparison");
        comparatorView.setScene(new Scene(root, 600, 250));
        comparatorView.show();

        mainPresenter.getComparatorViewPresenter().setStage(comparatorView);
    }
}
