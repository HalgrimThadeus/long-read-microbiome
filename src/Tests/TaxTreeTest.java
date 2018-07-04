
import model.tax.TaxNode;
import model.tax.TaxTree;
import org.junit.Test;

import static org.junit.Assert.*;

public class TaxTreeTest {

    @Test
    public void shouldCreateTestTree() {
        TaxTree taxTree = new TaxTree();
        taxTree.addNode(1,"SuperFastKingdom",1);
        taxTree.addNode(2,"Family",1);
        taxTree.addNode(3,"Species",2);
        taxTree.addNode(4,"Species",2);

        //DEBUG for seeing structure
        int i = 1;
    }

    @Test
    public void shouldCreateTestTreeUnordered() {
        TaxTree taxTree = new TaxTree();

        taxTree.addNode(3,"Species",2);
        taxTree.addNode(4,"Species",2);
        taxTree.addNode(1,"SuperFastKingdom",1);
        taxTree.addNode(2,"Family",1);

        //DEBUG for seeing structure
        int i = 1;
    }

    @Test
    public void shouldCreateTestTreeWithNames() {
        TaxTree taxTree = new TaxTree();
        taxTree.addNode(1,"SuperFastKingdom",1);
        taxTree.addNode(2,"Family",1);
        taxTree.addNode(3,"Species",2);
        taxTree.addNode(4,"Species",2);

        taxTree.setNameOfId(1,"Raubkatze");
        taxTree.setNameOfId(2, "Tiger");
        taxTree.setNameOfId(3, "Tiger");
        taxTree.setNameOfId(3,"Sumatratiger");
        taxTree.setNameOfId(4,"Sibirischer Tiger");


        taxTree.setNameOfId(3,"Dönertier");
        taxTree.setNameOfId(5,"Schwachsinn");
        taxTree.setNameOfId(1,"Tiger");

        //DEBUG for seeing structure
        int i = 1+1;
    }

    @Test
    public void shouldGetNodesByName() {
        TaxTree taxTree = new TaxTree();
        taxTree.addNode(1,"SuperFastKingdom",1);
        taxTree.addNode(2,"Family",1);
        taxTree.addNode(3,"Species",2);
        taxTree.addNode(4,"Species",2);

        taxTree.setNameOfId(1,"Raubkatze");
        taxTree.setNameOfId(2, "Tiger");
        taxTree.setNameOfId(3,"Sumatratiger");
        taxTree.setNameOfId(4,"Sibirischer Tiger");

        TaxNode testNodeRoot = new TaxNode(1,"SuperFastKingdom", null);
        testNodeRoot.setName("Raubkatze");
        TaxNode testNode1 = new TaxNode(2,"Family", testNodeRoot);
        testNode1.setName("Tiger");


        assertEquals(testNode1.toString(),taxTree.getNode("Tiger").toString());
        assertNull(taxTree.getNode("Käse"));
        assertEquals(testNodeRoot.toString(),taxTree.getNode("Raubkatze").toString());
    }
}