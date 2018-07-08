package model;

public class FilteredSample {

    /**
     * actual sample to be filtered
     */
    private Sample sample;
    /**
     * actual given filter that has to be applied on the actual sample
     */
    private Filter filter;

    /**
     * creates a new FilteredSample, given a sample and a filter to be applied
     * @param sample
     * @param filter
     */
    public FilteredSample(Sample sample, Filter filter) {
        this.sample = sample;
        this.filter = filter;
    }

    /**
     * @return actual sample
     */
    public Sample getSample() {
        return sample;
    }
    /**
     * @return actual filter
     */
    public Filter getFilter() {
        return filter;
    }

    public void setSample(Sample sample) {
        this.sample = sample;
    }

    /**
     * applies the given filter to the given sample; updates the samples list of reads
     * @param
     * @return
     */
    public void applyFilter(){
        this.sample.setReads(this.filter.suitable(this.sample));
    }

}
