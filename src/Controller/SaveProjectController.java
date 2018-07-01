package Controller;

import Model.IO.ConfigIO;
import Model.Sample;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.*;

public class SaveProjectController {

    public void saveProject() throws Exception {
        /*TODO THIS IS JUST ALL PSEUDOCODE; FOR IMPLEMENTATION IS NEEDED:
          TODO - CONTAINER CLASS OF ALL SAMPLES WITH FILE PATH (FOR SAMPLEPANE NEEDED)
          TODO - OBSERVABLE LIST OF FILTERS FOR EVERY SAMPLE
        for every sample of sample container Class
        SAVE FILEPATHS TO FILE
        SAVE CURRENT FILTER FOR THE SAMPLE
         */

        //first mini-test:
        String[] test = {"a", "b", "c"};
        saveFilePathToFile(test);

    }

    //Information: FileFormat *.lrcfg stands for "long read config File".
    public void saveFilePathToFile(String[] filePaths) throws Exception {
        //TODO store the information in a represententive and easy readable to the config file
        //TODO also check if file already exists, otherwise make new one istead of overwriting the existing
        try {
            int configVersionName = 1;
            File fileToWrite = null;

            JFileChooser f = new JFileChooser();
            f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            f.showSaveDialog(null);
            System.out.println(f.getSelectedFile());

            do {
                String fileToWritePath = f.getSelectedFile() + "\\config" + configVersionName + ".lrcfg";

                fileToWrite = new File(fileToWritePath);

                configVersionName++;

            } while (fileToWrite.isFile());


            ConfigIO WriteConfigIO = new ConfigIO(fileToWrite);
            String stringToWrite = "";
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
