package Model.Tax;

import java.util.*;

public class TaxTree {

    private Map<Integer, TaxNode> tree;
    //this is one idea how to store the taxdump entries...could be maybe spaceexpensive, but makes access easy
    private Map<String, Integer> nameMap;

    public TaxTree() {
        this.tree = new HashMap<>();
        this.nameMap =  new HashMap<>();
    }

    public void add(TaxNode taxNode) {
        tree.put(taxNode.getId(), taxNode);
        if(taxNode.getName() != null) {
            this.nameMap.put(taxNode.getName(),taxNode.getId());
        }
    }

    public void setNameOfId(int id, String name) {
        nameMap.put(name,id);
        tree.get(id).setName(name);
    }

    public void setChildren() {

        //THIS METHOD MUST BE CALLED BY TAXIO or CALL IT in ADD MEthod
        // (quiet time expensive, maybe better to have a list of unlinked children)
        for (TaxNode child : this.tree.values()) {

            //tests if node is root, to not set child of root root itself
            if(!(child.getId() == child.getParentId())) {
                TaxNode parent = tree.get(child.getParentId());
                parent.addChild(child.getId());
            }
        }
    }

    public Set<Integer> getAllChildren(int id) {
        Set<Integer> childrenList = tree.get(id).getChildren();
        Set<Integer> allChildrenList = childrenList;

        Iterator<Integer> childrenIterator = childrenList.iterator();
        while(childrenIterator.hasNext()) {
            allChildrenList.addAll(getAllChildren(childrenIterator.next()));
        }
        return allChildrenList;
    }

    public int getName(int id) {
        return this.nameMap.get(id);
    }
}
