package Model;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a big container/storage class of the Samples for the the whole Project (of a Session)
 * All Samples get added to the list of Samples in here
 */

public class Project {

    private List listOfSamples;

    public Project(){
        this.listOfSamples = new ArrayList();
    }

    /**
     * Add a new Sample
     * @param sampleToAdd
     */
    public void addSamples(Sample sampleToAdd){
        this.listOfSamples.add(sampleToAdd);
    }


}
