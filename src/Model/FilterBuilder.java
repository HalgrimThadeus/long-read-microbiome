package Model;
import javax.naming.Name;
import java.util.ArrayList;
import java.util.List;
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


    public static Predicate<Read> isCGContentEqual(Integer cgscore){
       return p-> {
        boolean cgcontentis = false;
        for(GffEntry gff: p.getGFFEntries()){
            if(Integer.parseInt(gff.getAttributes().get("CGConent")) == cgscore){
                cgcontentis = true;
                break;
            }
        }
        return cgcontentis;
       };
   }



    public static Predicate<Read> isCGContentHigher(Integer cgscore){
        return p -> {
            boolean cg = false;
            for(GffEntry gff: p.getGFFEntries()){
                if(Integer.parseInt(gff.getAttributes().get("CGContent")) >= cgscore){
                    cg = true;
                    break;
                }
            }
            return cg;
        };
    }

    public static Predicate<Read> isName(String name){
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
            if(Integer.parseInt(gff.getAttributes().get("Length")) >= length){
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
                if(Integer.parseInt(gff.getAttributes().get("Length")) <= length){
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
               if(Integer.parseInt(gff.getAttributes().get("Length")) == length){
                   lengthis = true;
                   break;
               }
           }
           return lengthis;
       };
    }
}
