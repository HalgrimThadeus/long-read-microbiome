package model.io;


import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import model.Filter;
import model.FilterBuilder;
import model.Project;
import model.Sample;

import java.beans.XMLEncoder;
import java.io.*;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;

public class ConfigIO {

    private File file;

    public ConfigIO(File file) {
        this.file = file;
    }

   /* public void writeProjectToFile(List<Sample> samples) throws IOException {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(this.file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            //write samples to file by iterating about all of them in the samples list:
            for (Sample sample : samples) {
                objectOutputStream.writeObject(sample);
            }

            objectOutputStream.close();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error initilizing stream");
        }

        /*XMLEncoder enc = null;

        try
        {
            enc = new XMLEncoder( new FileOutputStream(file) );
            enc.writeObject(samples.get(0).);

        }
        catch ( IOException e ) {
            e.printStackTrace();
        }
        finally {
            if ( enc != null )
                enc.close();
        }

    }*/

    public void writeProjectToFile(Project project) {
        try {
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(project.toString());
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*public List<Sample> readProjectFromFile() throws Exception {
        List<Sample> samples = new ArrayList<Sample>();

        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            Sample sample;
            do {
                sample = (Sample) objectInputStream.readObject();
                if (sample == null) break;
                samples.add(sample);
            } while (sample != null);

            objectInputStream.close();
            fileInputStream.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            //just delete the exception, readObject doesnt return null!
            System.out.println("");
        }

        return samples;
    }*/

    public void updateProjectFromFile(Project project) throws IOException {
        try {
            FileReader filereader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(filereader);
            String currentLine = "";
            //first construct the samples
            SampleReader sampleReader = new SampleReader();
            while ((currentLine = bufferedReader.readLine()) != null) {
                if (currentLine.startsWith("##########")) {
                    break;
                } else {
                    String sampleName = currentLine;
                    currentLine = bufferedReader.readLine(); //go to the next line, which is not the name
                    String[] filePaths = currentLine.split("\t");

                    Sample sampleToAdd = new Sample();
                    try {
                        sampleToAdd = sampleReader.read(filePaths[0], filePaths[1], filePaths[2]);
                        sampleToAdd.setName(sampleName);
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("SampleFormat not correct in the File");
                    }
                    project.addSamples(sampleToAdd);
                }
            }
            //Then construct the lists for the Filterbuilder:
            List<String> usedKeys = new ArrayList<>();
            List<String> usedValues = new ArrayList<>();
            List<String> usedCompare = new ArrayList<>();
            String filterName  = "";
            while ((currentLine = bufferedReader.readLine()) != null) {
                filterName = currentLine;
                currentLine = bufferedReader.readLine();
                String[] keysOfLine = currentLine.split("\t");
                for (int i = 0; i < keysOfLine.length; i++) {
                    usedKeys.add(keysOfLine[i]);
                }
                currentLine = bufferedReader.readLine();
                String[] valuesOfLine = currentLine.split("\t");
                for (int i = 0; i < valuesOfLine.length; i++) {
                    usedValues.add(valuesOfLine[i]);
                }
                currentLine = bufferedReader.readLine();
                String[] compareOfLine = currentLine.split("\t");
                for (int i = 0; i < compareOfLine.length; i++) {
                    usedCompare.add(compareOfLine[i]);
                }

            }
            for (int i = 0; i < usedKeys.size(); i++) {
                Filter filter = new Filter(filterName,usedKeys,usedValues,usedCompare);
                project.addFilter(filter);
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

}
