package View;

import Model.FastAEntry;
import Model.IO.FastAIO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Controller2 implements Initializable {

    @FXML
    public TitledPane fastaFiles;

    @FXML
    public TextArea textAreaTab;

    @FXML
    public CheckBox testCheckBox;

    @FXML
    public Button browseFileBtn;

    @FXML
    public ListView fastaList;

    @FXML
    public ListView gffList;

    @FXML
    public ListView taxaList;



    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    public void browseFileBtnClicked(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("BioFiles","*.fasta", "*.gff", "*.csv")
        );

        File selectedFile = fileChooser.showOpenDialog(new Stage());

        if(selectedFile != null){
            if(selectedFile.getName().endsWith(".fasta")){
                fastaList.getItems().add(selectedFile);
            }
            if(selectedFile.getName().endsWith(".gff")){
                gffList.getItems().add(selectedFile);
            }
            if(selectedFile.getName().endsWith(".csv")){
                taxaList.getItems().add(selectedFile);
            }

        }

    }

    public void addedNewFastaItem(MouseEvent event) throws IOException {
        File fastaFile = (File) fastaList.getSelectionModel().getSelectedItem();
        FileReader reader = new FileReader(fastaFile);
        FastAIO fastaReader = new FastAIO();
        List<FastAEntry> fastaEntries = fastaReader.readFastA(reader);

        String file = "";
        for(int i = 0; i < fastaEntries.size(); i++){
            file += fastaEntries.get(i).getHeader() + "\n"+ fastaEntries.get(i).getSequence() + "\n";
        }

        textAreaTab.setText(file);

    }

    public void checkedBox(ActionEvent event){
        if(testCheckBox.isSelected()) {
            textAreaTab.setStyle("-fx-control-inner-background:#000000; -fx-font-family: Consolas; -fx-highlight-fill: #ff00ff; -fx-highlight-text-fill: #ff00ff; -fx-text-fill: #ff00ff; ");
        }
        else{
            textAreaTab.setStyle("-fx-control-inner-background:#ffffff; -fx-font-family: Consolas; -fx-highlight-fill: #000000; -fx-highlight-text-fill: #000000; -fx-text-fill: #000000; ");
        }
    }



}
