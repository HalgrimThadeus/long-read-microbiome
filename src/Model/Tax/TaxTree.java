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

    public List<Integer> getAllChildren(int id) {
        List<Integer> childrenList = tree.get(id).getChildren();
        List<Integer> allChildrenList = childrenList;


            for (int i = 0; i < childrenList.size(); i++) {
                allChildrenList.addAll(getAllChildren(childrenList.get(i)));
            }
            return allChildrenList;



    }

    public int getName(int id) {

        //returns name using nameMap
        return 0;
    }
}
