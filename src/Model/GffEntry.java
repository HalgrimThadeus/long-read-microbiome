package Model;

import java.lang.String;
import java.util.Map;

public class GffEntry {
    /**
     * The name of the sequence where the feature is located
     */
    private String sequence;
    /**
     * Keyword identifying the source of the feature, like a program (e.g. Augustus or RepeatMasker) or an organization (like TAIR)
     */
    private String source;
    /**
     * feature 	The feature type name, like "gene" or "exon". In a well structured GFF file, all the children features always follow their parents in a single block (so all exons of a transcript are put after their parent "transcript" feature line and before any other parent transcript line). In GFF3, all features and their relationships should be compatible with the standards released by the Sequence Ontology Project
     */
    private String feature;
    /**
     * Genomic start of the feature, with a 1-base offset. This is in contrast with other 0-offset half-open sequence formats, like BED files.
     */
    private int start;
    /**
     * Genomic end of the feature, with a 1-base offset. This is the same end coordinate as it is in 0-offset half-open sequence formats, like BED files.[citation needed]
     */
    private int end;
    /**
     * score 	Numeric value that generally indicates the confidence of the source on the annotated feature. A value of "." (a dot) is used to define a null value.
     */
    private double score;
    /**
     * Single character that indicates the Sense (molecular biology) strand of the feature; it can assume the values of "+" (positive, or 5'->3'), "-", (negative, or 3'->5'), "." (undetermined).
     */
    private char strand;
    /**
     * (GTF, GFF2) or phase (GFF3) 	Frame or phase of CDS features; it can be either one of 0, 1, 2 (for CDS features) or "." (for everything else). Frame and Phase are not the same, See following subsection.
     */
    private int frame;

    /**
     * length of the GffEntry calculated through end-start
     */
    private int length;

    private Map<String,String> attributes; //All the other information pertaining to this feature. The format, structure and content of this field is the one which varies the most between the three competing file formats.

    /**
     * Make a new GffEntry with all the typical properties of a Feature in a GFF-File
     * @param sequence
     * @param source
     * @param feature
     * @param start
     * @param end
     * @param score
     * @param strand
     * @param frame
     * @param attributes
     */
    public GffEntry(String sequence, String source, String feature, int start, int end, int score, char strand, int frame, Map<String,String> attributes) {
            this.sequence = sequence;
            this.source = source;
            this.feature = feature;
            this.start = start;
            this.end = end;
            this.score = score;
        this.strand = strand;
        this.frame = frame;
        this.attributes = attributes;
    }

    /**
     *
     * @return Sequence of a GffEntry
     */
    public String getSequence() {
        return sequence;
    }

    /**
     *
     * @return name of the program that generated this feature, or the data source
     */
    public String getSource() {
        return source;
    }

    /**
     *
      * @return the feature type name, e. g. Gene, Variation, Similarity
     */
    public String getFeature() {
        return feature;
    }

    /**
     *
     * @return Start position of the feature
     */
    public int getStart() {
        return start;
    }

    /**
     *
     * @return End Position of the feature
     */
    public int getEnd() {
        return end;
    }

    /**
     *
     * @return the score of the feature
     */
    public double getScore() {
        return score;
    }

    /**
     *
     * @return a indicator (+ or -) for the Strand
     */
    public char getStrand() {
        return strand;
    }

    /**
     *
     * @return 0, 1 or 2 that indicates the used frame, so which Triplet-Iteration was used
     */
    public int getFrame() {
        return frame;
    }

    public Map<String,String> getAttributes() {
        return attributes;
    }
    public String getAttributefromKey(String key){
        return attributes.get(key);
    }

    /**
     * sets up a string out of the gffEntry
     * @return
     */
    @Override
    public String toString() {
        String output = this.sequence + "\t"
                + this.source + "\t"
                + this.feature + "\t"
                + this.start + "\t"
                + this.end + "\t"
                + this.score + "\t"
                + this.strand + "\t"
                + this.frame + "\t";

        for (String attribute:this.attributes.values()) {
            output = output + attribute + "; ";
        }

        return output;
    }

    public void setLength(){
        length = getEnd()-getStart();
    }

    public int getLength(){
        return length;
    }
}
