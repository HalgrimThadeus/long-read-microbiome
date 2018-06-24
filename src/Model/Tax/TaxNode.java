package Model.Tax;

import java.util.LinkedList;
import java.util.List;

/**
 * Datastructure of a TaxNode
 */
public class TaxNode {
    /**
     * Saves the Id from the Node
     */
    private int id;
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
    private List<TaxNode> listOfChildrenNodes = new LinkedList<TaxNode>();

    /**
     * Constructor of the class
     *
     * @param id takes the id of the node as int
     * @param parentNode takes parental node
     * @param rank takes the taxonomic rank of the node as String
     */
    public TaxNode(int id, String rank, TaxNode parentNode) {
        this.id = id;
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
    public void completeNode(String rank, TaxNode parentNode){
        this.rank = rank;
        this.parentNode = parentNode;
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
     * @param child as taxnode
     */
    public void addChild(TaxNode child) {
        this.listOfChildrenNodes.add(child);
    }

    /**
     * Returns the id
     * @return
     */
    public int getID(){
        return this.id;
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
                ", ParentID: " + this.parentNode +
                ", ChildrenIDs: " + this.listOfChildrenNodes.toString();
    }

    /**
     * returns true if node is root, because parent id is set to id of itself
     * @return
     */
    public boolean isRoot() {
        return this == this.parentNode;
    }

    /**
     * recursevily iterates through the children giving back ALL children (childrenschildren...)
     * @return list of children (taxNOde)
     */
    public List<TaxNode> getAllChildren() {
        if(this.listOfChildrenNodes == null) {
            return null;
        } else {
            List<TaxNode> childrenchilds = new LinkedList<>();
            for (TaxNode tn : this.listOfChildrenNodes) {
                childrenchilds.addAll(tn.getAllChildren());
            }
            return childrenchilds;
        }
    }


    /**
     * returns the ancestor which has the given rank
     * @param rank
     * @return
     */
    public TaxNode getAncestorAtRank(String rank) {
        if(this.rank.equals(rank)) {
            return this;
        } else {
            return this.parentNode.getAncestorAtRank(rank);
        }
    }
}
