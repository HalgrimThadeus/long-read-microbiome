package model;

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
    private Predicate<Read> filterPredicate = new Predicate<Read>() {
        @Override
        public boolean test(Read read) {
            return false;
        }
    };
    private List<String> keys;
    private List<String> values;
    private List<String> compare;
    private String name;

    public Filter(String name, List<String> keys, List<String> values, List<String> compare) {
        this.values = values;
        this.keys = keys;
        this.name = name;
        this.compare = compare;
        buildPredicate(keys, values, compare);
    }

    public Filter(){}

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
        List<String> usedKey = this.keys;
        List<String> usedValues = this.values;
        List<String> usedCompare = this.compare;
        res += String.join("\t", usedKey);
        res+= "\n";
        res += String.join("\t", usedValues);
        res+= "\n";
        res += String.join("\t", usedCompare);
        System.out.println(usedKey.toString());
        System.out.println(usedValues.toString());
        System.out.println(usedCompare.toString());
        System.out.println(res);

        return res;
    }


    /**
     * applies predicate to the List of Reads
     *
     * @return List of accepted reads
     **/
    public List<Read> suitable(Sample sample) {
        List<Read> acceptedReads = new ArrayList<>();
        if ((sample != null) && !sample.getReads().isEmpty()) {
            for (Read read : sample.getReads()) {
                if (filterPredicate.test(read)) {
                    acceptedReads.add(read);
                }
            }
        }
        return acceptedReads;
    }


    public void buildPredicate(List<String> keys, List<String> values, List<String> compares) {
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = values.get(i);
            String compare = compares.get(i);
            if (key.equals("Gen")) {
                filterPredicate = filterPredicate.and(FilterBuilder.isGen(value));
            } else if (key.equals("GC")) {
                if (isDouble(key)) {
                    if (compare.equals("<")) {
                        filterPredicate = filterPredicate.and(FilterBuilder.isGCContentLowerEq(Double.parseDouble(value)));
                    }
                    //adds greatereq to filter if the textfield contains a >
                    else if (compare.equals(">")) {
                        filterPredicate.and(FilterBuilder.isGCContentHigherEq(Double.parseDouble(value)));
                    }
                }
                //adds equal predicate if nothing but the number is given
                else if (compare.equals("=")) {
                    filterPredicate = filterPredicate.and(FilterBuilder.isGCContentEqual(Double.parseDouble(value)));
                }
            } else if (key.equals("Length")) {
                if (isDigit(key)) {
                    if (compare.equals("="))
                        filterPredicate = filterPredicate.and(FilterBuilder.isLengthEqual(Integer.parseInt(value)));
                } else if (compare.equals(">")) {
                    filterPredicate = filterPredicate.and(FilterBuilder.isLengthGreater(Integer.parseInt(value)));
                } else if (compare.equals("<")) {

                    filterPredicate = filterPredicate.and(FilterBuilder.isLengthSmaller(Integer.parseInt(value)));
                }
            } else if (key.equals("Score")) {
                if (isDigit(key)) {
                    if (compare.equals("=")) {
                        filterPredicate = filterPredicate.and(FilterBuilder.isScoreEqual(Integer.parseInt(value)));
                    } else if (compare.equals("<")) {
                        filterPredicate = filterPredicate.and(FilterBuilder.isScoreLower(Integer.parseInt(value)));
                    } else if (compare.equals(">")) {
                        filterPredicate = filterPredicate.and(FilterBuilder.isScoreHigher(Integer.parseInt(value)));
                    }
                }

            } else if (key.equals("Taxa")) {

                if (isDigit(key)) {
                    filterPredicate.and(FilterBuilder.isTaxaId(Integer.parseInt(value)));
                }
                //else case is filter by name (atm idk how)
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

    public Predicate<Read> getFilterPredicate() {
        return filterPredicate;
    }
}
