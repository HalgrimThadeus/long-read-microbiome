
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

    @Test
    public void shouldGetIdOfRoot() {
        shouldAddRootToTree();
        shouldAddTwoNodesWithSettingName();

        assertEquals(1, taxTree.getId("Flatulenzae"));
        assertNotEquals(2, taxTree.getId("Flatulenzae"));
        assertNotEquals(1, taxTree.getId("sdfsdfsdfdsfs"));
        assertEquals(-1, taxTree.getId("sbgnfgnargh"));
    }
}