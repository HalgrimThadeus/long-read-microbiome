package Model.Tax;

import javax.swing.tree.TreeNode;
import java.io.*;
import java.util.Map;

public class TaxIO {
    public static void main(String[] args) throws IOException {
        readInTaxTree();
    }
    public static TaxTree readInTaxTree() throws IOException {

        //please CHANGE
        String filePathToNodes = "C:\\Users\\manug\\Desktop\\nodes.dmp";
        String filePathToNames = "./names.dmp";

        TaxTree tree = new TaxTree();

        BufferedReader nodeReader = new BufferedReader(new FileReader(filePathToNodes));
        //read in first the nodes.dmp and create TaxNodes in TaxTree
        String line = nodeReader.readLine();
        while(line != null){
            line.replace('\t', '\0');
            String[] lineValues = line.trim().split("\\|");

            int id = Integer.parseInt(lineValues[0].trim());
            int parentId = Integer.parseInt(lineValues[1].trim());
            String rank = lineValues[2].trim();

            TaxNode node = new TaxNode(id, parentId, rank);
            tree.add(node);

            line = nodeReader.readLine();
        }

        tree.setChildren();

        //then read in names.dmp and use only the scientific names,which are then added to the corresponding TaxNode via TaxTree


        return null;
    }
}
