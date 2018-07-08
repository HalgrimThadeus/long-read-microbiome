import model.*;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class FilterTest {

    private Sample shouldCreateNewSample() {
        Sample sample = new Sample();

        Read read1 = new Read(">read1 bla bla", "ACTGCGCGCGCGCGCGCGCGCG", 23);
        Map<String,String> attributes = new HashMap<>(){
            {
                put("Fancyness", "quiet amazing");
                put("Stickyness", "doesnt let you down");
                put("Name", "ZUCCHINI");
            }
        };
        read1.addGffEntries(new GffEntry("read1", "Test", "gene", 0, 10, 3, '+', 4, attributes));

        Read read2 = new Read(">read2 bla bla", "ACTAAAAAAA", 22);
        read2.addGffEntries(new GffEntry("read2", "Test", "gene", 0, 10, 3, '+', 4, null));

        sample.addRead(read1);
        sample.addRead(read2);

        return  sample;
    }

    @Test
    public void shouldFilterListByGCContent() {
        FilterBuilder filterBuilder = new FilterBuilder();
        filterBuilder.addMainPredicate(FilterBuilder.isGCContentHigherEq(50.0));
        Filter filter = new Filter("TestFilter", filterBuilder.getMainPredicate());

        List<Read> suitableRead = filter.suitable(shouldCreateNewSample());
        assertEquals(1, suitableRead.size());
        assertEquals("read1", filter.suitable(shouldCreateNewSample()).get(0).getId());

    }

    @Test
    public void shouldFilterListByGCContent2() {
        FilterBuilder filterBuilder = new FilterBuilder();
        filterBuilder.addMainPredicate(FilterBuilder.isGCContentEqual(2000.0/22));
        Filter filter = new Filter("TestFilter", filterBuilder.getMainPredicate());

        List<Read> suitableRead = filter.suitable(shouldCreateNewSample());
        assertEquals(1, suitableRead.size());
        assertEquals("read1", filter.suitable(shouldCreateNewSample()).get(0).getId());

    }

    @Test
    public void shouldFilterListByLength() {

        FilterBuilder filterBuilder = new FilterBuilder();
        filterBuilder.addMainPredicate(FilterBuilder.isLengthEqual(10));
        Filter filter = new Filter("TestFilter", filterBuilder.getMainPredicate());

        assertEquals(1, filter.suitable(shouldCreateNewSample()).size());
        assertEquals("read2", filter.suitable(shouldCreateNewSample()).get(0).getId());
    }

    @Test
    public void shouldFilterListWithTwoStatements() {
        //test THREE
        FilterBuilder filterBuilder = new FilterBuilder();
        filterBuilder.addMainPredicate(FilterBuilder.isGCContentHigherEq(0.0));
        filterBuilder.addMainPredicate(FilterBuilder.isTaxaId(23));
        Filter filter = new Filter("TestFilter", filterBuilder.getMainPredicate());

        assertEquals(1, filter.suitable(shouldCreateNewSample()).size());
        assertEquals("read1", filter.suitable(shouldCreateNewSample()).get(0).getId());
    }

    @Test
    public void shouldFilterListWithTwoStatements2() {
        //test THREE
        FilterBuilder filterBuilder = new FilterBuilder();
        filterBuilder.addMainPredicate(FilterBuilder.isGCContentHigherEq(0.0));
        filterBuilder.addMainPredicate(FilterBuilder.isTaxaId(23));
        filterBuilder.addMainPredicate(FilterBuilder.isTaxaId(22));
        Filter filter = new Filter("TestFilter", filterBuilder.getMainPredicate());

        assertEquals(0, filter.suitable(shouldCreateNewSample()).size());
    }

    @Test
    public void shouldFilterByGeneName() {

        //test FOUR
        FilterBuilder filterBuilder = new FilterBuilder();
        filterBuilder.addMainPredicate(FilterBuilder.isGen("ZUCCHINI"));
        Filter filter = new Filter("TestFilter", filterBuilder.getMainPredicate());

        assertEquals(1, filter.suitable(new Sample()).size());
        assertEquals("read1", filter.suitable(shouldCreateNewSample()).get(0).getId());
    }

    @Test
    public void shouldFilterByGeneName2() {

        //test FOUR
        FilterBuilder filterBuilder = new FilterBuilder();
        filterBuilder.addMainPredicate(FilterBuilder.isGen("sgsd"));
        Filter filter = new Filter("TestFilter", filterBuilder.getMainPredicate());

        assertEquals(0, filter.suitable(new Sample()).size());
    }

    @Test
    public void shouldFilterEmpytSample() {

        //test FOUR
        FilterBuilder filterBuilder = new FilterBuilder();
        filterBuilder.addMainPredicate(FilterBuilder.isLengthGreater(4));
        filterBuilder.addMainPredicate(FilterBuilder.isGCContentHigherEq(0.0));
        Filter filter = new Filter("TestFilter", filterBuilder.getMainPredicate());

        assertEquals(0, filter.suitable(new Sample()).size());
    }

    @Test
    public void shouldFilterNullSample() {
        FilterBuilder filterBuilder = new FilterBuilder();
        filterBuilder.addMainPredicate(FilterBuilder.isLengthGreater(4));
        filterBuilder.addMainPredicate(FilterBuilder.isGCContentHigherEq(0.0));
        Filter filter = new Filter("TestFilter", filterBuilder.getMainPredicate());

        assertEquals(0, filter.suitable(null).size());
    }

    @Test
    public void shouldNotFilterSample() {
        FilterBuilder filterBuilder = new FilterBuilder();
        Filter filter = new Filter("TestFilter", filterBuilder.getMainPredicate());

        assertEquals(2, filter.suitable(shouldCreateNewSample()).size());
    }

}
