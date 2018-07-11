package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Sample{
    /**
     * Contains the List of the Reads in this Sample
     */
    private ObservableList<Read> reads = FXCollections.observableArrayList();

    private String name;
    private String gffFileName;
    private String fastaFileName;
    private String taxaFileName;

    public Sample() {
        this.name = "SampleName not set";
        this.gffFileName = "gffFileName not set";
        this.fastaFileName = "fastaFileName not set";
    }

    public Sample(String gffFileName, String fastaFileName, String taxaFileName) {
        this.name = "SampleName not set";
        this.gffFileName = gffFileName;
        this.fastaFileName = fastaFileName;
        this.taxaFileName = taxaFileName;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the List of Reads
     * @return reads
     */
    public ObservableList<Read> getReads() {
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

    public String getFastaFileName() {
        return fastaFileName;
    }

    public String getGffFileName() {
        return gffFileName;
    }

    /**
     * Set the List of reads to a new List of reads
     * @param newreads
     */
    public void setReads(List<Read> newreads) {
        reads.addAll(newreads);
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
        res += fastaFileName + '\t';
        res += gffFileName + '\t';
        res += taxaFileName + '\n';
        return res;
    }
}
