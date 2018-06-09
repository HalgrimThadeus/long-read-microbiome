package Model.Tax;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaxTree {
    private Map<Integer, TaxNode> tree = new HashMap<>();
    //this is one idea how to store the taxdump entries...could be maybe spaceexpensive, but makes access easy
    private Map<String, Integer> nameMap = new HashMap<>();

    public void add(TaxNode taxNode) {
        tree.put(taxNode.getId(), taxNode);
    }

    public void setNameOfId(int id, String name) {

        //add name to TaxNode
        //and add entry to nameMap
    }

    public void setChildren() {

        //THIS METHOD MUST BE CALLED BY TAXIO or CALL IT in ADD MEthod
        // (quiet time expensive, maybe better to have a list of unlinked children)
        int i=0;
        for (Map.Entry<Integer, TaxNode> child : this.tree.entrySet()) {
            TaxNode parent = tree.get(child.getValue().getParentId());
            parent.addChild(child.getValue().getId());
            System.out.println(i++ + " Fertig");
        }
        int j = 0;
        //iterate through the ids and set node for node the children of each node
        //this can be done by searching the parentId (which is stored in each node) and set itself as child of parent
    }

    public List<Integer> getAllChildren(int id) {
        //returns children and subchildren..subsusbsubsubsubsubchildren of a node
        //???MAyBE other datastructure for return, because could be quiet large
        return null;
    }

    public int getId(String name) {

        //returns id by using nameMap
        return 0;
    }
}
