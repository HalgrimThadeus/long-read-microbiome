package model;
import java.util.function.Predicate;

public class FilterBuilder {

    private Predicate<Read> mainPredicate = new Predicate<Read>() {
        @Override
        public boolean test(Read read) {
            return true;
        }
    };


    public void addMainPredicate(Predicate<Read> additionalPredicate){
       mainPredicate = mainPredicate.and(additionalPredicate);
    }
    public Predicate<Read> getMainPredicate(){
        return mainPredicate;
    }
    //examples:
   public static Predicate<Read> isScoreHigher(Integer score){
       return p-> {
         boolean scoreis= false;
         for(GffEntry gff: p.getGFFEntries()){
             if(gff.getScore() >= score){
                 scoreis = true;
                 break;
             }
         }
         return scoreis;
       };

   }
    public static Predicate<Read> isScoreLower(Integer score){
        return p-> {
            boolean scoreis= false;
            for(GffEntry gff: p.getGFFEntries()){
                if(gff.getScore() <= score){
                    scoreis = true;
                    break;
                }
            }
            return scoreis;
        };

    }

    public static Predicate<Read> isScoreEqual(Integer score){
        return p-> {
            boolean scoreis= false;
            for(GffEntry gff: p.getGFFEntries()){
                if(gff.getScore() == score){
                    scoreis = true;
                    break;
                }
            }
            return scoreis;
        };

    }


    public static Predicate<Read> isGCContentEqual(double gcscore){
        return p -> (p.getGCcontent() == gcscore);

   }



    public static Predicate<Read> isGCContentHigherEq(double gcscore){
        return p -> (p.getGCcontent() >= gcscore);
    }

    public static Predicate<Read> isGCContentLowerEq(double gcscore){
        return p -> (p.getGCcontent() <= gcscore);
    }

    public  static Predicate<Read> isGen(String name){
        return p-> {
                for (GffEntry gff : p.getGFFEntries()) {
                    if(gff.getAttributes() != null) {
                        if (gff.getAttributes().containsKey("Name")) {
                            if (gff.getAttributes().get("Name").equals(name)) {
                                return true;
                            }
                        }
                    }
                }
                return false;

        };
    }

    public static Predicate<Read> isLengthGreater(Integer length){
       return p-> (p.getSequence().length() >= length);
    }

    public static Predicate<Read> isLengthSmaller(Integer length){
        return p-> p.getSequence().length() <= length;
    }

    public static Predicate<Read> isLengthEqual(Integer length){
       return p->  p.getSequence().length() == length;

    }

    public static Predicate<Read> isTaxaId(Integer taxaId){
       return p-> p.getTaxonomicId() == taxaId ;
    }

    public boolean applyMainPredicate(Read read){
        return (mainPredicate.test(read));
    }


}
