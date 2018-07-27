package model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 *Takes a predicate and applies it to the reads of the sample
 *
 *
 *
 */
public class Filter {
    private Predicate<Read> filterPredicate = read -> true;

    private List<String> keys = new ArrayList<>();
    private List<String> values = new ArrayList<>();
    private List<String> compare = new ArrayList<>();
    private String name;
    public static String gene = "Gene";
    public static String gc = "GC";
    public static String length = "Length";
    public static String taxa = "Taxa";
    public static String score = "Score";

    public Filter(String name, List<String> keys, List<String> values, List<String> compare) {
        this.values.addAll(values);
        this.keys.addAll(keys);
        this.name = name;
        this.compare.addAll(compare);
        buildPredicate(keys, values, compare);
    }

    public Filter(){
        this.name = "filter not named";
    }

    public String getName() {
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public List<String> getCompare() {
        return compare;
    }

    public List<String> getKeys() {
        return keys;
    }

    public List<String> getValues() {
        return values;
    }

    /**
     * @return two-line String with name + \n +  \t filterAttribute1  + \t filterValueOfAttribute1 +. ...
     */
    @Override
    public String toString() {
        String res = name + '\n';
        res += String.join("\t", this.keys);
        res+= "\n";
        res += String.join("\t", this.values);
        res+= "\n";
        res += String.join("\t", this.compare);
        res += "\n";

        return res;
    }


    public void buildPredicate(List<String> keys, List<String> values, List<String> compares) {
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = values.get(i);
            String compare = compares.get(i);
            if (key.equals(gene)) {
                filterPredicate = filterPredicate.and(FilterBuilder.isGen(value));
            } else if (key.equals(gc)) {
                if (isDouble(value)) {
                    if (compare.equals("<")) {
                        filterPredicate = filterPredicate.and(FilterBuilder.isGCContentLowerEq(Double.parseDouble(value)));
                    }
                    //adds greatereq to filter if the textfield contains a >
                    else if (compare.equals(">")) {
                        filterPredicate = filterPredicate.and(FilterBuilder.isGCContentHigherEq(Double.parseDouble(value)));
                    }
                    //adds equal predicate if nothing but the number is given
                    else if (compare.equals("=")) {
                        filterPredicate = filterPredicate.and(FilterBuilder.isGCContentEqual(Double.parseDouble(value)));
                    }
                }
            } else if (key.equals(length)) {
                if (isDigit(value)) {
                    if (compare.equals("=")) {
                        filterPredicate = filterPredicate.and(FilterBuilder.isLengthEqual(Integer.parseInt(value)));
                    } else if (compare.equals(">")) {
                        filterPredicate = filterPredicate.and(FilterBuilder.isLengthGreater(Integer.parseInt(value)));
                    } else if (compare.equals("<")) {
                        filterPredicate = filterPredicate.and(FilterBuilder.isLengthSmaller(Integer.parseInt(value)));
                    }
                }
            } else if (key.equals(score)) {
                if (isDigit(value)) {
                    if (compare.equals("=")) {
                        filterPredicate = filterPredicate.and(FilterBuilder.isScoreEqual(Integer.parseInt(value)));
                    } else if (compare.equals("<")) {
                        filterPredicate = filterPredicate.and(FilterBuilder.isScoreLower(Integer.parseInt(value)));
                    } else if (compare.equals(">")) {
                        filterPredicate = filterPredicate.and(FilterBuilder.isScoreHigher(Integer.parseInt(value)));
                    }
                }

            } else if (key.equals(taxa)) {

                if (isDigit(value)) {
                    filterPredicate = filterPredicate.and(FilterBuilder.isTaxaId(Integer.parseInt(value)));
                }
                else{ filterPredicate = filterPredicate.and(FilterBuilder.isTaxaByName(taxa));}
            }
        }

    }


    //Checks if a String is a Int
    private boolean isDigit(String test) {

        boolean isDigit;
        try {
            Integer.parseInt(test);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }


    }

    private boolean isDouble(String test) {

        try {
            Double.parseDouble(test);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }


    }
    public void setFilterPredicate(Predicate p){
        filterPredicate = p;
    }

    public Predicate<Read> getFilterPredicate() {
        return filterPredicate;
    }

    public static Filter combineFilter(Filter f1, Filter f2){
        List<String> newKeys = new ArrayList<>();
        List<String> newValues = new ArrayList<>();
        List<String> newCompares = new ArrayList<>();

        newKeys.addAll(f1.getKeys());
        newKeys.addAll(f2.getKeys());
        newValues.addAll(f1.getValues());
        newValues.addAll(f2.getValues());
        newCompares.addAll(f1.getCompare());
        newCompares.addAll(f2.getCompare());
        Filter filter = new Filter(f1.getName() + " + " + f2.getName(), newKeys,newValues,newCompares);
        return filter;
    }
}
