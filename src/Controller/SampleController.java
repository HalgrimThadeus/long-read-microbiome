package Controller;

import Model.FastAEntry;
import Model.IO.FastAIO;
import Model.IO.SampleReader;
import Model.Project;
import Model.Read;
import Model.Sample;
import View.MainView;
import View.ProjectChangedListener;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SampleController {

    private ProjectChangedListener context;

    public SampleController(ProjectChangedListener context) {
        this.context = context;
    }

    public void loadSampleFromFile(File fastaFile, File gffFile,File csvFile) throws Exception {
        FileReader fastaFileReader = new FileReader(fastaFile.getAbsolutePath());
        FileReader gffFileReader = new FileReader(gffFile.getAbsolutePath());
        FileReader csvFileReader = new FileReader(csvFile.getAbsolutePath());
        Sample newSample = SampleReader.read(fastaFileReader,gffFileReader,csvFileReader);
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


        context.sampleAdded("Testsample", fastaFile.getName(), gffFile.getName(), readHeaders);
    }

}
