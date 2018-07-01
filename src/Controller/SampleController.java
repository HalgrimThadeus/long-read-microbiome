package Controller;

import Model.FastAEntry;
import Model.IO.FastAIO;
import View.MainView;
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

    public void openSamplePane() throws IOException {
        Stage filterPopUp = new Stage();
        Parent root = FXMLLoader.load(MainView.class.getResource("addNewSampleView.fxml"));
        filterPopUp.setTitle("New Sample");
        filterPopUp.setScene(new Scene(root, 600, 250));
        filterPopUp.show();
    }



    public File getNewFiles(String extension){
        String usedExtension = "*." + extension;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("BioFiles",usedExtension)
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

        TreeItem<String> fastaChild = new TreeItem<>("Files");
        fastaChild.setExpanded(false);


        root.getChildren().add(fastaChild);
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
