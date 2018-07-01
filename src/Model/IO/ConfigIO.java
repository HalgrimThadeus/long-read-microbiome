package Model.IO;


import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

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

    /**
     * TODO until now should just return listofPaths
     * @param configFile
     * @return
     * @throws Exception
     */
    public List<String[]> readInConfigFile(File configFile) throws Exception {
        List<String[]> listOfListOfPaths = new ArrayList<String[]>();
        if (this.reader == null) {
            throw new Exception("CSV-Reader has not been initializied yet");
        } else {
            BufferedReader bufferedReader = new BufferedReader(this.reader);

            String currentLine = "";
            int positionInArray = 0;
            int positionInList = 0;
            while ((currentLine = bufferedReader.readLine()) != null ) {
                if(currentLine.startsWith("#")){
                   positionInList++;
                   listOfListOfPaths.add(new String[3]);
                }
                else{
                    listOfListOfPaths.get(positionInList)[positionInList] = currentLine;
                    positionInArray++;
                }
            }
            return listOfListOfPaths;
        }
    }

}
