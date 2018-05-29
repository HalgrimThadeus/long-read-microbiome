package progproj.homework;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GffIO {

    //file content in java datatypes
    private String fileHeader;

    public List<GffEntry> readGff(String filePath) {
        List<GffEntry> gffEntries = new ArrayList<GffEntry>();

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = new FileReader(filePath);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line = null;
            this.fileHeader = "";

            //read actual content of the gff file
            while((line = bufferedReader.readLine()) != null) {
                if ( line.startsWith("#")) {
                    this.fileHeader = this.fileHeader + line + "\n";
                } else {
                    String[] lineCols = line.split("\t");
                    String sequence = lineCols[0];
                    String source = lineCols[1];
                    String feature = lineCols[2];

                    //wenn leer, dann wird eine -1 gespeichert
                    int start = -1;
                    if(!lineCols[3].equals("."))  {
                        start = Integer.parseInt(lineCols[3]);
                    }
                    int end = -1;
                    if(!lineCols[4].equals("."))  {
                        end = Integer.parseInt(lineCols[4]);
                    }
                    int score = -1;
                    if(!lineCols[5].equals("."))  {
                        score = Integer.parseInt(lineCols[5]);
                    }
                    char strand = lineCols[6].charAt(0);
                    int frame = -1;
                    if(!lineCols[7].equals("."))  {
                        frame = Integer.parseInt(lineCols[7]);
                    }


                    ArrayList<String> attributes = new ArrayList<>();
                    String[] attributes_array= lineCols[8].split("; ");
                    for (int i = 0; i < attributes_array.length; i++) {
                        attributes.add(attributes_array[i]);
                    }

                    gffEntries.add(new GffEntry(sequence, source, feature, start, end, score, strand, frame , attributes));
                }
            }

            // Always close files.
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + filePath + "'");
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + filePath + "'");
        }
        catch(ArrayIndexOutOfBoundsException ex) {
            System.out.println("The file '" + filePath + "' is not correctly formated as a GFF-file.");
        }
        return gffEntries;
    }

    public String getFileHeader() {
        return this.fileHeader;
    }
}
