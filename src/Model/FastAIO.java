package Model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * $FastAIO
 *
 * This is a FastA class for reading a FastA and saving the lines in 2 different Lists.
 * One List for the Header an the other one for the Sequence.
 *
 * @author Patrick Schirm
 */
public class FastAIO {

    /*TODO Sollte wie in der GffIO nur die FastA Datei lesen/schreiben und eine Liste von fastAEntrys zurückgegeben.
      TODO Geter können entfernt werden, da diese bereits in FastAEntry sind!
     */
    private List<String> headerList;
    private List<String> sequenceList;

    /**
     * For new FastaA which will be filled with the add method
     */
    public FastAIO() {

    }

    /**
     * Constructor for a FastA file that need to be read
     *
     * @param r
     * @throws IOException
     */
    public FastAIO(Reader r) throws IOException {
        readFastA(r);
        r.close();
    }

    /**
     * Constructor for a given path
     *
     * @param path
     * @throws IOException
     */
    public FastAIO(String path) throws IOException {
        readFastA(path);
    }

    //TODO Modifizieren das FastAEntry erzeugt und zurückegeben werden
    /**
     * With a given Reader this method is creating 2 lists with the input from the reader.
     * Split up into header and sequence lists.
     *     * @param r
     * @throws IOException
     */
    //Bereits modifiziert gibt Liste zurück
    /*
    public void readFastA(Reader r) throws IOException {
        String thisLine;
        String nextLine;
        String accumulator = "";

        BufferedReader reader = new BufferedReader(r);

        if ( (thisLine = reader.readLine()) != null) {
            nextLine = reader.readLine();
            while (nextLine != null) {
                if ( thisLine.startsWith(">") && nextLine.startsWith(">")) {
                    thisLine = nextLine;
                    nextLine = reader.readLine();
                } else if ( thisLine.startsWith(">") && !(nextLine.startsWith(">"))) {
                    headerList.add(thisLine);
                    thisLine = nextLine;
                    nextLine = reader.readLine();
                } else if ( !(thisLine.startsWith(">")) && !(nextLine.startsWith(">"))) {
                    accumulator += thisLine;
                    thisLine = nextLine;
                    nextLine = reader.readLine();
                } else {
                    accumulator += thisLine;
                    sequenceList.add(accumulator);
                    accumulator = "";
                    thisLine = nextLine;
                    nextLine = reader.readLine();
                }
            }
            accumulator += thisLine;
            sequenceList.add(accumulator);
        }

        reader.close();
    }
    */
    //TODO make it better, was from error fixing
    public List<FastAEntry> readFastA(Reader r) throws IOException{
        String thisLine;
        String nextLine;
        List<FastAEntry> fastAEntries = new ArrayList<>();

        BufferedReader reader = new BufferedReader(r);


        if((thisLine = reader.readLine()) != null){
            nextLine = reader.readLine();
            while (thisLine != null && nextLine != null) {
                if (thisLine.startsWith(">") && nextLine.startsWith(">")) {
                    thisLine = nextLine;
                    nextLine = reader.readLine();
                } else if (thisLine.startsWith(">") && !(nextLine.startsWith(">"))) {
                    String header;
                    String sequence = "";

                    header = thisLine;

                    while (!nextLine.startsWith(">")) {
                        thisLine = nextLine;
                        nextLine = reader.readLine();
                        if(nextLine == null)
                            break;
                        sequence += thisLine;
                    }
                    thisLine = nextLine;
                    nextLine = reader.readLine();

                    fastAEntries.add(new FastAEntry(header, sequence));
                }
            }
        }

        return fastAEntries;
    }

    /**
     * read a FastA file with a path
     *
     * @param filePath
     * @throws IOException
     */
    public List<FastAEntry> readFastA(String filePath) throws IOException {
        FileReader reader = new FileReader(filePath);
        return readFastA(reader);
    }

    /**
     * Using a Writer to write out the Lists in FastA format.
     *
     * @param w
     * @throws IOException
     */
    public void writeFastA(Writer w) throws IOException {

        BufferedWriter writer = new BufferedWriter(w);

        for(int i = 0; i < headerList.size(); i++){
            writer.write(headerList.get(i));
            writer.newLine();
            writer.write(sequenceList.get(i));
            writer.newLine();
        }
        writer.flush();
        writer.close();
    }

    /**
     * Using a Writer with given path
     *
     * @param path
     * @throws IOException
     */
    public void writeFastA(String path) throws IOException {
        Writer writer = new FileWriter(path);
        writeFastA(writer);
    }

    //TODO getter können eigentlich alle entfernt werden, da diese schon in FastAEntrys

    /**
     * is giving the header at given position
     *
     * @param i
     * @return
     * @throws Exception
     */
    public String getSequence(int i) throws Exception {
        if (sequenceList.size() < i -1) {
            throw new Exception("No Such Sequence");
        }
        return sequenceList.get(i - 1);
    }

    /**
     * is giving the header at given position
     *
     * @param i
     * @return
     * @throws Exception
     */
    public String getHeader(int i) throws Exception {
        if (headerList.size() < i -1) {
            throw new Exception("No such Header");
        }
        return headerList.get(i - 1);
    }


    /**
     * returns the size of the header list
     *
     * @return
     */
    public int getSize(){
        return (headerList.size());
    }

    /**
     * adds header and sequence at the end of the corresponding lists
     *
     * @param header
     * @param sequence
     */
    public void add(String header, String sequence) {
        headerList.add(header);
        sequenceList.add(sequence);
    }
}
