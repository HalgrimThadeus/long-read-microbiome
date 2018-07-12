package presenter;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Window;
import model.Filter;
import model.io.ConfigIO;
import model.Project;
import model.Sample;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class SaveProjectPresenter {

    private Project project;

    public SaveProjectPresenter(Project project) {
        this.project = project;
    }


    public void saveProject() throws Exception {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File
                (System.getProperty("user.home")));
        fileChooser.setTitle("Save Project to File...");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Project", "*.project"));
        fileChooser.setInitialFileName("save01.project");
        File file = fileChooser.showSaveDialog(new Stage());
        if (file == null) {
            return;
        }

        ConfigIO configIO = new ConfigIO(file);

        configIO.writeProjectToFile(project);
    }

    public void loadProject(File file) throws Exception {

        //first clear the Project
        project.clear();

        //then add all the samples back into the observable list:
        ConfigIO configIO = new ConfigIO(file);
        if(!configIO.updateProjectFromFile(project)){
            Alert alter = new Alert(Alert.AlertType.WARNING,"Save has Wrong Version!",ButtonType.OK);
            alter.show();
        }

    }
}
