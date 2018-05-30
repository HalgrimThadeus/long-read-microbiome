package progproj.homework;

import org.junit.*;
//import org.junit.Test;
import static org.junit.Assert.*;

public class ReadTest {
    @Test //tests getId()-method: is id processed out of the header correctly?
    public void idShouldBeProcessedOutOfTheHeader(){
        Read testRead = new Read();
        assertEquals("wrong id",testRead.getIdFromHeader(),"");
    }
    @Test //tests if the list of gffEntries is initialized correctly and not null
    public void gffEntriesShouldntBeNull(){
        Read testRead = new Read();
        assertNotNull("gffEntries shouldn't be null",testRead.getGFFEntries());
    }
}