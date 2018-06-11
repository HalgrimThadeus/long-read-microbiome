package Model.IO;

import Model.FastAEntry;

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
    /**
     * With a given Reader this method is creating 2 lists with the input from the reader.
     * Split up into header and sequence lists.
     *     * @param r
     * @throws IOException
     */

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
                        sequence += thisLine;
                        if(nextLine == null)
                            break;
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
    // not jet tested
    public void writeFastA(Writer w, List<FastAEntry> outputList) throws IOException {

        BufferedWriter writer = new BufferedWriter(w);

        for(int i = 0; i < outputList.size(); i++){
            writer.write(outputList.get(i).getHeader());
            writer.newLine();
            writer.write(outputList.get(i).getSequence());
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
    public void writeFastA(String path, List<FastAEntry> outputList) throws IOException {
        Writer writer = new FileWriter(path);
        writeFastA(writer, outputList);
    }
}
