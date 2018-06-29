package Controller;

import Model.IO.ConfigIO;
import Model.Sample;

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

    public void saveFilePathToFile(String[] filePaths) throws Exception {
        try {
            ConfigIO configIO = new ConfigIO("config1");
            String stringToWrite = "";
            for (int i = 0; i < filePaths.length; i++) {
                stringToWrite += filePaths[i] + "\n";
            }

            configIO.writeToFile(stringToWrite);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
