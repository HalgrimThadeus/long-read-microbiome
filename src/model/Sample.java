package model;

import java.util.ArrayList;
import java.util.List;

public class Sample{
    /**
     * Contains the List of the Reads in this Sample
     */
    private List<Read> reads = new ArrayList<>();
    private String name;
    private String gffFilePath;
    private String fastaFilePath;
    private String taxaFilePath;

    public Sample() {
        this.name = "SampleName not set";
        this.gffFilePath = "gffFilePath not set";
        this.fastaFilePath = "fastaFilePath not set";
        this.reads = new ArrayList<>();
    }

    public Sample(String gffFilePath, String fastaFilePath, String taxaFilePath) {
        this.name = "SampleName not set";
        this.gffFilePath = gffFilePath;
        this.fastaFilePath = fastaFilePath;
        this.taxaFilePath = taxaFilePath;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the List of Reads
     * @return reads
     */
    public List<Read> getReads() {
        return reads;
    }

    public List<String> getReadNames() {
        List<String> readNames = new ArrayList<>();
        for (Read r: this.reads) {
            readNames.add(r.getId());
        }
        return readNames;
    }

    public String getName() {
        return name;
    }

    public String getFastaFilePath() {
        return fastaFilePath;
    }

    public String getGffFilePath() {
        return gffFilePath;
    }

    /**
     * Set the List of reads to a new List of reads
     * @param newreads
     */
    public void setReads(List<Read> newreads) {
        reads = newreads;
    }

    /**
     * Add a new Read to to current List of Reads
     * @param newRead
     */
    public void addRead(Read newRead){
        reads.add(newRead);
    }

    /**
     * @return a two-line-String with the FilePaths of a Sample, SampleName \n FASTAFilePath \t GFFFilePath \t CSVFilePath
     */
    @Override
    public String toString(){
        String res = name + '\n';
        res += fastaFilePath + '\t';
        res += gffFilePath + '\t';
        res += taxaFilePath + '\n';
        return res;
    }
}
