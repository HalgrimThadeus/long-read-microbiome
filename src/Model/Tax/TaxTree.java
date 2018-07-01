package Model.Tax;

import javax.swing.tree.TreeNode;
import java.util.*;

/**
 * @class the datastorage for the taxdump files, where information about species and their taxonomic classification is put in
 * therefore there are two hashmaps, which make easy access of the tree strucure and the elements theirselves possible
 */
public class TaxTree {

    /**
     * contains the actual tree structure with the TaxNodes
     */
    private Map<Integer, TaxNode> tree;

    /**
     * in this map the search for ids by name is made possible, for easy use in later applications as genus filtering
     */
    private Map<String, Integer> nameMap;

    /**
     * constructing empty maps
     */
    public TaxTree() {
        this.tree = new HashMap<>();
        this.nameMap =  new HashMap<>();
    }

    /**
     * adds the taxNode to the tree, by creting a node out of the different components
     * @param id, rank, parentId
     */
    public void addNode(int id, String rank, int parentId) {

        //create almost empty parent node if it doesn't already exists, and initilize it completely later
        TaxNode parentNode;
        if(!this.tree.containsKey(parentId)){
            parentNode = new TaxNode(parentId);
        }
        else{
            parentNode = this.tree.get(parentId);
        }

        //Create node itself and putting it into the tree
        //if Node does not exist (check with HashMap):
        TaxNode newNode;
        if(!this.tree.containsKey(id)) {
            this.tree.put(id, new TaxNode(id, rank, parentNode));
        } else{
            newNode = this.tree.get(id);
            newNode.completeNode(rank, parentNode);
        }


    }

    /**
     * method to be called if name is set a posteriori (e.g. file parsing from name.dmp)
     * @param id
     * @param name
     */
    public void setNameOfId(int id, String name) {
        nameMap.put(name,id);
        tree.get(id).setName(name);
    }

    /**
     * returns the name of one id
     * @param name
     * @return
     */
    public int getId(String name) {
        if (this.nameMap.containsKey(name)) {
            return this.nameMap.get(name);
        } else {
            return -1;
        }
    }

    /**
     *
     * @return the hash tree
     */
    public Map<Integer, TaxNode> getTree() {
        return tree;
    }
}
