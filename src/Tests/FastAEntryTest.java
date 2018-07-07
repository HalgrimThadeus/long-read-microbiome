import model.FastAEntry;
import org.junit.Test;

import static org.junit.Assert.*;

public class FastAEntryTest {

    @Test
    public void shouldGetGCcontent() {
        FastAEntry entry = new FastAEntry(">id1 fsdfsdfsdf", "CGCGCGCG");
        assertEquals(100.0, entry.getGCcontent(), 0.01);

        entry = new FastAEntry(">id1 fsdfsdfsdf", "ACGC");
        assertEquals(75.0, entry.getGCcontent(), 0.01);

        entry = new FastAEntry(">id1 fsdfsdfsdf", "AAAAA");
        assertEquals(0.0, entry.getGCcontent(), 0.01);

        entry = new FastAEntry(">id1 fsdfsdfsdf", "hallo");
        assertEquals(0.0, entry.getGCcontent(), 0.01);
    }
}