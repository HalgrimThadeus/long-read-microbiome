package Model;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import static Model.FilterBuilder.*;
import java.util.function.Predicate;
/**
 * List of key are the selected criteria
 * List of values the user entered in the Textfield
 *
 * It now is given to the method and depending on what kind of key we got we use a given predicate or the hashmap
 *
 *TODO : method for GC Content , Taxa, Length etc.
 *
 *
 *
 */
public class Filter {
    private List<Read> acceptedreads = new ArrayList<>();
    private List<String> keys;
    private List<String> values;
    private List<String> criterias;
    String name;

    public Filter(String name,List<String> keys, List<String> values){
        this.keys = keys;
        this.values = values;
        this.name = name;
    }

    protected List<Read> suitable(Sample sample){
        List<Read> reads = sample.getReads();
        List<Read> suitablereads = sample.getReads();
             for(int k = 0; k < keys.size(); k++){
                if(keys.get(k).equals("Length")){
                    suitablereads.removeIf(isLengthEqual(Integer.parseInt(values.get(k))).negate());
                }
                else if(keys.get(k).equals("CGContent")){
                    suitablereads.removeIf(isGCContentEqual(Integer.parseInt(values.get(k))).negate());
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
 * and sets them to criteria
 */
    protected void getFilterCriteria(Sample sample){
        criterias = new ArrayList();
        List<Read> reads = sample.getReads();
        for(Read read: reads) {
            for (GffEntry gff : read.getGFFEntries()) {
                List<String> attributes = new ArrayList<>();
                attributes.addAll(gff.getAttributes().keySet());
                for (String attribute : attributes) {
                    if (!criterias.contains(attribute)) {
                        criterias.add(attribute);
                    }
                }
            }
        }
    }



    protected List<String> getCriteria(){
        return criterias;
    }









}
