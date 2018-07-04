package model;

/**
 *This class models a FastAEntry, so it's a seqeunce with its header (incl. title)
 * It is the motherclass of a Read, which gets additional information from a GFF-File
 *
 **/

 public class FastAEntry {
    /**
     * Header of the FastaEntry
     */
    private String header;
    /**
     * Sequence of a FastaSequece
     */
    private String sequence;

    /**
     * GC content for filter:
     */
    private double gc_content;
    /**
     * Make a new FastaEntry
     * @param header
     * @param sequence
     */
    public FastAEntry(String header, String sequence){
        this.header = header;
        this.sequence = sequence;
    }

    /**
     *
     * @return Header of a FastAEntry
     */
    public String getHeader() {
        return header;
    }

    /**
     *
     * @return Sequence of a FastAEntry
     */
    public String getSequence(){
        return sequence;
    }

    public double calculateGCContent(){
        int cg = 0;
        for(char i: sequence.toCharArray()){
            if((i == 'C') || (i == 'G')){
                cg++;
            }
        }
        return gc_content = cg/sequence.length() * 100;
    }
    public double getGc_content(){
        return calculateGCContent();
    }

}
