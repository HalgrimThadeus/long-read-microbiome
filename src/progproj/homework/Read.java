package progproj.homework;

import java.util.List;
import java.util.ArrayList;

/**
 * models a Read, which contains the header of the FastaEntry, the id out of the header, the sequence and a list of GFF entries
 * extends the FastAEntry class; adds more information (adds id and list of GFF entries)
 */
public class Read extends FastAEntry{

    /**
     *id out of the header of the FastaEntry
     */
    private String id;

    /**
     *list of GFF entries
     */
    private List<GffEntry> gffEntries;

    /**
     *make a new Read
     * @param header
     * @param sequence
     */
    public Read(String header, String sequence){
        super(header, sequence); //override
        this.id = getIdFromHeader(header);
        this.gffEntries = new ArrayList<GffEntry>(); //initialize gffEntries list, won't have to check if its null
    }
    //old constructor:
    /*
    public Read(FastAEntry fastaEntry){
        super(fastaEntry.getHeader(), fastaEntry.getSequence()); //override
        this.id = getIdFromHeader(fastaEntry.getHeader());
        this.gffEntries = new ArrayList<GffEntry>(); //initialize gffEntries list, won't have to check if its null
    }*/

    /**
     *
     * @return id
     */
    public String getId(){
        return this.id;
    }
    /**
     *
     * @return list of gffEntries
     */
    public List<GffEntry> getGFFEntries() {
        return this.gffEntries;
    }
    
    //TODO: remove unnecessary setters?
    /*
    public void setId(String id){
        this.id = id;
    }
    public void setGffEntries(ArrayList<GffEntry> gffEntries){
        this.gffEntries = gffEntries;
    }
    */

    /**
     * processes id out of the header
     * @param header
     * @return id
     */
    public String getIdFromHeader(String header){
        //starts with 1 to ignore/delete "<" at the beginning of the header & ends before first whitespace " "
        String id = header.substring(1,header.indexOf(' '));
        return id;
    }

    /**
     * adds a GffEntry
     * @param gffEntry
     */
    public void addGffEntries(GffEntry gffEntry) {
        //If its null, generate null
        if(this.gffEntries == null){ //no longer necessary if we initialize an empty list?
            this.gffEntries = new ArrayList<GffEntry>();
        }
        this.gffEntries.add(gffEntry);
    }

}
