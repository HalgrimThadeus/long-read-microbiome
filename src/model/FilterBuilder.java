package model;
import java.util.function.Predicate;

public class FilterBuilder {

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


    public static Predicate<Read> isGCContentEqual(Integer gcscore){
        return p -> (p.getGc_content() == gcscore);

   }



    public static Predicate<Read> isGCContentHigherEq(double gcscore){
        return p -> (p.getGc_content() >= gcscore);
    }

    public static Predicate<Read> isGCContentLowerEq(double gcscore){
        return p -> (p.getGc_content() <= gcscore);
    }

    public static Predicate<Read> isGen(String name){
        return p-> {
            boolean isname = false;
          for(GffEntry gff: p.getGFFEntries()){
              if(gff.getAttributes().get("Name").equals(name)){
               isname = true;
               break;
              }
          }
          return isname;
        };
    }

    public static Predicate<Read> isLengthGreater(Integer length){
       return p-> {
           boolean lengthis = false;
            for(GffEntry gff: p.getGFFEntries()){
            if(gff.getLength() >= length){
                lengthis = true;
                break;
            }
            }
            return lengthis;
       };

    }

    public static Predicate<Read> isLengthSmaller(Integer length){
        return p-> {
            boolean lengthis = false;
            for(GffEntry gff: p.getGFFEntries()){
                if(gff.getLength() <= length){
                    lengthis = true;
                    break;
                }
            }
            return lengthis;
        };
    }

    public static Predicate<Read> isLengthEqual(Integer length){
       return p-> {
           boolean lengthis =  false;
           for(GffEntry gff: p.getGFFEntries()){
               if(gff.getLength() == length){
                   lengthis = true;
                   break;
               }
           }
           return lengthis;
       };
    }

    public static Predicate<Read> isTaxa(Integer taxaId){
       return p-> p.getTaxonomicId() == taxaId ;

    }
}
