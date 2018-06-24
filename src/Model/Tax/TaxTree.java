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
     * cause the nodes.dmp only safe parent nodes, therefore the children have to be set afterwards !!TIME EXPENSIVE!!
     *
     *THIS METHOD MUST BE CALLED after Addition Process
     */
    /**public void setChildren() { //TODO should be removed

        for (TaxNode child : this.tree.values()) {

            //tests if node is root, to not set child of root root itself
            if(!(child.getID() == child.getParentId())) {
                TaxNode parent = tree.get(child.getParentId());
                parent.addChild(child.getID());
            }
        }
    }*/


    /**
     * returns all children (inner nodes and leafes) of one node
     * may cause problems if applied on root, because of a huge set
     * @param id
     * @return
     */
    public Set<Integer> getAllChildrenIDs(int id) {
        Set<Integer> childrenList = tree.get(id).getChildrenIDs();
        Set<Integer> allChildrenList = childrenList;

        Iterator<Integer> childrenIterator = childrenList.iterator();
        while(childrenIterator.hasNext()) {
            allChildrenList.addAll(getAllChildrenIDs(childrenIterator.next()));
        }
        return allChildrenList;
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
     * returns the ancestor at a specific rank of the taxtree by calling it with an organisms name
     * @param rank
     * @param organismName
     * @return
     */
    public TaxNode getAncestor(String rank,String organismName) {
        if(rank == null || organismName == null) {
            return null;
        } else {
            TaxNode currentNode = this.tree.get(this.getId(organismName));

            if(currentNode == null)
                return null;

            while (true) {
                if(rank.equals(currentNode.getRank())) {
                    return currentNode;
                }

                if(currentNode.isRoot()){
                    return null;
                }

                currentNode = this.tree.get(currentNode.getParentId());
            }
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
