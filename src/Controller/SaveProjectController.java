package Controller;
import Model.IO.ConfigIO;
import Model.Project;
import Model.Sample;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SaveProjectController {

    public void saveProject() throws Exception {

        //get the list of samples and the filepaths of the samples
        List<Sample> listOfSamples = Project.listOfSamples;

        List<File[]> listOfPaths= Project.listOfSamplesFilePaths;


        for(Sample sample: listOfSamples){
            //saveFilePathToFile(sample.getFilePaths());
        }


    }

    //Information: FileFormat *.lrcfg stands for "long read config File".
    public void saveFilePathToFile(String[] filePaths) throws Exception {
        //TODO store the information in a represententive and easy readable to the config file
        try {
            File fileToWrite = null;

            //choose the save-directory
            int configVersionName = 1;
            JFileChooser f = new JFileChooser();
            f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            f.showSaveDialog(null);
            System.out.println(f.getSelectedFile());

            //make a config file, that doesnt exist already
            do {
                String fileToWritePath = f.getSelectedFile() + "\\config" + configVersionName + ".lrcfg";

                fileToWrite = new File(fileToWritePath);

                configVersionName++;

            } while (fileToWrite.isFile());

            //
            ConfigIO WriteConfigIO = new ConfigIO(fileToWrite);
            String stringToWrite = "#Sample" + "\n";
            for (int i = 0; i < filePaths.length; i++) {
                stringToWrite += filePaths[i] + "\n";
            }


            WriteConfigIO.writeToFile(stringToWrite);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void readConfigFile() {
        //first get the new File from FileBrowser:
        File configFile = getNewFiles();

        //then read the file
        try {
            ConfigIO readConfigIO = new ConfigIO(configFile);


        } catch (IOException e) {
            e.printStackTrace();
        }
        //TODO then add then add the samples to the view!.

    }


    public File getNewFiles() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Config File");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Config", "*.lrcfg")
        );

        File selectedFile = fileChooser.showOpenDialog(new Stage());

        return selectedFile;
    }


}
