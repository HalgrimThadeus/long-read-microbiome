package View;

import Model.FastAEntry;
import Model.IO.FastAIO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import jdk.nashorn.api.tree.Tree;

import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    public Accordion sampleAccordion;

    @FXML
    public SplitPane mainSplitPain;

    @FXML
    public Button toolbarBtnAddSamplePage;

    @FXML
    public Button newSampleBtn;

    @FXML
    public ListView filterList;

    @FXML
    public HTMLEditor editor;

    @FXML
    public MenuItem addNewFilterContextMenu;


    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    public void newSampleBtnClicked(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("BioFiles","*.fasta", "*.gff", "*.csv")
        );

        File selectedFile = fileChooser.showOpenDialog(new Stage());

        FileReader reader = new FileReader(selectedFile);
        FastAIO fastaReader = new FastAIO();
        List<FastAEntry> fastaEntries = fastaReader.readFastA(reader);

        String file = "";
        for(int i = 0; i < fastaEntries.size(); i++){
            file += fastaEntries.get(i).getHeader() + "<br/> " +
                    fastaEntries.get(i).getSequence() + "<br/>";
            file += "\n";
        }

        TitledPane newAccordionPane = new TitledPane();
        AnchorPane newAnchorPane = new AnchorPane();
        TreeView newTreeView = new TreeView();

        TreeItem<String> root = new TreeItem<>("Sample 1");
        root.setExpanded(true);

        TreeItem<String> fastaChild = new TreeItem<>("Fasta-Files");
        fastaChild.setExpanded(false);
        TreeItem<String> gffChild = new TreeItem<>("Gff-Files");
        gffChild.setExpanded(false);
        TreeItem<String> taxaChild = new TreeItem<>("Taxonomic-Files");
        taxaChild.setExpanded(false);

        root.getChildren().add(fastaChild);
        root.getChildren().add(gffChild);
        root.getChildren().add(taxaChild);
        newTreeView.setRoot(root);
        newTreeView.setShowRoot(false);

        AnchorPane.setLeftAnchor(newTreeView, 0d);
        AnchorPane.setRightAnchor(newTreeView, 0d);
        AnchorPane.setBottomAnchor(newTreeView, 0d);
        AnchorPane.setTopAnchor(newTreeView, 0d);
        newAnchorPane.getChildren().add(newTreeView);

        newTreeView.setMaxHeight(100);
        newAccordionPane.setMaxHeight(100);
        newTreeView.setMinHeight(100);
        newAccordionPane.setMinHeight(100);
        newAccordionPane.setText("Sample " + (sampleAccordion.getPanes().size() + 1));
        newAccordionPane.setContent(newAnchorPane);

        sampleAccordion.getPanes().add(newAccordionPane);


        editor.setHtmlText(file);
    }

    @FXML
    public void toolbarBtnAddSamplePage(ActionEvent event) throws IOException {
        TabPane newSampleTabPane = FXMLLoader.load(getClass().getResource("tabPane.fxml"));
        mainSplitPain.getItems().add(newSampleTabPane);
        System.out.println(mainSplitPain.getItems());
    }

    public void addNewFilterContextMenuClicked(ActionEvent event) throws IOException {
        System.out.println("Open the Filter");
    }
    /*
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
*/


}
