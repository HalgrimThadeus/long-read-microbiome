package Model;

import static Model.FilterBuilder.*;

public class FilteredSample {

    private Sample sample;
    private Filter filter;

    public FilteredSample(Sample sample, Filter filter) {
        this.sample = sample;
        this.filter = filter;
    }

    public Sample getSample() {
        return sample;
    }
    public Filter getFilter() {
        return filter;
    }

    public void applyFilter(){
        this.sample.setReads(this.filter.suitable(this.sample));
    }

}
