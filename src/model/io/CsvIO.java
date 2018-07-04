package model.io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

/**
 * This is a io-class for the CSV-Files and grabs the needed data
 * (only takes data of first two columns, which should be the ReadName and the TaxonID)
 */
public class CsvIO {
    private Reader reader;

    public CsvIO(Reader reader) {
        this.reader = reader;
    }

    public CsvIO(String filePath) throws FileNotFoundException {
        this.reader = new FileReader(filePath);
    }

    /**
     * This map generates the needed Association of a correpsonding csv-file.
     * @return HashMap with the Readname(key) to TaxonID(value) association
     * @throws Exception
     */
    public Map<String, Integer> readCsv() throws Exception{

        HashMap<String, Integer> readnameToTaxonIdMap = new HashMap<>();


            if (this.reader == null) {
                throw new Exception("CSV-Reader has not been initializied yet");
            } else {
                BufferedReader bufferedReader = new BufferedReader(this.reader);

                String currentLine = "";
                while ((currentLine = bufferedReader.readLine()) != null) {
                    //split lines by tabs, semikolon or commas
                    currentLine = currentLine.replace(',', '\t');
                    currentLine = currentLine.replace(';', '\t');
                    String[] entriesPerLine = currentLine.split("\t");

                    readnameToTaxonIdMap.put(entriesPerLine[0], Integer.parseInt(entriesPerLine[1]));
                }
            }


        return readnameToTaxonIdMap;
    }
}
