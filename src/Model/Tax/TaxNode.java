package Model.Tax;

import java.util.HashSet;
import java.util.LinkedList;
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
     * Reference to the parentNode
     */
    private TaxNode parentNode;
    /**
     * List of the all the childrenNodes
     */
    private LinkedList<TaxNode> listOfChildrenNodes = new LinkedList<TaxNode>();

    /**
     * Constructor of the class
     *
     * @param id takes the id of the node as int
     * @param parentId takes the id of the parent of the node as int
     * @param rank takes the taxonomic rank of the node as String
     */
    public TaxNode(int id, int parentId, String rank, TaxNode parentNode) {
        //normally this should be out commented, because its not my job ;)
        this.id = id;
        this.parentId = parentId;
        this.rank = rank;
        this.name = null;
        this.parentNode = parentNode;
    }

    /**
     * 2nd constructor to initilize empty parent node when initilizing it from a child
     */
    public TaxNode(int id){
        this.id = id;
    }
    /**
     * method to complete a node, that was just initilized when the child was found
     */
    public void updateNode(int parentId, String rank, TaxNode parentNode){
        this.rank = rank;
        this.parentId = parentId;
    }

    /**
     * Set the name of the node
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     *
     */

    /**
     * Add a Children of the node to the List of childrenIds
     * @param childId
     */
    public void addChild(int childId, TaxNode child) {
        this.childrenIds.add(childId);
        this.listOfChildrenNodes.add(child);
    }

    /**
     * Retruns the List of the childrenIds
     * @return
     */
    public Set<Integer> getChildrenIDs() {
        return this.childrenIds;
    }

    /**
     * Returns the id
     * @return
     */
    public int getID(){
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

    /**
     * returns the rank of the taxNode
     * @return
     */
    public String getRank() { return this.rank; }

    /**
     * converts the object to string
     * @return
     */
    @Override
    public String toString() {
        return "Name: " + this.name +
                ", ID: " + this.id +
                ", Rank: " + this.rank +
                ", ParentID: " + this.parentId +
                ", ChildrenIDs: " + this.getChildrenIDs();
    }

    /**
     * returns true if node is root, because parent id is set to id of itself
     * @return
     */
    public boolean isRoot() {
        return this.getID() == this.getParentId();
    }

}
