package Model;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class FilterBuilder {

    //examples:
    public static Predicate<Read> scorePredicate = new Predicate<Read>(){
        public boolean test (Read read){
            boolean hasLargerScore = false;
            for(GffEntry gff: read.getGFFEntries()){
                if(gff.getScore()>=x){   //how can we add the x to compare with as a parameter??
                    hasLargerScore = true;
                    break;
                }
            }
            return hasLargerScore;
        }
    };


    public static Predicate<Read> GCContentPredicate = new Predicate<Read>(){
        public boolean test(Read read){
            boolean hasLargerGCContent = false;
            for(GffEntry gff: read.getGFFEntries()){
                if(gff.getAttributes().get("CGContent")>=x){  //same here
                    hasLargerGCContent = true;
                    break;
                }
            }
            return hasLargerGCContent;
        }
    };

    //first try for ANY attribute in the attributes list... - e.g. name, same for others
    public static Predicate<Read> namePredicate = new Predicate<Read>(){
        public boolean test(Read read){
            boolean hasSameName = false;
            for(GffEntry gff: read.getGFFEntries()){
                if(gff.getAttributes().containsKey("name")){
                    if(gff.getAttributes().get("name").equals("xxx")){  //same here
                        hasSameName = true;
                        break;
                    }
                }
            }
            return hasSameName;
        }
    };

    //how to use in the "real" filter function?? (not right place)
    //filter function: uses predicate as an argument
    public static List<Read>  filter (List<Read> reads, Predicate<Read> pred){
        List<Read> filteredReads = new ArrayList<Read>();
        //... apply(map?) predicate function to every read in the list
        return filteredReads;
    }
}
