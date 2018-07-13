package presenter;

import model.Comparator;
import view.ComparatorView;

import java.util.ArrayList;
import java.util.List;

public class ComparatorViewPresenter {
    ComparatorView comparatorView;
    public ComparatorViewPresenter(ComparatorView comparatorView, Comparator comparator) {
        this.comparatorView = comparatorView;
        List<List<Double>> dataResults = comparator.getData();

        comparatorView.start((ArrayList<Double>) dataResults.get(0),(ArrayList<Double>) dataResults.get(1),comparator.getFilteredSample1().getName(),comparator.getFilteredSample2().getName(), comparator.getComparisonMode());


    }
}
