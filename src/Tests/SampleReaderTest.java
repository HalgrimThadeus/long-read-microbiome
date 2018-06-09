
import Model.IO.SampleReader;
import org.junit.Test;

import java.io.FileNotFoundException;

public class SampleReaderTest {


    /*
    Hier müssten temporäre Testfiles erstellt werden ://
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
    */

    @Test
    public void wrongFilePath(){
        try {
            SampleReader.read("asdf", "asdf");
        } catch (Exception e) {
            assert e.getMessage().equals("asdf (Das System kann die angegebene Datei nicht finden)");
        }
    }

}
