package Model.IO;


import javax.swing.*;
import java.io.*;

public class ConfigIO {

    private FileWriter writer;

    private FileReader reader;


    /**
     * Constructor for Writing and Reading, initialized by passing a File
     *
     * @param file
     */
    public ConfigIO(File file) throws IOException {
        this.writer = new FileWriter(file);
        this.reader = new FileReader(file);
    }



    public void writeToFile(String stringToWrite) throws Exception {


        if (this.writer == null) {
            throw new Exception("Config-Reader has not been initializied yet");
        } else {
            BufferedWriter bufferedWriter = new BufferedWriter(this.writer);
            bufferedWriter.write(stringToWrite);
            System.out.println("Succesfully wrote something to file");
            bufferedWriter.close();
        }
    }

    public void readInConfigFile(File configFile) {
        //TODO instantly add all the new samples to the View



    }

}
