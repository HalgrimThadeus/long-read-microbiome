import Model.Tax.TaxNode;
import Model.Tax.TaxTree;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class TaxNodeTest {

    private TaxNode rootNode = new TaxNode(1, "family", null);

    @Test
    public void shoulGetAllChildrenOfRoot(){
        //shouldAddNodesToRoot
        TaxNode parentNode = new TaxNode(2, "genus", rootNode);
        rootNode.addChild(parentNode);
        TaxNode parentNode2 = new TaxNode(3, "genus", rootNode);
        rootNode.addChild(parentNode2);
        TaxNode childrenNode = new TaxNode(4, "family", parentNode);
        rootNode.addChild(childrenNode);


        List<TaxNode> expectedChildrenList = new LinkedList<TaxNode>();
        expectedChildrenList.add(parentNode);
        expectedChildrenList.add(parentNode2);
        expectedChildrenList.add(childrenNode);

        assertEquals(expectedChildrenList , rootNode.getAllChildren());
    }


}