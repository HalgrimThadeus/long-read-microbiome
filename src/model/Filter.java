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
public class Filter {
    private FilterBuilder filterBuilder = new FilterBuilder();
    private Predicate<Read> filterPredicate;
    private String name;
    public Filter(String name, Predicate<Read> filterPredicate){
        this.filterPredicate = filterPredicate;
        this.name = name;
    }
    public String getName(){
        return name;
    }

    public FilterBuilder getFilterBuilder(){
        return filterBuilder;
    }
    public void setFilterBuilder(FilterBuilder fb){
        filterBuilder = fb;
    }

    /**
     * @return two-line String with name + \n +  \t filterAttribute1  + \t filterValueOfAttribute1 +. ...
     */
    @Override
    public String toString(){
        String res = name + '\n';
        List<String> usedKey = filterBuilder.getUsedKey();
        List<String> usedValues = filterBuilder.getUsedValues();
        for (int i = 0; i < usedKey.size(); i++){
            res += usedKey.get(i) + '\t' + usedValues.get(i) + '\t';
        }

        return res;
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

    public Predicate<Read> getFilterPredicate() {
        return filterPredicate;
    }
}
