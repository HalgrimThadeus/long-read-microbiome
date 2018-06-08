package Model;

import java.util.ArrayList;
import java.util.List;

public class Sample {

    private List<Read> reads = new ArrayList<>(); // List of Reads

    public List<Read> getReads() {
        return reads;
    }

    public void setReads(List<Read> newreads) {
        reads = newreads;
    }

    //Method to add single Reads to the List
    public void addReads(Read newread){
        reads.add(newread);
    }

    /*---------------------------------------------------------------
    Picks up all reads with the demanded gene (id?) from the reads list.
   private List<Read> getReadsWithGene(String gene){

        List<Read> readswithgene = new ArrayList<>();

        for(Read possibleread: reads){
          List<GffEntry> entries = possibleread.getGFFEntries();    //List of entries in the current read
            //checks if the current entries contains the gene . if yes it adds read to result else not
            for(GffEntry entry: entries){
              if(isGeneInRead(entry,gene)){
                  readswithgene.add(possibleread);
              break;
              }
          }

        }

        return readswithgene;
    }
    --------------------------------------------------*/

//Checks if the gene is contained in an entry
    private List<Read> filter(String criteria){
        Filter crit = new Filter(reads,criteria);
        List<Read> acceptedreads = crit.getAcceptedReads();
        crit.writeAcceptedReads();
        return acceptedreads;
    }
}
