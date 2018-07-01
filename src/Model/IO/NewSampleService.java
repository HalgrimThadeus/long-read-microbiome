package Model.IO;

import Model.Sample;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.io.FileReader;

/**
 * NewSampleService
 *
 * @author Patrick Schirm
 */
public class NewSampleService extends Service<Sample> {
    private final String csvPath;
    private final String gffPath;
    private final String fastaPath;

    public NewSampleService(String fastAPath, String gffPath, String csvPath){
        this.fastaPath = fastAPath;
        this.gffPath = gffPath;
        this.csvPath = csvPath;
    }

    @Override
    protected Task<Sample> createTask() {

        return new Task<>() {
            @Override
            protected Sample call() throws Exception {
                FileReader fastaFileReader = new FileReader(fastaPath);
//                sleep(15000);
                FileReader gffFileReader = new FileReader(gffPath);
                FileReader csvFileReader = new FileReader(csvPath);
                return SampleReader.read(fastaFileReader,gffFileReader,csvFileReader);
            }
        };
    }
}
