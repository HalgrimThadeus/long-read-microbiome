package Model.IO;

import Model.Tax.TaxIO;
import Model.Tax.TaxTree;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 * ReadInTaxTreeService
 *
 * @author Patrick Schirm
 */
public class ReadInTaxTreeService extends Service<TaxTree> {

    private final String filePathToNodes;
    private final String filePathToNames;

    public ReadInTaxTreeService(String filePathToNodes, String filePathToNames) {
        this.filePathToNodes = filePathToNodes;
        this.filePathToNames = filePathToNames;
    }

    @Override
    protected Task<TaxTree> createTask() {
        return new Task<>() {
            @Override
            protected TaxTree call() throws Exception {
                TaxIO treeReader = new TaxIO(filePathToNodes, filePathToNames);
                return treeReader.readInTaxTree();
            }
        };
    }
}
