import Model.Tax.TaxNode;
import Model.Tax.TaxTree;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class TaxNodeTest {

    @Test
    public void shouldGetAllChildrenOfRoot(){
        TaxNode rootNode = new TaxNode(1, "family", null);
        TaxNode parentNode = new TaxNode(2, "genus", rootNode);
        rootNode.addChild(parentNode);
        TaxNode parentNode2 = new TaxNode(3, "genus", rootNode);
        rootNode.addChild(parentNode2);
        TaxNode childrenNode = new TaxNode(4, "species", parentNode);
        parentNode.addChild(childrenNode);


        //order is important
        List<TaxNode> expectedChildrenList = new LinkedList<TaxNode>();
        expectedChildrenList.add(parentNode);
        expectedChildrenList.add(childrenNode);
        expectedChildrenList.add(parentNode2);

        assertEquals(expectedChildrenList,rootNode.getAllChildren());
        assertEquals(0,childrenNode.getAllChildren().size());
        assertNotEquals(expectedChildrenList, parentNode.getAllChildren());
    }


    @Test
    public void shouldGetAncestorAtRankFamily() {
        //builds up the tree
        TaxNode rootNode = new TaxNode(1, "family", null);
        TaxNode parentNode = new TaxNode(2, "genus", rootNode);
        rootNode.addChild(parentNode);
        TaxNode parentNode2 = new TaxNode(3, "genus", rootNode);
        rootNode.addChild(parentNode2);
        TaxNode childrenNode = new TaxNode(4, "species", parentNode);
        parentNode.addChild(childrenNode);

        assertEquals(rootNode,childrenNode.getAncestorAtRank("family"));
        assertEquals(parentNode,childrenNode.getAncestorAtRank("genus"));
        assertEquals(childrenNode,childrenNode.getAncestorAtRank("species"));
        assertNull(childrenNode.getAncestorAtRank("SuperKINGDOM"));
    }


}