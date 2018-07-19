package model;
import model.tax.TaxTree;

import java.util.function.Predicate;

public class FilterBuilder {
    static TaxTree tree = Project.tree;

    //examples:
   public static Predicate<Read> isScoreHigher(Integer score){
       return p-> {

         for(GffEntry gff: p.getGFFEntries()){
             if(gff.getScore() >= score){
                 return true;
             }
         }
         return false;
       };

   }
    public static Predicate<Read> isScoreLower(Integer score){
        return p-> {
            for(GffEntry gff: p.getGFFEntries()){
                if(gff.getScore() <= score){
                   return  true;

                }
            }
            return false;
        };

    }

    public static Predicate<Read> isScoreEqual(Integer score){
        return p-> {

            for(GffEntry gff: p.getGFFEntries()){
                if(gff.getScore() == score){
                   return  true;

                }
            }
            return false;
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
                        if (gff.getAttributes().containsKey(" Name")) {
                            if (gff.getAttributes().get(" Name").contains(name)) {
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
        return p-> (p.getSequence().length() <= length);
    }

    public static Predicate<Read> isLengthEqual(Integer length){
       return p->  (p.getSequence().length() == length);

    }

    public static Predicate<Read> isTaxaId(Integer taxaId){
       return p-> (p.getTaxonomicId() == taxaId) ;
    }

   public static Predicate<Read>  isTaxaByName(String name) {
        return p -> {
        if(!tree.isEmpty()) {
            for (GffEntry gff : p.getGFFEntries()) {
                if ((gff.getAttributes() != null) && gff.getAttributes().containsKey("tax")) {
                    if (tree.getNode(name).getAllChildren().contains(gff.getAttributes().get("tax"))) {
                        return true;

                    }
                }
            }
            return false;
        }
        return false;
        };
    }
}
