package Model;

import java.util.ArrayList;
import java.util.List;

public class Sample {
    /**
     * Contains the List of the Reads in this Sample
     */
    private List<Read> reads = new ArrayList<>();

    /**
     * Returns the List of Reads
     * @return reads
     */
    public List<Read> getReads() {
        return reads;
    }

    /**
     * Set the List of reads to a new List of reads
     * @param newreads
     */
    public void setReads(List<Read> newreads) {
        reads = newreads;
    }

    /**
     * Add a new Read to to current List of Reads
     * @param newread
     */
    public void addReads(Read newread){
        reads.add(newread);
    }

    //TODO Is this working??? --> now this should (not safe) work , but it's not intended to be in Filter, not here
    /*
      //Picks up all reads with the demanded gene (id?) from the reads list.
   private List<Read> getReadsWithGene(String geneID){

        List<Read> readswithgene = new ArrayList<>();


        for(Read possibleread: reads){
          List<GffEntry> entries = possibleread.getGFFEntries();    //List of entries in the current read
            //checks if the current entries contains the gene . if yes it adds read to result else not
            for(GffEntry entry: entries){
                if (entry.getAttributefromKey("ID") == geneID){
                    readswithgene.add(possibleread);
                    break;
                }
          }

        }

        return readswithgene;
    }
    */


    /**
     * Checks if the gene is contained in an entry
     * @param keys
     * @param values
     * @return
     */
    private List<Read> filter(List<String> keys, List<String> values){
        Filter crit = new Filter(reads, keys,values);
        List<Read> acceptedreads = crit.getAcceptedReads();
        crit.writeAcceptedReads();
        return acceptedreads;
    }
}
