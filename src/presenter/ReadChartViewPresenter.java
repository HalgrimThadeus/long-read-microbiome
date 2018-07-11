package presenter;

import model.FilteredSample;
import view.ReadChartView;

public class ReadChartViewPresenter {

    private FilteredSample filteredSample;

    private ReadChartView readChartView;

    public ReadChartViewPresenter(FilteredSample filteredSample, ReadChartView readChartView){
        this.filteredSample = filteredSample;
        this.readChartView = readChartView;

        filteredSample.getSample().addListener((observable, oldValue, newValue) -> {
            readChartView.addReads(newValue.getReads());
        });
    }



}
