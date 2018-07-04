package presenter;

import javafx.stage.Window;
import model.io.ConfigIO;
import model.Project;
import model.Sample;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.*;
import java.util.List;


public class SaveProjectPresenter {

    public void saveProject() throws Exception {

        //get the list of samples and the filepaths of the samples
        List<Sample> listOfSamples = Project.listOfSamples;

        List<File[]> listOfPaths = Project.listOfSamplesFilePaths;

        File configFileToWrite = createEmptyFile();

        saveFilePathToFile(listOfSamples, listOfPaths, configFileToWrite);

    }

    private File createEmptyFile() {
        File fileToWrite = null;

        //choose the save-directory
        int configVersionName = 1;
        JFileChooser f = new JFileChooser();
        DirectoryChooser dc = new DirectoryChooser();
        dc.showDialog(new Stage());
        f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        f.showSaveDialog(null);
        System.out.println(f.getSelectedFile());

        //make a config file, that doesnt exist already
        do {
            String fileToWritePath = f.getSelectedFile() + "\\config" + configVersionName + ".lrcfg";

            fileToWrite = new File(fileToWritePath);

            configVersionName++;

        } while (fileToWrite.isFile());

        return fileToWrite;

    }

    //Information: FileFormat *.lrcfg stands for "long read config File".
    public void saveFilePathToFile(List<Sample> listOfSamples, List<File[]> listOfPaths, File configFileToWrite) throws Exception {
        String stringToWrite = "";

        for (int i = 0; i < listOfPaths.size(); i++) {
            //create the StringFilePath arrays (with the 3 filepaths of fasta, gff, and csv)

            File[] file = listOfPaths.get(i);

            String fastaPath = file[0].getAbsolutePath();
            String gffPath = file[1].getAbsolutePath();
            String csvPath = file[2].getAbsolutePath();

            stringToWrite += "#Sample" + i + "\n";
            stringToWrite += fastaPath + "\n";
            stringToWrite += gffPath + "\n";
            stringToWrite += csvPath + "\n";
        }

        ConfigIO WriteConfigIO = new ConfigIO(configFileToWrite);
        WriteConfigIO.writeToFile(stringToWrite);
    }

    public void readConfigFile() throws Exception {
        //first get the new File from FileBrowser:
        File configFile = getNewFile();

        //then read the file
        try {
            ConfigIO readConfigIO = new ConfigIO(configFile);

            readConfigIO.readInConfigFile(configFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
        //TODO then add then add the samples to the view!

    }


    public File getNewFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Config File");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Config", "*.lrcfg")
        );

        File selectedFile = fileChooser.showOpenDialog(new Stage());

        return selectedFile;
    }


}
