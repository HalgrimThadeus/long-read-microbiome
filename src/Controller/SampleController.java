package Controller;

import Model.FastAEntry;
import Model.IO.FastAIO;
import javafx.event.EventHandler;
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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class SampleController {


    public String readInFastaFileInHtml(File fastaFile) throws IOException {
        FileReader reader = new FileReader(fastaFile);
        FastAIO fastaReader = new FastAIO();
        List<FastAEntry> fastaEntries = fastaReader.readFastA(reader);

        String file = "";
        for(int i = 0; i < fastaEntries.size(); i++){
            file += fastaEntries.get(i).getHeader() + "<br/> " +
                    fastaEntries.get(i).getSequence() + "<br/>";
            file += "\n";
        }

        return file;

    }


    public File getNewFiles(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("BioFiles","*.fasta", "*.gff", "*.csv")
        );

        File selectedFile = fileChooser.showOpenDialog(new Stage());

        return selectedFile;
    }

    public void addNewSampleInAccordion(Accordion sampleAccordion){
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


        newAccordionPane.setOnDragDetected(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                /* drag was detected, start a drag-and-drop gesture*/
                /* allow any transfer mode */
                Dragboard db = newAccordionPane.startDragAndDrop(TransferMode.ANY);

                /* Put a string on a dragboard */
                ClipboardContent content = new ClipboardContent();
                content.putString(newAccordionPane.getText());
                db.setContent(content);

                event.consume();
            }
        });

        sampleAccordion.getPanes().add(newAccordionPane);
    }


}
