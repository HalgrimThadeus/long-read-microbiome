package progproj.homework;

import org.junit.*;
\\import org.junit.Test;
import static org.junit.Assert.*;

public class ReadTest {
    @Test //tests getId()-method: is id processed out of the header correctly?
    public void idShouldBeProcessedOutOfTheHeader(){
        FastAEntry testFastaEntry = new FastAEntry(">blabla bla bla","AAAAAAAAAAAAA");
        Read testRead = new Read(testFastaEntry);
        assertEquals("wrong id","blabla",testRead.getIdFromHeader(testRead.getHeader()));
    }
    @Test //tests if the list of gffEntries is initialized correctly and not null
    public void gffEntriesShouldntBeNull(){
        FastAEntry testFastaEntry = new FastAEntry(">blabla bla bla","AAAAAAAAAAAAA");
        Read testRead = new Read(testFastaEntry);
        assertNotNull("gffEntries shouldn't be null",testRead.getGFFEntries());
    }
}