package progproj.homework;

import java.util.List;
import java.util.Map;

public class TaxTree {
    private Map<Integer, TaxNode> tree;
    private Map<String, Integer> nameMap;

    public void add(TaxNode taxNode) {

    }

    public void setNameOfId(int id, String name) {

    }

    public void setChildren() {

        //THIS METHOD MUST BE CALLED BY TAXIO or CALL IT in ADD MEthod
        // (quiet time expensive, maybe better to have a list of unlinked children)


        //iterate through the ids and set node for node the children of each node
        //this can be done by searching the parentId (which is stored in each node) and set itself as child of parent
    }

    public List<Integer> getAllChildren(int id) {
        //returns children and subchildren..subsusbsubsubsubsubchildren of a node
        //???MAyBE other datastructure for return, because could be quiet large
        return null;
    }


    //PROBLEM: SERCHING FOR  NAME is super compliccated
    //USE two hashtables??? One for name to id, one for id to node
}