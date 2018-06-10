package Model.Tax;

import java.util.HashSet;
import java.util.Set;

/**
 * Datastructure of a TaxNode
 */
public class TaxNode {
    /**
     * Saves the Id from the Node
     */
    private int id;
    /**
     * Saves the Id from the parent node
     */
    private int parentId;
    /**
     * Contains the Children of this node
     */
    private Set<Integer> childrenIds = new HashSet<>();
    /**
     * Contains the taxonomic rank of the node
     */
    private String rank;
    /**
     * Contains the scietific name of the node
     */
    private String name;

    /**
     * Constructor of the class
     *
     * @param id takes the id of the node as int
     * @param parentId takes the id of the parent of the node as int
     * @param rank takes the taxonomic rank of the node as String
     */
    public TaxNode(int id, int parentId, String rank) {
        //normally this should be out commented, because its not my job ;)
        this.id = id;
        this.parentId = parentId;
        this.rank = rank;
        this.name = null;
    }

    /**
     * Set the name of the node
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Add a Children of the node to the List of childrenIds
     * @param childId
     */
    public void addChild(int childId) {
        this.childrenIds.add(childId);
    }

    /**
     * Retruns the List of the childrenIds
     * @return
     */
    public Set<Integer> getChildren() {
        return this.childrenIds;
    }

    /**
     * Returns the id
     * @return
     */
    public int getId(){
        return this.id;
    }

    /**
     * Returns the parent Id
     * @return
     */
    public int getParentId(){
        return parentId;
    }

    /**
     * returns the name of the taxNode
     * @return
     */
    public String getName() {
        return this.name;
    }

}
