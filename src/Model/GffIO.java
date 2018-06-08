package Model;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GffIO {

    private Reader reader;

    public GffIO(Reader reader) {
        this.reader = reader;
    }

    public GffIO(String filePath) throws FileNotFoundException {
        this.reader = new FileReader(filePath);
    }

    /**
     * Reads from a GFF file with a given filepath the GFF entries, which are the lines of the file.
     * for that the reader-attribute (from class) is used to read the files
     *
     * @return gffentries: a list of gffentries, which are stored in the file, from which the filepath was given
     * @throws Exception
     */
    public List<GffEntry> readGff() throws Exception {
        List<GffEntry> gffEntries = new ArrayList<>();

        try {
            // Always wrap Reader in BufferedReader.
            if(this.reader == null) {
                throw new Exception("The reader wasn't initiliazed before reading the Gff-File.");
            } else {
                BufferedReader bufferedReader = new BufferedReader(this.reader);

                String line = "";
                String fileHeader = "";

                //read actual content of the gff file
                while ((line = bufferedReader.readLine()) != null) {
                    if (line.startsWith("#")) {
                        fileHeader = fileHeader + line + "\n";
                    } else {
                        String[] lineCols = line.split("\t");
                        String sequence = lineCols[0];
                        String source = lineCols[1];
                        String feature = lineCols[2];

                        //if empty, -1 is stored
                        int start = -1;
                        if (!lineCols[3].equals(".")) {
                            start = Integer.parseInt(lineCols[3]);
                        }
                        int end = -1;
                        if (!lineCols[4].equals(".")) {
                            end = Integer.parseInt(lineCols[4]);
                        }
                        int score = -1;
                        if (!lineCols[5].equals(".")) {
                            score = Integer.parseInt(lineCols[5]);
                        }
                        char strand = lineCols[6].charAt(0);
                        int frame = -1;
                        if (!lineCols[7].equals(".")) {
                            frame = Integer.parseInt(lineCols[7]);
                        }

                        //creating the map for the attributes
                        Map<String, String> attributes = new HashMap<>();

                        String[] attributesArray = lineCols[8].split(";");
                        for (int i = 0; i < attributesArray.length; i++) {
                            String[] keyAttribute = attributesArray[i].split("=");

                            //controls if really only the attribute itself consists of key and value
                            if (keyAttribute.length != 2) {
                                throw new Exception("The file is not correctly formated as a GFF-file. Because attributes and keys not match to eacht other");
                            }
                            //adding to Map of key and value
                            attributes.put(keyAttribute[0], keyAttribute[1]);
                        }

                        gffEntries.add(new GffEntry(sequence, source, feature, start, end, score, strand, frame, attributes));
                    }
                }

                // Always close files.
                bufferedReader.close();
            }
        }
        catch(ArrayIndexOutOfBoundsException ex) {
            throw new Exception("The file is not correctly formated as a GFF-file.", ex);
        }

        return gffEntries;
    }

}
