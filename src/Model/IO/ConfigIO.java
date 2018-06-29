package Model.IO;


import java.io.*;
import java.nio.Buffer;
import java.util.HashMap;
import java.util.Map;

public class ConfigIO {

    private FileWriter writer;

    private FileReader reader;


    /**
     * Constructors for Writing, initialized by using fileName
     *
     * @param fileName
     */
    public ConfigIO(String fileName) throws IOException {
        this.writer = new FileWriter(fileName);
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

}
