package presenter;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
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
import model.Filter;
import model.FilteredSample;
import model.GffEntry;
import model.Read;
import view.ReadChartView;
import view.WorkView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadChartViewPresenter {

    private FilteredSample filteredSample;
    private ReadChartView readChartView;

    private BooleanProperty isSearched = new SimpleBooleanProperty(false);
    private String searchedGene = "";

    public ReadChartViewPresenter(FilteredSample filteredSample, ReadChartView readChartView){
        this.filteredSample = filteredSample;
        this.readChartView = readChartView;
        onActions();

    }

    private void onActions(){
        this.filteredSample.getFilteredReads().addListener((ListChangeListener<? super Read>) change -> {
            if(filteredSample.getFilter() != null && filteredSample.getFilter().get() != null){
                readChartView.filterChooseBox.getItems().add(filteredSample.getFilter().get().getName());
            }
        });


        this.filteredSample.getFilteredReads().addListener((ListChangeListener<? super Read>) change -> {

            readChartView.sequences.getChildren().clear();
            readChartView.names.getChildren().clear();


            while(change.next()) {

                if(change.wasAdded()) {
                    //this.readChartView.drawReads(getReadInformationOutOfRead(sortReads((List<Read>) change.getList())));
                    addReads();
                }
            }
        });
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

    private List<Read> getReadsByName(List<Read> reads, String name){
        List<Read> readByName = new ArrayList<>();

        for(Read read : reads){
            for(GffEntry gffEntry : read.getGFFEntries()){
                if(gffEntry.getAttributes().containsKey(" Name")){
                    if(gffEntry.getAttributes().get(" Name").contains(name)){
                        readByName.add(read);
                    }
                }
            }
        }

        return readByName;
    }



    //Creating Chart Elements----------------------------------------------------------------------------------------------

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

        rectangle.setFill(Color.GRAY);
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


            //Genes with start reds the get blue
            if (isReversed) {
                rectangle.setFill(javafx.scene.paint.Color.rgb(34, 34, 178, 0.5));
                isSearched.addListener((observable, oldValue, newValue) -> {

                    if (!name.contains(searchedGene)) {
                        rectangle.setFill(Color.rgb(34, 34, 178, 0.5));
                    } else {
                        rectangle.setFill(Color.rgb(255, 215 , 0, 0.5));
                    }
                });
                genesReversed.getChildren().add(rectangle);

            } else {
                rectangle.setFill(javafx.scene.paint.Color.rgb(178, 34, 34, 0.5));
                isSearched.addListener((observable, oldValue, newValue) -> {
                    if (!name.contains(searchedGene)) {
                        rectangle.setFill(Color.rgb(178, 34, 34, 0.5));
                    } else {
                        rectangle.setFill(Color.rgb(255, 215 , 0, 0.5));
                    }
                });
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

        javafx.scene.control.Label label = new Label(name);
        label.prefHeightProperty().bind(readChartView.barWidth.multiply(3));
        label.minHeightProperty().bind(readChartView.barWidth.multiply(3));
        label.maxHeightProperty().bind(readChartView.barWidth.multiply(3));

        return label;

    }

    //Searching-----------------------------------------------------------------------------------------------------------



    public void markSearchedGenes(String geneName){
        searchedGene = geneName;

        isSearched.set(true);
        isSearched.set(false);
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

        Filter filter = new Filter();

        //TODO Add a filter to filterView
        //TODO isGen filter is not working proper

        List<Read> reads = getReadsByName(filteredSample.getFilteredReads(), genName);

        getZoomAreaReads(readChartView, reads, genName);
    }


    private int AREA_WIDTH = 1000;

    private void getZoomAreaReads(ReadChartView readChartView, List<Read> reads, String genName) {

        readChartView.xAxis.setUpperBound(2000);

        for (Read read : reads) {

            int length = 2 * AREA_WIDTH;
            String id = read.getId();
            String taxId = read.getTaxonomicId() + "";


            //If there are more then one, always the first is taken
            int startGen = Integer.MAX_VALUE;
            for (GffEntry gens : read.getGFFEntries()) {
                if(gens.getAttributes().containsKey( " Name")) {
                    if (gens.getStart() < startGen && gens.getAttributes().get(" Name").contains(genName)) {
                        startGen = gens.getStart();
                    }
                }
            }

            //Area width, where genes are painted
            int lowerBound = startGen - AREA_WIDTH;
            int upperBound = startGen + AREA_WIDTH;

            AnchorPane[] genes = addZoomedGenes(read.getGFFEntries(), lowerBound, startGen, upperBound);

            readChartView.genPane.add(genes[0]);
            readChartView.reversedGenPane.add(genes[1]);
            readChartView.name.add(addName(taxId));
            readChartView.reads.add(getZoomedSequence(read));

        }
        readChartView.xAxis.setLowerBound(-1000);
        readChartView.xAxis.setUpperBound(1000);
        readChartView.xAxis.setTickUnit(100);
    }

    private Rectangle getZoomedSequence(Read read){
        String id = read.getId();

        Rectangle rectangle = new Rectangle();

        rectangle.setFill(Color.GRAY);
        rectangle.widthProperty().bind(readChartView.xAxis.widthProperty());
        rectangle.heightProperty().bind(readChartView.barWidthSpinner.valueProperty());

        Tooltip t = new Tooltip("Sequence-Id: " + id + "\n" + "Length: " + read.getSequence().length());
        Tooltip.install(rectangle, t);

        return rectangle;
    }

    private AnchorPane[] addZoomedGenes(List<GffEntry> gffEntries, int lowerBound, int startGen, int upperBound){

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

            System.out.println(start);
            System.out.println(startGen);

            if(start >= startGen-AREA_WIDTH && start <= startGen+AREA_WIDTH){
                start = start - startGen + AREA_WIDTH;
                if(start < 0){
                    start = 0;
                }
                if(end > upperBound) {
                    end = 2*AREA_WIDTH;
                }else{
                    end = end-startGen + AREA_WIDTH;
                }
                System.out.println(start);
                System.out.println(end);
            }
            else{
                continue;
            }


            Rectangle rectangle = new Rectangle();

            //Genes with start reds the get blue
            if (isReversed) {
                rectangle.setFill(javafx.scene.paint.Color.rgb(34, 34, 178, 0.5));
                isSearched.addListener((observable, oldValue, newValue) -> {

                    if (!name.contains(searchedGene)) {
                        rectangle.setFill(Color.rgb(34, 34, 178, 0.5));
                    } else {
                        rectangle.setFill(Color.rgb(255, 215 , 0, 0.5));
                    }
                });
                genesReversed.getChildren().add(rectangle);

            } else {
                rectangle.setFill(javafx.scene.paint.Color.rgb(178, 34, 34, 0.5));
                isSearched.addListener((observable, oldValue, newValue) -> {
                    if (!name.contains(searchedGene)) {
                        rectangle.setFill(Color.rgb(178, 34, 34, 0.5));
                    } else {
                        rectangle.setFill(Color.rgb(255, 215 , 0, 0.5));
                    }
                });
                genes.getChildren().add(rectangle);
            }


            rectangle.heightProperty().bind(readChartView.barWidthSpinner.valueProperty());
            rectangle.setStrokeWidth(0.5);
            rectangle.setStroke(Color.BLACK);
            rectangle.xProperty().bind(readChartView.xAxis.widthProperty().divide(readChartView.xAxis.upperBoundProperty()).multiply(start).multiply(9.5));
            rectangle.widthProperty().bind(readChartView.xAxis.widthProperty().divide(readChartView.xAxis.upperBoundProperty()).multiply(end-start).multiply(9.5));

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


}
