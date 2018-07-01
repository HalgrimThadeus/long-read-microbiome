package Model.Tax;

import javax.swing.tree.TreeNode;
import java.io.*;
import java.util.Map;

/**
 * TaxIo class gets the FilePaths of a names.dmp and a nodes.dmp File and produces a tree
 * representing the phylogney of the taxas.
 */

public class TaxIO {

    /**
     * this static mathod readInTaxTree gets the filepaths and produces the tree
     * @return the completed tree from the files
     * @throws IOException
     */
    public TaxTree readInTaxTree(String filePathToNodes,String filePathToNames ) throws IOException {
        //Initialise a emptry tree to start
        TaxTree tree = new TaxTree();
        //read in first the nodes.dmp and create TaxNodes in TaxTree
        BufferedReader nodeReader = new BufferedReader(new FileReader(filePathToNodes));
        //get the first line
        String nodeLine = nodeReader.readLine();
        //as long as there are lines, we make new nodes and add them to the tree
        while(nodeLine != null){
            nodeLine = nodeLine.replace('\t', '\0');
            String[] lineValues = nodeLine.trim().split("\\|");

            //ID, parentID and rank for a node
            int id = Integer.parseInt(lineValues[0].trim());
            int parentId = Integer.parseInt(lineValues[1].trim());
            String rank = lineValues[2].trim();

            //Also add the new Node newNode to the HashMap-Tree
            tree.addNode(id, rank, parentId);

            nodeLine = nodeReader.readLine();
        }

        //then read in names.dmp and use only the scientific names,which are then added to the corresponding TaxNode via TaxTree

        BufferedReader nameReader = new BufferedReader(new FileReader(filePathToNames));
        //read in first the nodes.dmp and create TaxNodes in TaxTree

        //useful to only take the first line with a ID
        String nameLine = nameReader.readLine();

        //Add names; as long as there are lines take them and add the names to the corresponding node
        while (nameLine != null ) {

                nameLine = nameLine.replace('\t', '\0');
                String[] lineValues = nameLine.trim().split("\\|");

                //only take the scientific names; overwrites if there are multiple scientifc names for one id;
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
