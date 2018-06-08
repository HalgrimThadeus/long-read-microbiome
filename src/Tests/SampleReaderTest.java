package Tests;

import Model.*;
import Model.IO.FastAIO;
import Model.IO.SampleReader;
import org.junit.Test;
import progproj.homework.*;

import java.util.List;

public class SampleReaderTest {

    /**
     * Because of the Implementation the Reads in the Sample should have the same order as the FastEntries.
     * So the following asserts should always be true
     *
     * @param fastaFile File Path to fastA File
     * @param gffFile File Path to Gff File
     */
    @Test
    public void allFastAEntriesAreInSample(String fastaFile, String gffFile) {
        try {
            Sample sample = SampleReader.read(fastaFile, gffFile);
            FastAIO fastaReader = new FastAIO(fastaFile);
            List<FastAEntry> fastAEntries = fastaReader.readFastA(fastaFile);

            assert fastAEntries.size() == sample.getReads().size() :
                    "FastA Entries und Reads haben unterschiedliche Größe";

            for(int i = 0; i < fastAEntries.size(); i++){
                FastAEntry fastaEntry = fastAEntries.get(i);
                Read read = sample.getReads().get(i);

                assert fastaEntry.getHeader().contains(read.getId()) : "Einige IDs fehlen oder sind nicht korrekt";
                assert fastaEntry.getSequence().equals(read.getSequence()) : "Sequenzen sind nicht gleich";
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Test could not be done because the FilePaths are Wrong");
        }
    }
}
