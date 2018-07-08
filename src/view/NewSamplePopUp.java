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
    private TextField sampleName;

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
        fileChooser.setInitialDirectory(new File
                (System.getProperty("user.home") ));
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
            else return;
        }
        if(event.getSource().equals(searchGffFile)){
            gffFile = this.getNewFiles("gff");

            if(gffFile != null)
                gffFileTextField.setText(fastaFile.getAbsolutePath());
            else return;
        }
        if(event.getSource().equals(searchCsvFile)){
            csvFile = this.getNewFiles("txt");
            if(csvFile != null)
                csvFileTextField.setText(fastaFile.getAbsolutePath());
            else return;
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

        if(sampleName.getText() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please set a sample name", ButtonType.OK);
            alert.show();
            return;
        }

        newSamplePopUpPresenter.addSampleToProject(fastaFile,gffFile,csvFile,sampleName.getText());

        stage.close();
    }

}
