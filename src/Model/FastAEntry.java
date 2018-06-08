package Model;

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

}
