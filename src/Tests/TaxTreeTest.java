
import Model.Tax.TaxNode;
import Model.Tax.TaxTree;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;

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

    public void shouldAddSubNodes() {
        TaxNode subNode = new TaxNode(4, 3, "Species");
        subNode.setName("Schüsselsprenger");
        taxTree.add(subNode);
        TaxNode subNode2 = new TaxNode(5, 3, "Species");
        subNode2.setName("Flitzpiepe");
        taxTree.add(subNode2);
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

        shouldAddSubNodes();
        shouldSetChildrenOfTree();
        System.out.println(taxTree.getAllChildren(1));
    }

    @Test
    public void shouldGetAllChildrenOfLeaf() {
        shouldAddRootToTree();
        shouldAddTwoNodesWithSettingName();
        shouldSetChildrenOfTree();

        assertEquals(new HashSet<Integer>(), taxTree.getAllChildren(3));
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