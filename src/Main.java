import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Project;
import presenter.MainPresenter;
import view.MainView;

public class Main extends Application {

    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        MainView mainView = new MainView();

        FXMLLoader loader = new FXMLLoader(MainView.class.getResource("mainView.fxml"));
        loader.setController(mainView);

        Parent root = loader.load();

        Project project = new Project();

        MainPresenter mainPresenter = new MainPresenter(project, mainView);
        mainView.setMainPresenter(mainPresenter);
        primaryStage.setTitle("LAMA");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.show();
    }
}
