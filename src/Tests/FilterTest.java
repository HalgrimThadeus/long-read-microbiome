package Tests;

import model.Filter;
import model.GffEntry;
import model.Read;
import model.Sample;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class FilterTest {

    private Sample shouldCreateNewSample() {
        Sample sample = new Sample();

        Read read1 = new Read(">read1 bla bla", "ACTGCGCGCGCGCGCGCGCG", 23);
        Map<String,String> attributes = new HashMap<>(){
            {
                put("Fancyness", "quiet amazing");
                put("Stickyness", "doesnt let you down");
                put("Name", "ZUCCHINI");
            }
        };
        read1.addGffEntries(new GffEntry("read1", "Test", "gene", 0, 10, 3, '+', 4, attributes));

        Read read2 = new Read(">read2 bla bla", "ACTAAAAAA", 22);
        read2.addGffEntries(new GffEntry("read2", "Test", "gene", 0, 10, 3, '+', 4, null));

        sample.addRead(read1);
        sample.addRead(read2);

        return  sample;
    }


    @Test
    public void shouldFilterListByGCContent() {

        Filter filter = new Filter();
        List<String> keys = new ArrayList<>() {
            {
                add("GC");
            }
        };
        List<String> values = new ArrayList<>() {
            {
                add("50");
            }
        };
        List<String> compares = new ArrayList<>() {
            {
                add(">");
            }
        };
        filter.buildPredicate(keys,values,compares);

        List<Read> suitableRead = shouldCreateNewSample().getReads().filtered(filter.getFilterPredicate());
        assertEquals(1, suitableRead.size());
        assertEquals("read1", shouldCreateNewSample().getReads().filtered(filter.getFilterPredicate()).get(0).getId());

    }

    @Test
    public void shouldFilterListByScore(){
        Filter filter = new Filter();
        List<String> keys = new ArrayList<>() {
            {
                add("Score");
            }
        };
        List<String> values = new ArrayList<>() {
            {
                add("3");
            }
        };
        List<String> compares = new ArrayList<>() {
            {
                add("=");
            }
        };
        filter.buildPredicate(keys,values,compares);

        List<Read> suitableRead = shouldCreateNewSample().getReads().filtered(filter.getFilterPredicate());
        assertEquals(2, suitableRead.size());
        assertEquals("read1", shouldCreateNewSample().getReads().filtered(filter.getFilterPredicate()).get(0).getId());
        assertEquals("read2", shouldCreateNewSample().getReads().filtered(filter.getFilterPredicate()).get(1).getId());
    }

    @Test
    public void shouldFilterListByGCContent2() {
        Filter filter = new Filter();
        List<String> keys = new ArrayList<>() {
            {
                add("GC");
            }
        };
        List<String> values = new ArrayList<>() {
            {
                add("90.0");
            }
        };
        List<String> compares = new ArrayList<>() {
            {
                add("=");
            }
        };
        filter.buildPredicate(keys,values,compares);

        List<Read> suitableRead = shouldCreateNewSample().getReads().filtered(filter.getFilterPredicate());
        assertEquals(1, suitableRead.size());
        assertEquals("read1", shouldCreateNewSample().getReads().filtered(filter.getFilterPredicate()).get(0).getId());

    }

    @Test
    public void shouldFilterListByLength() {
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

        assertEquals(1,shouldCreateNewSample().getReads().filtered(filter.getFilterPredicate()).size());
        assertEquals("read2", shouldCreateNewSample().getReads().filtered(filter.getFilterPredicate()).get(0).getId());
    }



    @Test
    public void shouldFilterByGeneName() {
        Filter filter = new Filter();
        List<String> keys = new ArrayList<>() {
            {
                add("Gene");
            }
        };
        List<String> values = new ArrayList<>() {
            {
                add("ZUCCHINI");
            }
        };
        List<String> compares = new ArrayList<>() {
            {
                add("=");
            }
        };
        filter.buildPredicate(keys,values,compares);

        assertEquals(1, shouldCreateNewSample().getReads().filtered(filter.getFilterPredicate()).size());
        assertEquals("read1", shouldCreateNewSample().getReads().filtered(filter.getFilterPredicate()).get(0).getId());
    }

    @Test
    public void shouldFilterEmptySample() {
        Filter filter = new Filter();
        List<String> keys = new ArrayList<>() {
            {
                add("Gene");
            }
        };
        List<String> values = new ArrayList<>() {
            {
                add("ZUCCHINI");
            }
        };
        List<String> compares = new ArrayList<>() {
            {
                add("=");
            }
        };
        filter.buildPredicate(keys,values,compares);

        assertEquals(0, new Sample().getReads().filtered(filter.getFilterPredicate()).size());
    }

    @Test
    public void shouldNotFilterSample() {
        Filter filter = new Filter();

        assertEquals(2, shouldCreateNewSample().getReads().filtered(filter.getFilterPredicate()).size());
    }

}
