package view;

import javafx.concurrent.Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Sample;
import model.services.NewSampleService;
import presenter.NewSamplePopUpPresenter;

import java.io.File;

public class NewSamplePopUp {


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

    private NewSamplePopUpPresenter newSamplePopUpPresenter;

    public NewSamplePopUp(NewSamplePopUpPresenter newSamplePopUpPresenter) {
        this.newSamplePopUpPresenter = newSamplePopUpPresenter;
    }

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

            if(fastaFile != null)
                fastaFileTextField.setText(fastaFile.getAbsolutePath());
        }
        if(event.getSource().equals(searchGffFile)){
            gffFile = this.getNewFiles("gff");

            if(gffFile != null)
                gffFileTextField.setText(fastaFile.getAbsolutePath());
        }
        if(event.getSource().equals(searchCsvFile)){
            csvFile = this.getNewFiles("txt");
            if(csvFile != null)
                csvFileTextField.setText(fastaFile.getAbsolutePath());
        }
    }

    @FXML
    public void createNewSampleClicked(ActionEvent event){
        Stage stage = (Stage) createNewSample.getScene().getWindow();

        //if not all files were declared return a warnning notice
        if(fastaFile == null || gffFile == null || csvFile == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "At least one file is missing!", ButtonType.OK);
            alert.show();
            return;
        }

        Service newSampleService = new NewSampleService(fastaFile, gffFile, csvFile);

        newSampleService.setOnSucceeded(event1 -> {
            Sample sample = ((NewSampleService)event1.getSource()).getValue();
            newSamplePopUpPresenter.addSampleToProject(fastaFile, gffFile, csvFile, sample);
        });

        newSampleService.setOnFailed(event1 -> {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Loading of Sample FAILED", ButtonType.CANCEL);
            alert.show();
        });

        newSampleService.start();

        stage.close();
    }

}
