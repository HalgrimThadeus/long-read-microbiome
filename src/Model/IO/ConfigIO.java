package Model.IO;


import java.io.*;

public class ConfigIO {

    private FileWriter writer;

    private FileReader reader;


    /**
     * Constructor for Writing, initialized by using fileName
     *
     * @param fileName
     */
    public ConfigIO(String fileName) throws IOException {
        this.writer = new FileWriter(fileName);
    }

    /**
     * Constructor for Reading, initilized by using File
     * @param file
     * @throws Exception
     */
    public ConfigIO(File file) throws IOException{
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
