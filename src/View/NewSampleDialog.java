package View;

import Model.IO.SampleReader;
import Model.Project;
import Model.Sample;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.print.DocFlavor;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;

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

        try {
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

        } catch(Exception e) {
            e.printStackTrace();
            //todo: show up error dialog in window
        }

        stage.close();
    }
}
