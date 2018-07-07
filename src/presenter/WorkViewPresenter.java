package presenter;


import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ToolBar;
import model.Project;
import model.Sample;
import model.io.SampleReader;
import model.io.TaxIO;
import view.MainView;
import view.WorkView;

import java.io.FileNotFoundException;
import java.util.Observable;

public class WorkViewPresenter {

    private WorkView workView;

    public WorkViewPresenter(WorkView workView){
        this.workView = workView;
    }

    public Sample addNewSampleToMainPain(String sampleName) {
        //TODO Change that you get this from project by name
        String fastaFile = "res/ehec/ehec.f1000000-ex.fasta";
        String gffFile = "res/ehec/ehec.f1000000-all.gff";
        String taxFile = "res/ehec/ehec.f1000000-readname2taxonid.txt";
        try{
            Sample sample = SampleReader.read(fastaFile, gffFile, taxFile);
            return sample;
        }catch (Exception e){

        }
        return null;

    }

}
