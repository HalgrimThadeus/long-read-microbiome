package model.io;


import model.Filter;
import model.Project;
import model.Sample;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ConfigIO {

    private File file;

    private static String VERSIONSNUMMER = "Version 1.0";

    public ConfigIO(File file) {
        this.file = file;
    }

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


    public boolean updateProjectFromFile(Project project) throws IOException {
        try {
            FileReader filereader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(filereader);
            String currentLine = bufferedReader.readLine();
            if (!currentLine.contains(VERSIONSNUMMER)) {
                return false;
            }
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
            String filterName = "";
            while ((currentLine = bufferedReader.readLine()) != null) {
                filterName = currentLine;
                currentLine = bufferedReader.readLine();
                String[] keysOfLine = currentLine.split("\t");

                Collections.addAll(usedKeys, keysOfLine);
                currentLine = bufferedReader.readLine();
                String[] valuesOfLine = currentLine.split("\t");
                Collections.addAll(usedValues, valuesOfLine);
                currentLine = bufferedReader.readLine();
                String[] compareOfLine = currentLine.split("\t");
                Collections.addAll(usedCompare, compareOfLine);
                System.out.println(usedKeys);

                for (int i = 0; i < usedKeys.size(); i++) {
                    Filter filter = new Filter(filterName, usedKeys, usedValues, usedCompare);
                    project.addFilter(filter);
                }
                usedKeys.clear();
                usedCompare.clear();
                usedValues.clear();
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return true;

    }

}
