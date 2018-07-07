import model.Filter;
import model.GffEntry;
import model.Read;
import model.Sample;
import static org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FilterTest {

    private Sample shouldCreateNewSample() {
        Sample sample = new Sample();

        Read read1 = new Read(">read1 bla bla", "ACTGCGCGCGCGCGCGCGCGCG");
        Map<String,String> attributes = new HashMap<>(){
            {
                put("Fancyness", "quiet amazing");
                put("Stickyness", "doesnt let you down");
            }
        };
        read1.addGffEntries(new GffEntry("read1", "Test", "gene", 0, 10, 3, '+', 4, attributes));

        Read read2 = new Read(">read2 bla bla", "ACTAAAAAAA");
        read2.addGffEntries(new GffEntry("read1", "Test", "gene", 0, 10, 3, '+', 4, null));

        sample.addRead(read1);
        sample.addRead(read2);

        return  sample;
    }

    @Test
    public void shouldFilterList() {

        Filter filter = new Filter("TestFilter",
                new ArrayList<>() {
                    {
                        add("Length");
                        add("GCContent");
                    }
                },
                new ArrayList<>() {
                    {
                        add("10");
                        add("5");
                    }
                });

        List<Read> suitableRead = filter.suitable(shouldCreateNewSample());
        assertEquals(1, suitableRead.size());
        assertEquals("read2", filter.suitable(shouldCreateNewSample()).get(0).getId());

    }

    @Test
    public void shouldFilterListByGCConttent() {

        Filter filter = new Filter("TestFilter",
                new ArrayList<>() {
                    {
                        add("Length");
                        add("GCContent");
                    }
                },
                new ArrayList<>() {
                    {
                        add("10");
                        add("20");
                    }
                });
        assertEquals(1, filter.suitable(shouldCreateNewSample()).size());
        assertEquals("read1", filter.suitable(shouldCreateNewSample()).get(0).getId());
    }

    @Test
    public void shouldFilterListByAttribute() {
        //test THREE
        Filter filter = new Filter("TestFilter",
                new ArrayList<>() {
                    {
                        add("Fancyness");
                    }
                },
                new ArrayList<>() {
                    {
                        add("quiet amazing");
                    }
                });
        assertEquals(1, filter.suitable(shouldCreateNewSample()).size());
    }

    @Test
    public void shouldFilterEmpytSample() {

        //test FOUR
        Filter filter = new Filter("TestFilter",
                new ArrayList<>() {
                    {
                        add("Fancyness");
                    }
                },
                new ArrayList<>() {
                    {
                        add("quiet amazing");
                    }
                });
        assertEquals(0, filter.suitable(new Sample()).size());
    }

    @Test
    public void shouldFilterNullSample() {
        Filter filter = new Filter("TestFilter",
                new ArrayList<>() {
                    {
                        add("Fancyness");
                    }
                },
                new ArrayList<>() {
                    {
                        add("quiet amazing");
                    }
                });

        //MAYBE SOMETHING ELSE SHOULD BE EXPECTED
        assertEquals(0, filter.suitable(null).size());
    }

    @Test
    public void shouldNotFilterSample() {
        Filter filter = new Filter("TestFilter",
                null, null);

        //MAYBE SOMETHING ELSE SHOULD BE EXPECTED
        assertEquals(2, filter.suitable(shouldCreateNewSample()).size());
    }

}
