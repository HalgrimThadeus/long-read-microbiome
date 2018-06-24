package View;

import Model.FastAEntry;
import Model.IO.FastAIO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

    /**
     * Important Containers
     */
    @FXML
    public Accordion sampleAccordion;

    @FXML
    public SplitPane mainSplitPain;

    @FXML
    public ListView filterList;

    @FXML
    public HTMLEditor editor;


    /**
     * Important Buttons
     */
    @FXML
    public Button toolbarBtnAddSamplePage;

    @FXML
    public Button newSampleBtn;

    /**
     * Menues
     */
    @FXML
    public MenuItem addNewFilterContextMenu;


    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    /**
     * You can open a fastaFile at the Monoment and an item is added to the Accordion TODO Sample pop up???
     * @param event
     * @throws IOException
     */
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

    @FXML
    public void addNewFilterContextMenuClicked(ActionEvent event) throws IOException {
        Stage filterPopUp = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("filterview2.fxml"));
        filterPopUp.setTitle("New Filter");
        filterPopUp.setScene(new Scene(root, 400, 400));
        filterPopUp.show();
    }

}
