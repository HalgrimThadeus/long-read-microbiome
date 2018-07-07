package presenter;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Filter;
import model.Project;
import view.FilterView;
import view.MainView;
import view.NewFilterPopUp;
import view.NewSamplePopUp;

import java.io.IOException;
import java.util.List;


public class FilterPresenter {
    private NewFilterPopUpPresenter newFilterPopUpPresenter = null;
    private ObservableList<Filter> listOfFilters;
    private FilterView filterView;

    public FilterPresenter(FilterView filterView) {
        this.filterView = filterView;
    }

    public void initialize(NewFilterPopUpPresenter newFilterPopUpPresenter, ObservableList<Filter> listOfFilters){
        this.listOfFilters = listOfFilters;
        this.newFilterPopUpPresenter = newFilterPopUpPresenter;

        listOfFilters.addListener((ListChangeListener<Filter>) c ->{
            c.next();
            List<Filter> addedFilters = (List<Filter>) c.getAddedSubList();
            for(Filter filter:addedFilters){
                filterView.updateFilterListView(filter.getName());
            }

        } );
    }
    public void openNewFilterDialog() throws IOException {
        if (newFilterPopUpPresenter != null) {
            NewFilterPopUp newFilterPopUp = new NewFilterPopUp(newFilterPopUpPresenter);
            Stage filterPopUp = new Stage();

            FXMLLoader loader = new FXMLLoader(MainView.class.getResource("newFilterPopUp.fxml"));
            loader.setController(newFilterPopUp);

            Parent root = loader.load();
            filterPopUp.setTitle("New Filter");
            filterPopUp.setScene(new Scene(root, 600, 400));
            filterPopUp.show();
        }
        else {
            throw new ExceptionInInitializerError("Could not Start NewFilterPopUp, because the Presenter hasn't been initialized");
        }
    }





}
