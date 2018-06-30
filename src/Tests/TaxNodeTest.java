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
        //builds up the tree
        TaxNode rootNode = new TaxNode(1, "family", null);
        TaxNode parentNode = new TaxNode(2, "genus", rootNode);
        rootNode.addChild(parentNode);
        TaxNode parentNode2 = new TaxNode(3, "genus", rootNode);
        rootNode.addChild(parentNode2);
        TaxNode childrenNode = new TaxNode(4, "species", parentNode);
        parentNode.addChild(childrenNode);


        List<TaxNode> expectedChildrenList = new LinkedList<TaxNode>();

        expectedChildrenList = rootNode.getAllChildren();

        for(TaxNode tn: expectedChildrenList) {
            System.out.println(tn.toString());
        }
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

        System.out.println(childrenNode.getAncestorAtRank("family"));
        System.out.println(childrenNode.getAncestorAtRank("genus"));
    }


}