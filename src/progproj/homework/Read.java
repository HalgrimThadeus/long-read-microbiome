package progproj.homework;

import java.util.List;
import java.util.ArrayList;

public class Read extends FastAEntry{

    //private String header; //inherited from superclass FastAEntry
    //private String sequence; //inherited from superclass FastAEntry - should be in superclass??
    private String id; //id out of header
    private List<GffEntry> gffEntries;//list of GFF entries

    public Read(FastAEntry fastaEntry){
        super(fastaEntry.getHeader(),fastaEntry.getSequence());
        this.id = getIdFromHeader(fastaEntry.getHeader());
        //initialize gffEntries list, so that we won't have to check if its null all the time
        this.gffEntries = new ArrayList<GffEntry>();
    }

    //public String getSequence(){return this.sequence;} //should be in superclass??
    public String getId(){
        return this.id;
    }
    public List<GffEntry> getGFFEntries() {
        return this.gffEntries;
    }
    
    //remove unnecessary setters?
    //public void setSequence(String sequence){this.sequence = sequence;} //should be in superclass??
    public void setId(String id){
        this.id = id;
    }
    public void setGffEntries(ArrayList<GffEntry> gffEntries){
        this.gffEntries = gffEntries;
    }

    //processes id out of the header; id = first in header until first whitespace & delete first sign <
    private String getIdFromHeader(String header){
        //String id = header.split("/s")[0];
        String id = "";
        return id;
    }

    //Added ein Gff Entry
    public void addGffEntries(GffEntry gffEntry) {
        //If its null, generate null
        if(this.gffEntries == null){ //no longer necessary if we initialize an empty list
            this.gffEntries = new ArrayList<GffEntry>();
        }
        this.gffEntries.add(gffEntry);
    }

}
