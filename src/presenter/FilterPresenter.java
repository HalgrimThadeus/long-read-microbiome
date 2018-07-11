package presenter;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Filter;
import view.FilterView;
import view.MainView;
import view.NewFilterPopUp;

import java.io.IOException;
import java.util.List;


public class FilterPresenter {
    private NewFilterPopUpPresenter newFilterPopUpPresenter = null;
    private ObservableList<Filter> filters;
    private FilterView filterView;

    public FilterPresenter(FilterView filterView) {
        this.filterView = filterView;
    }

    public void initialize(NewFilterPopUpPresenter newFilterPopUpPresenter, ObservableList<Filter> filters){
        this.filters = filters;
        this.newFilterPopUpPresenter = newFilterPopUpPresenter;

        filters.addListener((ListChangeListener<Filter>) change -> {
            while(change.next()) {
                if(change.wasAdded()) {
                    List<Filter> addedFilters = (List<Filter>) change.getAddedSubList();

                    for (Filter filter:addedFilters) {
                        this.addFilter(filter);
                    }
                }
                else if(change.wasRemoved()) {
                    List<Filter> removedFilters = (List<Filter>) change.getRemoved();

                    for (Filter filter:removedFilters) {
                        this.removeFilter(filter);
                    }
                }

            }
        });
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
            throw new ExceptionInInitializerError("Could not Start NewFilterPopUp, because the presenter hasn't been initialized");
        }
    }

    public  void openFilterDialog(String filterName) throws IOException{
        if (newFilterPopUpPresenter != null) {
            NewFilterPopUp newFilterPopUp = new NewFilterPopUp(newFilterPopUpPresenter);
            Stage filterPopUp = new Stage();

            FXMLLoader loader = new FXMLLoader(MainView.class.getResource("newFilterPopUp.fxml"));
            loader.setController(newFilterPopUp);
            Parent root = loader.load();
            filterPopUp.setTitle("New Filter");

            Filter usedFilter = getFilterByName(filterName);
            List<String> usedKeys = usedFilter.getKeys();
            List<String> usedValues = usedFilter.getValues();
            List<String> usedCompare = usedFilter.getCompare();

            newFilterPopUp.setFilterName(filterName);

            for(int i = 0; i < usedKeys.size();i++){
                String key = usedKeys.get(i);
                if(key.equals("GC")){
                    newFilterPopUp.setGccontent(usedValues.get(i));
                    newFilterPopUp.setGCCompareChoice(usedCompare.get(i));
                }
                else if(key.equals("Length")){
                    newFilterPopUp.setLengthvalue(usedValues.get(i));
                    newFilterPopUp.setLengthCompareChoice(usedCompare.get(i));
                }
                else if(key.equals("Score")){
                    newFilterPopUp.setScorevalue(usedValues.get(i));
                    newFilterPopUp.setscoreCompareChoice(usedCompare.get(i));
                }
                else if(key.equals("Tax")){
                    newFilterPopUp.setTaxaid(usedValues.get(i));
                }
                else if(key.equals("Gen")){
                    newFilterPopUp.setGenname(usedValues.get(i));
                }
            }


            filterPopUp.setScene(new Scene(root, 600, 400));
            filterPopUp.show();
        }
        else {
            throw new ExceptionInInitializerError("Could not Start NewFilterPopUp, because the presenter hasn't been initialized");
        }



    }

    private Filter getFilterByName(String name){

        for(Filter f: filters){
            System.err.println(f.getName());
            if(f.getName().equals(name)){
                 return f;
            }
        }
        return null;
    }

    private void addFilter(Filter filter){
        filterView.updateFilterListView(filter.getName());
    }

    private  void removeFilter(Filter filter){
        filterView.getFilterView().getItems().remove(filter.getName());
        }
    }







