package progproj.homework;

import java.util.ArrayList;

public class GffEntry {

    private String sequence; //The name of the sequence where the feature is located

    private String source; //Keyword identifying the source of the feature, like a program (e.g. Augustus or RepeatMasker) or an organization (like TAIR)

    private String feature; //feature 	The feature type name, like "gene" or "exon". In a well structured GFF file, all the children features always follow their parents in a single block (so all exons of a transcript are put after their parent "transcript" feature line and before any other parent transcript line). In GFF3, all features and their relationships should be compatible with the standards released by the Sequence Ontology Project

    private int start; //Genomic start of the feature, with a 1-base offset. This is in contrast with other 0-offset half-open sequence formats, like BED files.

    private int end; //Genomic end of the feature, with a 1-base offset. This is the same end coordinate as it is in 0-offset half-open sequence formats, like BED files.[citation needed]

    private int score; //score 	Numeric value that generally indicates the confidence of the source on the annotated feature. A value of "." (a dot) is used to define a null value.

    private char strand; //Single character that indicates the Sense (molecular biology) strand of the feature; it can assume the values of "+" (positive, or 5'->3'), "-", (negative, or 3'->5'), "." (undetermined).

    private int frame; //(GTF, GFF2) or phase (GFF3) 	Frame or phase of CDS features; it can be either one of 0, 1, 2 (for CDS features) or "." (for everything else). Frame and Phase are not the same, See following subsection.

    private ArrayList<String> attributes; //All the other information pertaining to this feature. The format, structure and content of this field is the one which varies the most between the three competing file formats.

    public GffEntry(String sequence, String source, String feature, int start, int end, int score, char strand, int frame, ArrayList<String> attributes) {
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

    public String getSequence() {
        return sequence;
    }

    public String getSource() {
        return source;
    }

    public String getFeature() {
        return feature;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public int getScore() {
        return score;
    }

    public char getStrand() {
        return strand;
    }

    public int getFrame() {
        return frame;
    }

    public ArrayList<String> getAttributes() {
        return attributes;
    }

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

        for (String attribute:this.attributes) {
            output = output + attribute + "; ";
        }

        return output;
    }
}
