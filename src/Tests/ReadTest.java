package Tests;

import org.junit.*;
import progproj.homework.FastAEntry;
import progproj.homework.Read;
//import org.junit.Test;
import static org.junit.Assert.*;

public class ReadTest {
    @Test //tests getIdFromHeader()-method: is id processed out of the header correctly?
    public void idShouldBeProcessedOutOfTheHeader(){
        FastAEntry testFastaEntry = new FastAEntry(">blabla bla bla","AAAAAAAAAAAAA");
        //old: Read testRead = new Read(testFastaEntry);
        Read testRead = new Read(testFastaEntry.getHeader(),testFastaEntry.getSequence());
        //positive case:
        assertEquals("wrong id","blabla",testRead.getIdFromHeader(testRead.getHeader()));
        //negative case: ??
        assertNotEquals("wrong id","blabla bla bla",testRead.getIdFromHeader(testRead.getHeader()));
        assertNotEquals("wrong id",">blabla",testRead.getIdFromHeader(testRead.getHeader()));
    }

    @Test //tests if the list of gffEntries is initialized correctly and not null
    public void gffEntriesShouldntBeNull(){
        FastAEntry testFastaEntry = new FastAEntry(">blabla bla bla","AAAAAAAAAAAAA");
        //old: Read testRead = new Read(testFastaEntry);
        Read testRead = new Read(testFastaEntry.getHeader(),testFastaEntry.getSequence());
        assertNotNull("gffEntries shouldn't be null",testRead.getGFFEntries());
    }
}