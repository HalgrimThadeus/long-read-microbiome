package Model.Tax;

import java.util.ArrayList;
import java.util.List;

public class TaxNode {
    private int id;
    private int parentId;
    private List<Integer> childrenIds = new ArrayList<>() ;
    private String rank;
    private String name;

    public TaxNode(int id, int parentId, String rank) {
        //normally this should be out commented, because its not my job ;)
        this.id = id;
        this.parentId = parentId;
        this.rank = rank;
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addChild(int childId) {
        this.childrenIds.add(childId);
    }

    public List<Integer> getChildren() {
        return this.childrenIds;
    }

    public int getId(){
        return this.id;
    }

    public int getParentId(){
        return parentId;
    }


}
