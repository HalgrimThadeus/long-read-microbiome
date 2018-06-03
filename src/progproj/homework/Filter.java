package progproj.homework;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * Filter works as followed:
 * User input: String of criteria designed like the attribute string.
 * For example:
 * Name=XY;Length= Z; etc
 *Idea: specific length can be given by Length = x and something like length < y by max = y
 */
public class Filter {
    private List<Read> reads;
    private String criteria;
    private List<Read> acceptedreads = new ArrayList<>();
    private String[] criteriaarray = criteria.split(";"); //split the list to geht key=value pairs
    private List<String> keys = new ArrayList<>();
    private List<String> values= new ArrayList<>();

    public Filter(List<Read> reads,String criteria){
        reads = this.reads;
        criteria = this.criteria;
    }

    protected List<Read> getAcceptedReads(){
        findReads();
        return acceptedreads;
    }


    private void findReads(){
        splitKeyValuePairs();
        for(Read read: reads){
            for(GffEntry gff: read.getGFFEntries()){
                boolean fits = true;
                for(int i = 0; i < keys.size(); i++){

                    //First
                    //if there is a size span look at min and max values and compare them to the length
                    if(keys.get(i).equals("min")){
                        if(Integer.parseInt(gff.getAttributes().get("Length")) < Integer.parseInt(values.get(i))){
                            fits = false;
                            break;
                        }
                    }
                    else if(keys.get(i).equals("max")){
                        if(Integer.parseInt(gff.getAttributes().get("Length")) < Integer.parseInt(values.get(i))){
                            fits = false;
                            break;
                        }
                        //then all else keys are compared if they exist in the attributes
                    } else if(gff.getAttributes().get(keys.get(i)) == null){
                        System.err.println("Key doesn't exist in attributes");
                        break;
                    }
                     // if the key is something else look up the value of the gffEntry and compare to value
                    else if(!(gff.getAttributes().get(keys.get(i)).equals(values.get(i)))){
                        fits = false;
                        break;
                    }

                }
                // as soon as one gffEntry passes the filter you can continue with the next read
                if(fits){
                    acceptedreads.add(read);
                    break;
                }
            }
        }
    }

    private void splitKeyValuePairs() {
        //Split key=value pairs to use them in findread;
        for (String pairs : criteriaarray) {

            keys.add(pairs.split("=")[0]);
            values.add(pairs.split("=")[1]);
        }
    }

    protected void writeAcceptedReads(){
        Writer writer ;
        try {
            writer = new FileWriter("AcceptedReads.txt");
            for (Read accepted: acceptedreads) {
                writer.write(accepted.getId());
                writer.append("\n");
            }
            writer.close();
        }
        catch(IOException e){
            System.err.println("Couldn't write out Reads !");
        }
    }






}
