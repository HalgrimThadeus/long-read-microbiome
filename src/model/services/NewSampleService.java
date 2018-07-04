package model.services;

import model.Sample;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import model.io.SampleReader;

import java.io.File;
import java.io.FileReader;

import static java.lang.Thread.sleep;

/**
 * NewSampleService
 *
 * @author Patrick Schirm
 */
public class NewSampleService extends Service<Sample> {
    private final File csvFile;
    private final File gffFile;
    private final File fastaFile;

    public NewSampleService(File fastaFile, File gffFile, File csvFile){
        this.fastaFile = fastaFile;
        this.gffFile = gffFile;
        this.csvFile = csvFile;
    }

    @Override
    protected Task<Sample> createTask() {

        return new Task<>() {
            @Override
            protected Sample call() throws Exception {
//                sleep(5000);
                FileReader fastaFileReader = new FileReader(fastaFile.getAbsolutePath());
                FileReader gffFileReader = new FileReader(gffFile.getAbsolutePath());
                FileReader csvFileReader = new FileReader(csvFile.getAbsolutePath());
                return SampleReader.read(fastaFileReader,gffFileReader,csvFileReader);
            }
        };
    }
}
