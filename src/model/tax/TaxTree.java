package model.tax;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.HashMap;
import java.util.Map;

/**
 * @class the datastorage for the taxdump files, where information about species and their taxonomic classification is put in
 * therefore there are two hashmaps, which make easy access of the tree strucure and the elements theirselves possible
 */
public class TaxTree {

    private BooleanProperty isLoaded = new SimpleBooleanProperty(false);

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


    public void setIsLoaded(boolean loaded){
        isLoaded.setValue(loaded);
    }

    public BooleanProperty getIsLoaded(){
        return isLoaded;
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
            //if(!nameMap.containsKey(name)) {
            //}

            //prevents collision of two names naming the same node
            if(!nameMap.containsValue(id)) {
                //adds "_" to name of an organism if name already exists and puts the rank too
                if(!nameMap.containsKey(name)) {
                    nameMap.put(name,id);
                    tree.get(id).setName(name);
                } else {
                    TaxNode existingNode = tree.get(nameMap.get(name));
                    nameMap.remove(existingNode.getName());
                    nameMap.put(existingNode.getName() + "_" + existingNode.getRank(), existingNode.getID());
                    existingNode.setName(existingNode.getName() + "_" + existingNode.getRank());

                    name = name + "_" + tree.get(id).getRank();
                    nameMap.put(name,id);
                    tree.get(id).setName(name);
                }
            }
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

    public boolean isEmpty(){
        return tree.isEmpty();
    }
}
