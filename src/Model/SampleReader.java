package Model;


import java.util.List;
/**
 * $SampleReader
 *
 * The Sample Reader Class creates a Sample out of a FastA and Gff File.
 * It takes the FastAEntrys from FastAIO and the GffEntrys from GffIO, then expand the FastAEntrys to Reads
 * by adding all Gff-Entrys for this Sequence.
 *
 * @author Manuel Glöckler
 */
public class SampleReader{

    /**
     * read Method for making Samples out of an FastA and Gff File
     *
     * @param filepathFastA File-Path to the file as String
     * @param filePathGff File-Path to the file as String
     *
     * @return Sample constructed out of the Gff/FastA File
     *
     * @throws Exception if the File  was not found or...
     */
    public static Sample read(String filepathFastA, String filePathGff) throws Exception {
        //New Sample is generated
        Sample sample = new Sample();

        //Read Gff File
        GffIO gffReader = new GffIO(filePathGff);
        List<GffEntry> gffEntries = gffReader.readGff();

        //Read FastA File
        FastAIO fastaReader = new FastAIO();
        fastaReader.readFastA(filepathFastA);
        List<FastAEntry> fastaEntries = fastaReader.readFastA(filepathFastA);

        //Iterates throught all FastA Entries
        for(int i = 0; i < fastaEntries.size(); i++){

            //Creating new Read and adding id and sequence of FastA
            Read read = new Read(fastaEntries.get(i).getHeader(), fastaEntries.get(i).getSequence());

            for(int j = 0; j < gffEntries.size(); j++){
                GffEntry entry = gffEntries.get(j);
                if(entry.getSequence().equals(read.getId())){
                    read.addGffEntries(entry);
                }
            }
            //Adds the read to the new Sample
            sample.addReads(read);
        }
        return  sample;
    }
}
