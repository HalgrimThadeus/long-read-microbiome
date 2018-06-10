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
        String filePathToNodes = "res\\TreeDumpFiles\\nodesTest.dmp";
        String filePathToNames = "res\\TreeDumpFiles\\namesTest.dmp";

        TaxTree tree = new TaxTree();

        BufferedReader nodeReader = new BufferedReader(new FileReader(filePathToNodes));
        //read in first the nodes.dmp and create TaxNodes in TaxTree
        String nodeLine = nodeReader.readLine();
        while(nodeLine != null){
            nodeLine = nodeLine.replace('\t', '\0');
            String[] lineValues = nodeLine.trim().split("\\|");

            int id = Integer.parseInt(lineValues[0].trim());
            int parentId = Integer.parseInt(lineValues[1].trim());
            String rank = lineValues[2].trim();

            TaxNode node = new TaxNode(id, parentId, rank);
            tree.add(node);

            nodeLine = nodeReader.readLine();
        }

        tree.setChildren();

        //then read in names.dmp and use only the scientific names,which are then added to the corresponding TaxNode via TaxTree
        BufferedReader nameReader = new BufferedReader(new FileReader(filePathToNames));
        //read in first the nodes.dmp and create TaxNodes in TaxTree

        //useful to only take the first line with a ID
        String nameLine = nameReader.readLine();


        while (nameLine != null ) {

                nameLine = nameLine.replace('\t', '\0');
                String[] lineValues = nameLine.trim().split("\\|");

                //overwrites if there are multiple scientifc names for one id
                if(lineValues[3].contains("scientific name")) {
                    int nodeID = Integer.parseInt(lineValues[0].trim());

                    String nodeName = lineValues[1].trim();
                    tree.setNameOfId(nodeID, nodeName);

                }
                nameLine = nameReader.readLine();
            }


        return tree;
    }
}
