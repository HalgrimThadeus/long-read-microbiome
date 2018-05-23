package homework;

import java.util.ArrayList;

public class Read {

    private String id;
    private String sequence;
    private ArrayList<GffEntry> gffEntries;

    public String getId(){
        return this.id;
    }
    public String getSequence(){
        return this.sequence;
    }
    public ArrayList<GffEntry> getGFFEntries() {
        return this.gffEntries;
    }

}
