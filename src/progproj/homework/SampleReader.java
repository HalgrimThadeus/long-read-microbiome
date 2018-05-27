package progproj.homework;


import java.util.ArrayList;

public class SampleReader{

    public static Sample sample;

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
            Read read = new Read();
            read.setId(fastaReader.getId(i+1));
            read.setSequence(fastaReader.getSequence(i+1));

            //Every GFF-Entry with the same id is added to the Read
            for(int j = 0; j < gffReader.getGffEntries().size(); j++){
                GffEntry entry = gffReader.getGffEntry(j);
                if(gffReader.getGffEntry(j).getSequence().equals(read.getId())){
                    ArrayList<GffEntry> newEntry= read.getGFFEntries();
                    newEntry.add(entry);
                    read.setGffEntries(newEntry);
                }
            }
            //Adds the read to the new Sample
            sample.addReads(read);
        }

    }
}
