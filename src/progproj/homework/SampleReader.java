package progproj.homework;


import java.util.ArrayList;

public class SampleReader{

    private static Sample sample;

    public static void read(String filepathFastA, String filePathGff) throws Exception {
        //Read Gff File
        GffIO gffReader = new GffIO();
        gffReader.readGff(filePathGff);

        //Read FastA File
        FastAIO fastaReader = new FastAIO();
        fastaReader.readFastA(filepathFastA);

        //New Sample Object
        sample = new Sample();

        //Iterates throught all FastA Entries
        for(int i = 0; i < fastaReader.getSize(); i++){

            //Creating new Read and adding id and sequence of FastA
            //A Constructor would be nice
            Read read = new Read();
            //i+1 because headers in FastAIO are from 1 to n... . Consistence would be nice
            read.setId(fastaReader.getId(i+1));
            read.setSequence(fastaReader.getSequence(i+1));
            read.setGffEntries(new ArrayList<GffEntry>());

            //Every GFF-Entry with the same id is added to the Read
            for(int j = 0; j < gffReader.getGffEntries().size(); j++){
                GffEntry entry = gffReader.getGffEntry(j);
                if(entry.getSequence().equals(read.getId())){
                    read.addGffEntries(entry);
                }
            }
            //Adds the read to the new Sample
            sample.addReads(read);
        }

    }

    //Returns the sample if sonething was read.
    public static Sample getSample() throws Exception {
        if(sample == null){
            throw new Exception("No sample was read");
        }
        else{
            return sample;
        }
    }
}
