package progproj.homework;


import java.util.ArrayList;

public class SampleReader{

    public static Sample read(String filepathFastA, String filePathGff) throws Exception {
        Sample sample = new Sample();
        //Read Gff File
        GffIO gffReader = new GffIO();
        gffReader.readGff(filePathGff);

        //Read FastA File
        FastAIO fastaReader = new FastAIO();
        fastaReader.readFastA(filepathFastA);

        //Iterates throught all FastA Entries
        for(int i = 0; i < fastaReader.getSize(); i++){

            //Creating new Read and adding id and sequence of FastA
            Read read = new Read(fastaReader.getFastaEntry(i));

            for(int j = 0; j < gffReader.getGffEntries().size(); j++){
                GffEntry entry = gffReader.getGffEntry(j);
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
