package model.io;


import javafx.collections.ObservableList;
import model.Project;
import model.Sample;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ConfigIO {

   private File file;

    public ConfigIO(File file) {
        this.file = file;
    }

    public void writeProjectToFile(List<Sample> samples) throws IOException {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(this.file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            //write samples to file by iterating about all of them in the samples list:
            for (Sample sample: samples) {
            objectOutputStream.writeObject(sample);
            }

            objectOutputStream.close();
            fileOutputStream.close();
        }catch (FileNotFoundException e){
            System.out.println("File not found");
        }catch (IOException e){
            System.out.println("Error initilizing stream");
        }
    }

    public List<Sample> readProjectFromFile() throws Exception{
        List<Sample> samples = new ArrayList<Sample>();

        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            Sample sample;
            do {
                sample = (Sample)objectInputStream.readObject();
                if(sample == null) break;
                samples.add(sample);
            }while(sample != null);

            objectInputStream.close();
            fileInputStream.close();
        }catch (FileNotFoundException e){
            System.out.println("File not found");
        }catch (IOException e){
            //just delete the exception, readObject doesnt return null!
            System.out.println("");
        }

        return samples;
    }










    /*
    private FileWriter writer;


    private FileReader reader;


    /**
     * Constructor for Writing and Reading, initialized by passing a File
     *
     * @param file

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
    */

}
