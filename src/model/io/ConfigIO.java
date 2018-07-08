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


}
