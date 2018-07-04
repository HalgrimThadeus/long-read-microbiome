package view;

import javafx.event.ActionEvent;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import presenter.FilterPresenter;

public class FilterView extends AnchorPane {
    private FilterPresenter filterPresenter;

    public FilterView() {
        this.filterPresenter = new FilterPresenter(this);
    }

    public FilterPresenter getFilterPresenter() {
        return filterPresenter;
    }

    public void addNewFilterContextMenuClicked(ActionEvent actionEvent) {
    }
}
