package model.tax;


import java.util.HashMap;
import java.util.Map;

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
        //if the added node is root set parent to null
        TaxNode parentNode;
        if (parentId == id) {
            parentNode = null;
        } else if (!this.tree.containsKey(parentId)) {
            parentNode = new TaxNode(parentId);
            this.tree.put(parentId, parentNode);
        } else {
            parentNode = this.tree.get(parentId);
        }

        //Create node itself and putting it into the tree
        //if Node does not exist (check with HashMap):
        if(!this.tree.containsKey(id)) {
            this.tree.put(id, new TaxNode(id, rank, parentNode));
        } else{
            this.tree.get(id).completeNode(rank, parentNode);
        }

        if(parentNode != null)
            parentNode.addChild(this.tree.get(id));

    }

    /**
     * method to be called if name is set a posteriori (e.g. file parsing from name.dmp)
     * the name and id for all entries are fixed after putting it into the datastructure
     * @param id
     * @param name
     */
    public void setNameOfId(int id, String name) {
        if(tree.containsKey(id)) {
            //only adds the names of organims, !!doesnot regard if there are multiple ranks named by one name.
            // the latest of the names.dmp file is used. So the species names are always in, because no rank has a double name like each species!
            nameMap.put(name,id);
            tree.get(id).setName(name);
        }
    }

    /**
     * returns the node with a specific name
     * @param name
     * @return
     */
    public TaxNode getNode(String name) {
        if (this.nameMap.containsKey(name)) {
            return this.tree.get(this.nameMap.get(name));
        } else {
            return null;
        }
    }

    public TaxNode getNodeById(int id){
        if(this.tree.containsKey(id)){
            return this.tree.get(id);
        }
        else{
            return null;
        }
    }


    public boolean isEmpty(){
        return tree.isEmpty();
    }

    public boolean hasNode(String name) {
        return nameMap.containsKey(name);
    }
}
