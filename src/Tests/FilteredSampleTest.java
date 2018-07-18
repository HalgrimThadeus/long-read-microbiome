package Tests;

import javafx.collections.ListChangeListener;
import model.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FilteredSampleTest {

    @Test
    public void getFilteredReads() {
        FilteredSample filteredSample = new FilteredSample();

        filteredSample.getFilteredReads().addListener((ListChangeListener<Read>) change -> {
            while(change.next()) {
                if(change.wasAdded()) {
                    System.out.println("Added: ");
                    printReads((List<Read>) change.getAddedSubList());
                } else if(change.wasRemoved()) {
                    System.out.println("Removed: ");
                    printReads((List<Read>) change.getRemoved());
                } else if(change.wasReplaced()) {
                    System.out.println("Replaced: ");
                    printReads((List<Read>) change.getList());
                } else if(change.wasPermutated()) {
                    System.out.println("Permutated: ");
                    printReads((List<Read>) change.getList());
                } else if(change.wasUpdated()) {
                    System.out.println("Updated: ");
                    printReads((List<Read>) change.getList());
                }
            }
            System.out.println("The list in whole:");
            printReads(filteredSample.getFilteredReads());

        });

        System.out.println("New Sample add------------------------------");
        filteredSample.setSample(shouldCreateNewSample());

        System.out.println("New Filter add------------------------------");
        filteredSample.setFilter(shouldCreateFilter());
    }

    private void printReads(List<Read> reads) {
        String s = "";
        for (Read r: reads) {
            s = s + "\t" + r.getId();
        }
        System.out.println(s);
    }

    private Sample shouldCreateNewSample() {
        Sample sample = new Sample();

        Read read1 = new Read(">read1 bla bla", "ACTGCGCGCGCGCGCGCGCGCG", 23);
        Map<String,String> attributes = new HashMap<String, String>(){
            {
                put("Fancyness", "quiet amazing");
                put("Stickyness", "doesnt let you down");
                put("Name", "ZUCCHINI");
            }
        };
        read1.addGffEntries(new GffEntry("read1", "Test", "gene", 0, 10, 3, '+', 4, attributes));

        Read read2 = new Read(">read2 bla bla", "ACTAAAA", 22);
        read2.addGffEntries(new GffEntry("read2", "Test", "gene", 0, 10, 3, '+', 4, null));

        sample.addRead(read1);
        sample.addRead(read2);

        return  sample;
    }

    private Filter shouldCreateFilter() {
        Filter filter = new Filter();
        List<String> keys = new ArrayList<>() {
            {
                add("Length");
            }
        };
        List<String> values = new ArrayList<>() {
            {
                add("10");
            }
        };
        List<String> compares = new ArrayList<>() {
            {
                add("<");
            }
        };
        filter.buildPredicate(keys,values,compares);
        return filter;
    }

}