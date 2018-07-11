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

            Filter usedFilter = new Filter(null,null,null,null);
            for(Filter f: listOfFilters){
                if(f.getName().equals(filterName)){
                    usedFilter = f;
                    break;
                }
            }
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

    public  void removeFromList(String name){
        for(Filter f: listOfFilters){
            if(f.getName().equals(name)){
                listOfFilters.remove(f);
            }
        }
    }






}
