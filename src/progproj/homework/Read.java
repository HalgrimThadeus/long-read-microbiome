package progproj.homework;

import java.util.ArrayList;

public class Read {

    private String id;
    private String sequence;
    private ArrayList<GffEntry> gffEntries;//list of GFF entries

    public String getId(){
        return this.id;
    }
    public String getSequence(){
        return this.sequence;
    }
    public ArrayList<GffEntry> getGFFEntries() {
        return this.gffEntries;
    }
    
    public void setId(String id){
        this.id = id;
    }
    public void setSequence(String sequence){
        this.sequence = sequence;
    }
    public void setGffEntries(ArrayList<GffEntry> gffEntries){
        this.gffEntries = gffEntries;
    }

    //Added ein Gff Entry
    public void addGffEntries(GffEntry gffEntry) {
        //If its null, generate null
        if(this.gffEntries == null){
            this.gffEntries = new ArrayList<GffEntry>();
        }
        this.gffEntries.add(gffEntry);
    }

}
