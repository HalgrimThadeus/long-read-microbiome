package Model;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import static Model.FilterBuilder.*;
import java.util.function.Predicate;
/**
 * New idea with GUI
 * List of key are the selected criteria
 * List of values the user entered in the Textfield
 *
 * It now is given to the method and depending on what kind of key we got we use a given predicate or the hashmap
 */
public class Filter {
    private List<Read> reads;
    private String criteria;
    private List<Read> acceptedreads = new ArrayList<>();
    private List<String> keys;
    private List<String> values;
    private List<String> criterias = new ArrayList<>();
    public Filter(Sample sample,List<String> keys, List<String> values){
        List<Read> r = sample.getReads();
        r = this.reads;
        keys = this.keys;
        values = this.values;
    }

    protected List<Read> getAcceptedReads(){
        return suitable();
    }



    protected List<Read> suitable(){
        List<Read> suitablereads = reads;
             for(int k = 0; k < keys.size(); k++){
                if(keys.get(k).equals("Length")){
                    suitablereads.removeIf(isLengthEqual(Integer.parseInt(values.get(k))).negate());
                }
                else if(keys.get(k).equals("CGContent")){
                    suitablereads.removeIf(isCGContentEqual(Integer.parseInt(values.get(k))).negate());
                }
                else if(keys.get(k).equals("Name")){
                    suitablereads.removeIf(isName(values.get(k)).negate());
                }
                else if(keys.get(k).equals("Score")){
                    suitablereads.removeIf(isScoreEqual(Integer.parseInt(values.get(k))).negate());
                }
                else{
                    for(Read read: reads){
                        boolean fits = false;
                        for(GffEntry gff: read.getGFFEntries()){
                            if(gff.getAttributes().get(keys.get(k)).equals(values.get(k))){
                                fits = true;
                                break;
                            }
                        }
                        if(!fits){
                            suitablereads.remove(read);
                        }
                    }
                }

             }




        return suitablereads;
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




/**
 * gets all the attributes so we can display them in the UI. With that the user will be abel to filter
 */

    protected List<String> getFilterCriteria(){
        List<String> criteria = new ArrayList();

        for(Read read: reads) {
            for (GffEntry gff : read.getGFFEntries()) {
                List<String> attributes = new ArrayList<>();
                attributes.addAll(gff.getAttributes().keySet());
                for (String attribute : attributes) {
                    if (!criteria.contains(attribute)) {
                        criteria.add(attribute);
                    }
                }
            }
        }

        return criteria;
    }



    protected List<String> getCriteria(){
        return criterias;
    }









}
