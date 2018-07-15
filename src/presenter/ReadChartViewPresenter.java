package presenter;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
        filteredSample.getSample().addListener((observable, oldValue, newValue) -> {
            readChartView.drawReads(getReadInformationOutOfRead(sortReads(newValue.getReads())));
        });
    }

    private List<Read> sortReads(List<Read> reads){
        List<Read> sortedRead = new ArrayList<>(reads);
        sortedRead.sort(new Comparator<Read>() {
            @Override
            public int compare(Read o1, Read o2) {
                if(o1.getSequence().length() < o2.getSequence().length()){
                    return 1;
                }
                else{
                    return -1;
                }
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
        System.out.println(name);
        for(Read read : reads){
            for(GffEntry gffEntry : read.getGFFEntries()){
                if(name.equals(gffEntry.getAttributes().get(" Name"))){
                    readByName.add(read);
                }
            }
        }
        System.out.println(readByName);

        return readByName;
    }





}
