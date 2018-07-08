package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 *Takes a predicate and applies it to the reads of the sample
 *
 *TODO : method for GC Content , Taxa, Length etc.
 *
 *
 *
 */
public class Filter implements Serializable {
    private Predicate<Read> filterPredicate;
    private String name;
    public Filter(String name, Predicate<Read> filterPredicate){
        this.filterPredicate = filterPredicate;
        this.name = name;
    }
    public String getName(){
        return name;
    }



    /**applies predicate to the List of Reads
     * @return List of accepted reads
    **/
    public List<Read> suitable(Sample sample){
        List<Read> acceptedReads = new ArrayList<>();
        if((sample != null) && !sample.getReads().isEmpty()) {
            for (Read read : sample.getReads()) {
                if (filterPredicate.test(read)) {
                    acceptedReads.add(read);
                }
            }
        }
        return acceptedReads;
    }


    /**
    public List<Read> suitable(Sample sample){
        List<Read> reads = sample.getReads();
        List<Read> suitablereads = new ArrayList<>();
        for(Read read: reads) {
            boolean fits = true;
            for (int k = 0; k < keys.size(); k++) {
                if (!values.get(k).equals("")) {
                    if (keys.get(k).equals("Length")) {
                        if(!isLengthEqual(Integer.parseInt(values.get(k))).test(read)) {
                            fits = false;
                            break;
                        }

                    } else if (keys.get(k).equals("GCContent")) {
                        if(!isGCContentEqual(Integer.parseInt(values.get(k))).test(read)){
                            fits = false;
                            break;
                        }
                    } else if (keys.get(k).equals("Gen")) {
                        if(!isGen(values.get(k)).test(read)){
                            fits = false;
                            break;
                        }
                    } else if (keys.get(k).equals("Score")) {
                        if (!isScoreEqual(Integer.parseInt(values.get(k))).test(read)){
                            fits = false;
                            break;
                        }
                    } else if (keys.get(k).equals("TaxaId")) {
                        if(!isTaxa(Integer.parseInt(values.get(k))).test(read)){
                            fits = false;
                            break;
                        }
                    } else {
                            for (GffEntry gff : read.getGFFEntries()) {
                                if (!gff.getAttributes().get(keys.get(k)).equals(values.get(k))) {
                                    fits = false;
                                    break;
                                }
                            }
                            if (fits) {
                                suitablereads.add(read);
                            }
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

**/


/**
 * gets all the attributes so we can display them in the UI. With that the user will be abel to filter
 * and sets them to criteria

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

**/







}
