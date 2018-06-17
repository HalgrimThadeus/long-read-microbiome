package View;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;

public class Main extends Application {

    //todo: these attributes must be moved to controller
    private @FXML
    Button openFileButton;
    private @FXML
    TextField outputTestField;
    private FileChooser fileChooser;


    private Scene scene;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //set environmental parameters
        Parent root = FXMLLoader.load(getClass().getResource("/View/sample.fxml"));
        primaryStage.setTitle("DNA to protein sequence");
        scene = new Scene(root, 600, 500);



        ///////////////////////////////////////////////////////////////////////
        //test part
        //set some input interfaces

        fileChooser = new FileChooser();
        openFileButton = (Button) scene.lookup("#openFileButton");
        outputTestField = (TextField) scene.lookup("#outputTestField");
        openFileButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                File file = fileChooser.showOpenDialog(primaryStage);
                if (file != null) {
                    try {
                        openFile(file);
                    } catch(IOException exception) {
                        exception.printStackTrace();
                    }
                }
            }
        });
        /////////////////////////////////////////////////////////////////////////
        //test until here


        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * test method for opening file
     * todo: must be moved to controller or model
     */
    private void openFile(File file) throws IOException {
        FileReader fileReader = new FileReader(file);
        BufferedReader reader = new BufferedReader(fileReader);

        String inputString = reader.readLine();

        outputTestField.setText(inputString);
    }
}
