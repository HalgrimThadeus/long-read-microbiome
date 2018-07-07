import model.services.ReadInTaxTreeService;
import model.io.TaxIO;
import model.tax.TaxNode;
import model.tax.TaxTree;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.embed.swing.JFXPanel;
import org.junit.Test;

import java.io.CharArrayReader;
import java.io.IOException;
import java.io.Reader;

import static org.junit.Assert.assertEquals;

public class TaxIOTest {

    //plus adding test data in resources
    @Test
    public void shouldReadInTaxTree() {
        String names = "1\t|\tPardus\t|\t\t|\tscientific name\t|\n" +
                "2\t|\tPanthera\t|\tEukaryota\t|\tscientific name\t|";
        String nodes = "1\t|\t2\t|\tspecies\t|\t\t|\t8\t|\t0\t|\t1\t|\t0\t|\t0\t|\t0\t|\t0\t|\t0\t|\t\t|\n" +
                "2\t|\t3\t|\tgenus\t|\t\t|\t0\t|\t0\t|\t11\t|\t0\t|\t0\t|\t0\t|\t0\t|\t0\t|\t\t|";

        Reader testNameReader = new CharArrayReader(names.toCharArray());
        Reader testNodeReader = new CharArrayReader(nodes.toCharArray());

        TaxIO taxIO = new TaxIO(testNodeReader,testNameReader);

        TaxTree testTree = null;

        try{
            testTree = taxIO.readInTaxTree();
        } catch(IOException ex) {
            ex.printStackTrace();
        }

        assertEquals("Pardus", testTree.getNode("Pardus").getName());
        assertEquals(1, testTree.getNode("Pardus").getID());
        assertEquals("Pardus", testTree.getNode("Panthera").getAllChildren().get(0).getName());
        assertEquals(false, testTree.getNode("Pardus").isRoot());
        assertEquals(false, testTree.getNode("Panthera").isRoot());
    }

    @Test
    public void shouldReadInTaxTreeFromFiles() {
        TaxTree testTree = null;

        try {
            TaxIO treeReader = new TaxIO("res/TreeDumpFiles/nodesTest.dmp", "res/TreeDumpFiles/namesTest.dmp");
            testTree = treeReader.readInTaxTree();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals("Pardus", testTree.getNode("Pardus").getName());
        assertEquals(1, testTree.getNode("Pardus").getID());
        assertEquals("Pardus", testTree.getNode("Panthera").getAllChildren().get(0).getName());
        assertEquals(false, testTree.getNode("Pardus").isRoot());
        assertEquals(false, testTree.getNode("Panthera").isRoot());
        assertEquals(8, testTree.getNode("Mustelidae").getID());
        assertEquals(6, testTree.getNode("Lutra_species").getID());
        assertEquals(7, testTree.getNode("Lutra_genus").getID());
        assertEquals(7, testTree.getNode("Lutra_species").getAncestorAtRank("genus").getID());
        assertEquals("Canidae", testTree.getNode("Lupus").getAncestorAtRank("family").getName());
    }

    @Test
    public void shouldReadInTaxTreeFromFilesWithService() {
        new JFXPanel();
        Platform.runLater(() -> {
            Service readInTaxTreeService = new ReadInTaxTreeService("res/TreeDumpFiles/nodesTest.dmp", "res/TreeDumpFiles/namesTest.dmp");
            readInTaxTreeService.setOnSucceeded(event -> {
                TaxTree testTree = ((ReadInTaxTreeService)event.getSource()).getValue();

                assertEquals("Pardus", testTree.getNode("Pardus").getName());
                assertEquals(1, testTree.getNode("Pardus").getID());
                assertEquals("Pardus", testTree.getNode("Panthera").getAllChildren().get(0).getName());
                assertEquals(false, testTree.getNode("Pardus").isRoot());
                assertEquals(false, testTree.getNode("Panthera").isRoot());
                assertEquals(8, testTree.getNode("Mustelidae").getID());
                assertEquals(6, testTree.getNode("Lutra_species").getID());
                assertEquals(7, testTree.getNode("Lutra_genus").getID());
                assertEquals(7, testTree.getNode("Lutra_species").getAncestorAtRank("genus").getID());
                assertEquals("Canidae", testTree.getNode("Lupus").getAncestorAtRank("family").getName());
            });
            readInTaxTreeService.start();
        });
    }
}