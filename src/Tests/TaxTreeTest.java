
import Model.Tax.TaxNode;
import Model.Tax.TaxTree;
import org.junit.Test;

import static org.junit.Assert.*;

public class TaxTreeTest {

    private TaxTree taxTree = new TaxTree();

    public void shouldAddRootToTree() {
        TaxNode root = new TaxNode(1, 1, "SuperFastKingdom");
        root.setName("Flatulenzae");
        taxTree.add(root);
    }

    public void shouldAddTwoNodesWithSettingName() {
        TaxNode testNode = new TaxNode(2, 1, "Family");
        taxTree.add(testNode);
        taxTree.setNameOfId(2, "Liquipupso");

        TaxNode testNode2 = new TaxNode(3, 1, "Family");
        taxTree.add(testNode2);
        taxTree.setNameOfId(3, "Secoaire");
    }

    public void shouldSetChildrenOfTree() {
        taxTree.setChildren();
    }

    @Test
    public void shouldGetAllChildrenOfRoot() {
        shouldAddRootToTree();
        shouldAddTwoNodesWithSettingName();
        shouldSetChildrenOfTree();

        assertEquals(true, taxTree.getAllChildren(1).contains(3));
        assertNotEquals(true, taxTree.getAllChildren(1).contains(1));
    }

    @Test
    public void shouldGetAllChildrenOfLeaf() {
        shouldAddRootToTree();
        shouldAddTwoNodesWithSettingName();
        shouldSetChildrenOfTree();

        assertNull(taxTree.getAllChildren(3));
    }

    @Test
    public void shouldGetNameOfRoot() {
        shouldAddRootToTree();
        shouldAddTwoNodesWithSettingName();

        assertEquals("Flatulenzae", taxTree.getName(1));
        assertNotEquals("Flatulenzae", taxTree.getName(2));
        assertNull(taxTree.getName(42));
    }
}