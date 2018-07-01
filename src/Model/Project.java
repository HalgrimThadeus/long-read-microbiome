package Model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * This is a big container/storage class of the Samples for the the whole Project (of a Session)
 * All Samples get added to the list of Samples in here
 */

public class Project {

    /**
     * static List contains all the Samples created while the program runs
     */
    public static List listOfSamples;

    /**
     * static List contains all the Files for the Samples including the filepaths
     */
    public static List<File[]> listOfSamplesFilePaths;

    /**
     * Add a new Sample
     * @param sampleToAdd
     */
    public static void addSamples(Sample sampleToAdd){
        listOfSamples.add(sampleToAdd);
    }

}
