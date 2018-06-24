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
    //this is one idea how to store the taxdump entries...could be maybe spaceexpensive, but makes access easy
    private Map<String, Integer> nameMap;

    /**
     * constructing empty maps
     */
    public TaxTree() {
        this.tree = new HashMap<>();
        this.nameMap =  new HashMap<>();
    }

    /**
     * adds the taxNode to the tree, including the name if exists
     * @param taxNode
     */
    public void add(TaxNode taxNode) {
        tree.put(taxNode.getID(), taxNode);
        if(taxNode.getName() != null) {
            this.nameMap.put(taxNode.getName(),taxNode.getID());
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
     * returns all childrenNode (inner nodes and leafes) of one node,
     * using the Node-getAllchildren Method
     * may cause problems if applied on root, because of a huge set
     * @param id
     * @return
     */
    public List<TaxNode> getAllChildrens(int id) {
        TaxNode correspondingNode = tree.get(id);
        List<TaxNode> allChildrenNodeList = correspondingNode.getAllChildren();

        return allChildrenNodeList;
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
