package model.services;

import model.io.TaxIO;
import model.tax.TaxTree;
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
        return new Task<TaxTree>() {
            @Override
            protected TaxTree call() throws Exception {
                TaxIO treeReader = new TaxIO(filePathToNodes, filePathToNames);
                return treeReader.readInTaxTree();
            }
        };
    }
}
