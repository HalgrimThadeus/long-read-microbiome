package presenter;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import model.FilteredSample;
import model.GffEntry;
import model.Read;
import view.ReadChartView;
import view.WorkView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ReadChartViewPresenter {

    private FilteredSample filteredSample;

    private ReadChartView readChartView;

    public ReadChartViewPresenter(FilteredSample filteredSample, ReadChartView readChartView){
        this.filteredSample = filteredSample;
        this.readChartView = readChartView;
        onActions();

    }

    private void onActions(){
        this.filteredSample.getFilteredReads().addListener((ListChangeListener<? super Read>) change -> {
            while(change.next()) {

                if(change.wasAdded()) {
                    //this.readChartView.drawReads(getReadInformationOutOfRead(sortReads((List<Read>) change.getList())));
                    addReads();
                }
            }
        });
    }

    private void addReads(){
        List<Read> reads = sortReads(filteredSample.getFilteredReads());
        int maxLength = reads.get(0).getSequence().length();

        readChartView.xAxis.setUpperBound((maxLength + 1000)/1000 * 1000);
        readChartView.xAxis.setTickUnit(1000);

        for(Read read : reads){

            Rectangle rectangle = getSequence(read);

            AnchorPane[] genes = addGenes(read.getGFFEntries());

            readChartView.genPane.add(genes[0]);
            readChartView.reversedGenPane.add(genes[1]);
            readChartView.name.add(addName(read.getTaxonomicId() + ""));
            readChartView.reads.add(rectangle);



        }
    }

    private Rectangle getSequence(Read read){
        int length = read.getSequence().length();
        String id = read.getId();

        Rectangle rectangle = new Rectangle();

        rectangle.widthProperty().bind(readChartView.xAxis.widthProperty().divide(readChartView.xAxis.upperBoundProperty()).multiply(length));
        rectangle.heightProperty().bind(readChartView.barWidthSpinner.valueProperty());

        Tooltip t = new Tooltip("Sequence-Id: " + id + "\n" + "Length: " + length);
        Tooltip.install(rectangle, t);

        return rectangle;
    }

    private AnchorPane[] addGenes(List<GffEntry> gffEntries){

        AnchorPane genes = new AnchorPane();
        AnchorPane genesReversed = new AnchorPane();


        genes.prefHeightProperty().bind(readChartView.barWidthSpinner.valueProperty());
        genes.minHeightProperty().bind(readChartView.barWidthSpinner.valueProperty());
        genesReversed.prefHeightProperty().bind(readChartView.barWidthSpinner.valueProperty());
        genesReversed.minHeightProperty().bind(readChartView.barWidthSpinner.valueProperty());

        for (GffEntry gffEntry : gffEntries) {

            String name = "" + gffEntry.getAttributes().get(" Name");
            int start = gffEntry.getStart();
            int end = gffEntry.getEnd();
            boolean isReversed = (gffEntry.getStrand() == '+') ? false : true;


            Rectangle rectangle = new Rectangle();

            //TODO Make search gen work again

            //Genes with start reds the get blue
            if (isReversed) {
                rectangle.setFill(javafx.scene.paint.Color.rgb(34, 34, 178, 0.5));
                /*
                markColor.addListener((observable, oldValue, newValue) -> {

                    if (!name.contains(searchedGene)) {
                        rectangle.setFill(javafx.scene.paint.Color.rgb(34, 34, 178, 0.5));
                    } else {
                        rectangle.setFill(newValue);
                    }
                });
                */
                genesReversed.getChildren().add(rectangle);

            } else {
                rectangle.setFill(javafx.scene.paint.Color.rgb(178, 34, 34, 0.5));
                /*
                markColor.addListener((observable, oldValue, newValue) -> {
                    if (!name.contains(searchedGene)) {
                        rectangle.setFill(javafx.scene.paint.Color.rgb(178, 34, 34, 0.5));
                    } else {
                        rectangle.setFill(newValue);
                    }
                });
                */
                genes.getChildren().add(rectangle);
            }


            rectangle.heightProperty().bind(readChartView.barWidthSpinner.valueProperty());
            rectangle.setStrokeWidth(0.5);
            rectangle.setStroke(Color.BLACK);
            rectangle.xProperty().bind(readChartView.xAxis.widthProperty().divide(readChartView.xAxis.upperBoundProperty()).multiply(start));
            rectangle.widthProperty().bind(readChartView.xAxis.widthProperty().divide(readChartView.xAxis.upperBoundProperty()).multiply(end-start));

            //Add tooltip
            String tooltipMessage = name + "\n"
                    + "Start: " + start + " End: " + end + "\n"
                    + "Strand: " + ((isReversed) ? "+" : "-");
            Tooltip t = new Tooltip(tooltipMessage);
            Tooltip.install(rectangle, t);


            //genes.getChildren().add(rectangle);

        }

        //sequences.getChildren().add(pane);
        AnchorPane[] allGenes = new AnchorPane[2];
        allGenes[0] = genes;
        allGenes[1] = genesReversed;
        return allGenes;
    }

    private Label addName(String name){
        //TODO Transform them to names add listener to spinner
        javafx.scene.control.Label label = new Label(name);
        label.prefHeightProperty().bind(readChartView.barWidth.multiply(3));
        label.minHeightProperty().bind(readChartView.barWidth.multiply(3));
        label.maxHeightProperty().bind(readChartView.barWidth.multiply(3));

        return label;

    }


    private List<Read> sortReads(List<Read> reads){
        List<Read> sortedRead = new ArrayList<>(reads);
        sortedRead.sort((o1, o2) -> {
            if(o1.getSequence().length() < o2.getSequence().length()){
                return 1;
            }
            else{
                return -1;
            }
        });
        return sortedRead;
    }

    public class GeneData{
        public String name;
        public int start;
        public int end;
        public boolean isReversed;


    }

    public class SequenceData{
        public String seqId;
        public int taxId;
        public int length;
        public List<GeneData> geneData = new ArrayList<>();

        public List<GeneData> sortGeneData(List<GeneData> reads){
            List<GeneData> sortedRead = new ArrayList<>(geneData);
            sortedRead.sort(new Comparator<GeneData>() {
                @Override
                public int compare(GeneData o1, GeneData o2) {
                    if(o1.start < o2.start){
                        return -1;
                    }
                    else{
                        return 1;
                    }
                }
            });
            return sortedRead;
        }
    }


    private List<SequenceData> getReadInformationOutOfRead(List<Read> read){

        List<SequenceData> sequenceData = new ArrayList<>();

        for(Read r : read){
            SequenceData sequence = new SequenceData();
            sequence.seqId = r.getId();
            sequence.length = r.getSequence().length();
            sequence.taxId = r.getTaxonomicId();
            for(GffEntry gffEntry : r.getGFFEntries()){
                GeneData geneData = new GeneData();
                geneData.name = gffEntry.getAttributes().get(" Name");
                geneData.start = gffEntry.getStart();
                geneData.end = gffEntry.getEnd();
                geneData.isReversed = (gffEntry.getStrand() == '+') ? false : true;
                sequence.geneData.add(geneData);
            }
            sequenceData.add(sequence);
        }

        return sequenceData;
    }

    public void searchForGenByName(String genName) throws IOException {
        ReadChartView readChartView = new ReadChartView();
        ReadChartViewPresenter readChartViewPresenter = new ReadChartViewPresenter(this.filteredSample, readChartView);
        FXMLLoader loader = new FXMLLoader(WorkView.class.getResource("readChartView.fxml"));
        loader.setController(readChartView);
        readChartView.setReadChartViewPresenter(readChartViewPresenter);

        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();

        readChartView.drawZoomedReads(getReadInformationOutOfRead(getReadsByName(filteredSample.getSample().get().getReads(), genName)), genName);

    }

    private List<Read> getReadsByName(List<Read> reads, String name){
        List<Read> readByName = new ArrayList<>();

        for(Read read : reads){
            for(GffEntry gffEntry : read.getGFFEntries()){
                if(name.equals(gffEntry.getAttributes().get(" Name"))){
                    readByName.add(read);
                }
            }
        }

        return readByName;
    }





}
