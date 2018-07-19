package presenter;

import javafx.application.Platform;
import model.Comparator;
import view.ComparatorView;

public class ComparatorViewPresenter {
    ComparatorView comparatorView;
    Comparator comparator;

    public ComparatorViewPresenter(ComparatorView comparatorView, Comparator comparator) {
        this.comparatorView = comparatorView;
        this.comparator = comparator;

        recalculateData(10);

        comparatorView.getCategoriesProperty().bind(comparator.categories);
        comparatorView.getCounts1Property().bind(comparator.counts1);
        comparatorView.getCounts2Property().bind(comparator.counts2);

        //recalculateData(10);

        comparatorView.start(
                comparator.getFilteredSample1().getSample().getValue().getName(), comparator.getFilteredSample2().getSample().getValue().getName(),
                comparator.getFilteredSample1().getFilter().getValue().getName(), comparator.getFilteredSample2().getFilter().getValue().getName(),
                comparator.getComparisonMode());

    }

    public void recalculateData(int numberOfBins) {
        Platform.runLater(new Runnable() {
            @Override
           public void run() {
                //comparator.calculateBoundaries(numberOfBins);
                comparator.setCategoryNames(numberOfBins);
                comparator.groupData(numberOfBins);
                comparatorView.setRedraw();
            }
        });
    }
}
