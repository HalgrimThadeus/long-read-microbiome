package model.io;


import model.FastAEntry;
import model.GffEntry;
import model.Read;
import model.Sample;

import java.io.FileReader;
import java.io.Reader;
import java.util.List;
import java.util.Map;

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
     * Takes the FilePath as String an runs read with Readers
     * @param filePathFasta
     * @param filePathGff
     * @return
     * @throws Exception
     */
    public static Sample read(String filePathFasta, String filePathGff, String filePathTaxa) throws Exception {
        return read(new FileReader(filePathFasta), new FileReader(filePathGff), new FileReader(filePathTaxa), filePathGff, filePathFasta);
    }

    public static Sample read(Reader fasta, Reader gff, Reader taxa) throws Exception{
        return read(fasta, gff, taxa, "No GFF File Name Added", "No Fasta File Name Added");
    }

    /**
     * read Method for making Samples out of an FastA and Gff File
     *
     * @param fasta File-Path to the file as String
     * @param gff File-Path to the file as String
     *
     * @return Sample constructed out of the Gff/FastA File
     *
     * @throws Exception if the File  was not found or...
     */
    public static Sample read(Reader fasta, Reader gff, Reader taxa, String gffFileName, String fastaFileName) throws Exception {
        //New Sample is generated
        Sample sample = new Sample(gffFileName, fastaFileName);
        Map<String, Integer> taxaId;

        CsvIO  taxaIds = new CsvIO(taxa);
        taxaId = taxaIds.readCsv();

        //Read Gff File
        GffIO gffReader = new GffIO(gff);
        List<GffEntry> gffEntries = gffReader.readGff();

        //Read FastA File
        FastAIO fastaReader = new FastAIO();
        List<FastAEntry> fastaEntries = fastaReader.readFastA(fasta);

        //Iterates throught all FastA Entries and creates new Reads
        for(int i = 0; i < fastaEntries.size(); i++){

            //Creating new Read and adding id and sequence of FastA
            String header = fastaEntries.get(i).getHeader();
            Read read = new Read(header, fastaEntries.get(i).getSequence(), taxaId);

            for(int j = 0; j < gffEntries.size(); j++){
                GffEntry entry = gffEntries.get(j);
                if(entry.getSequence().equals(read.getId())){
                    read.addGffEntries(entry);
                }
            }
            //Adds the read to the new Sample
            sample.addRead(read);
        }
        return  sample;
    }
}
