package View;

import Model.IO.NewSampleService;
import Model.Project;
import Model.Sample;
import javafx.concurrent.Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class NewSampleDialog {

    @FXML
    private Button searchFastaFile;
    @FXML
    private Button searchGffFile;
    @FXML
    private Button searchCsvFile;

    @FXML
    private TextField fastaFileTextField;
    @FXML
    private TextField gffFileTextField;
    @FXML
    private TextField csvFileTextField;

    @FXML
    private Button createNewSample;


    private File fastaFile = null;
    private File gffFile = null;
    private File csvFile = null;

    private File getNewFiles(String extension){
        String usedExtension = "*." + extension;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("BioFiles",usedExtension)
        );

        File selectedFile = fileChooser.showOpenDialog(new Stage());

        return selectedFile;
    }



    @FXML
    public void addNewFilesClicked(ActionEvent event){

        if(event.getSource().equals(searchFastaFile)){
            fastaFile = this.getNewFiles("fasta");
            fastaFileTextField.setText(fastaFile.getAbsolutePath());
        }
        if(event.getSource().equals(searchGffFile)){
            gffFile = this.getNewFiles("gff");
            gffFileTextField.setText(fastaFile.getAbsolutePath());
        }
        if(event.getSource().equals(searchCsvFile)){
            csvFile = this.getNewFiles("txt");
            csvFileTextField.setText(fastaFile.getAbsolutePath());
        }


    }

    //move this one in presenter?
    @FXML
    public void createNewSampleClicked(ActionEvent event){
        Stage stage = (Stage) createNewSample.getScene().getWindow();

        //if not all files were declared return a warnning notice
        //todo: warning dialog
        if(fastaFile == null || gffFile == null || csvFile == null)
            stage.close();

        Service newSampleService = new NewSampleService(fastaFile.getAbsolutePath(), gffFile.getAbsolutePath(), csvFile.getAbsolutePath());

        newSampleService.setOnSucceeded(event1 -> {
            Sample sample = ((NewSampleService)event1.getSource()).getValue();
            Project.addSamples(sample);
            File[] files = new File[3];
            files[0] = fastaFile;
            files[1] = gffFile;
            files[2] = csvFile;
            Project.listOfSamplesFilePaths.add(files);
        });

        newSampleService.setOnFailed(event1 -> {
            //TODO: On error
        });

        newSampleService.start();

        stage.close();
    }
}
