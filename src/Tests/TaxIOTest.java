import Model.Tax.TaxIO;
import Model.Tax.TaxNode;
import Model.Tax.TaxTree;
import org.junit.Test;

import java.io.CharArrayReader;
import java.io.IOException;
import java.io.Reader;
import java.util.stream.Stream;

import static org.junit.Assert.*;

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

        testTree.getNode("Pardus");
        //view in DEBUG
        int i = 1;
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

        //view in DEBUG
        int i = 1;

        //following works only with working taxnode
        for (TaxNode tn: testTree.getNode("Lutra").getAncestorAtRank("family").getAllChildren()) {
            System.out.println(tn);
        }
    }
}