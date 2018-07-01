package View;

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
            File fastaFile = this.getNewFiles("fasta");
            fastaFileTextField.setText(fastaFile.getAbsolutePath());
        }
        if(event.getSource().equals(searchGffFile)){
            File fastaFile = this.getNewFiles("gff");
            gffFileTextField.setText(fastaFile.getAbsolutePath());
        }
        if(event.getSource().equals(searchCsvFile)){
            File fastaFile = this.getNewFiles("dmp");
            csvFileTextField.setText(fastaFile.getAbsolutePath());
        }
    }

    @FXML
    public void createNewSampleClicked(ActionEvent event){
        Stage stage = (Stage) createNewSample.getScene().getWindow();
        stage.close();
    }
}
