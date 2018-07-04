import model.Project;
import presenter.MainPresenter;
import view.MainView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        MainView mainView = new MainView();
        Project project = new Project();

        FXMLLoader loader = new FXMLLoader(MainView.class.getResource("mainView.fxml"));
        loader.setController(mainView);



        Parent root = loader.load();

        MainPresenter mainPresenter = new MainPresenter(project, mainView);
        primaryStage.setTitle("Long Read Microbiome");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.show();
    }
}
