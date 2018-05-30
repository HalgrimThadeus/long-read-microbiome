package progproj.homework;

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

    public String getHeader() {
        return header;
    }
    public String getSequence(){
        return sequence;
    }
    /** pussible getID, if necessairy
    public String getID(){
        String res = "NoID";
        int i = 0;
        if(sequence != null) {
            while (sequence.charAt(i) != '\t') {
                res += sequence.charAt(i);
                i++;
            }
            res = res.substring(1);
        }
        return res;
    }
     **/
}
