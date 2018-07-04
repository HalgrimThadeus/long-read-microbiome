package presenter;

import model.Project;
import model.Read;
import model.Sample;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class NewSamplePopUpPresenter {


    public void addSampleToProject(File fastaFile, File gffFile, File csvFile, Sample newSample) {
        Project.addSamples(newSample);
        File[] files = new File[3];
        files[0] = fastaFile;
        files[1] = gffFile;
        files[2] = csvFile;
        Project.listOfSamplesFilePaths.add(files);

        List<String> readHeaders = new ArrayList<>();
        for (Read read:newSample.getReads()) {
            readHeaders.add(read.getHeader());
        }

    }
}
