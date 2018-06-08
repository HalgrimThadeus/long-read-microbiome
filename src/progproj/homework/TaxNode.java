package progproj.homework;

import java.util.ArrayList;
import java.util.List;

public class TaxNode {
    private long id;
    private long parentId;
    private List<Long> childrenIds = new ArrayList<>() ;
    private String rank;
    private String name;

    public TaxNode(long id, long parentId, String rank) {
        //normally this should be out commented, because its not my job ;)
        this.id = id;
        this.parentId = parentId;
        this.rank = rank;
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addChild(long childId) {
        //this.childrenIds.add(childId);
    }

    public List<Long> getChildren() {
        //return this.childrenIds;
        return null;
    }


}
