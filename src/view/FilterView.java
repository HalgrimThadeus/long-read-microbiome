package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import presenter.FilterPresenter;

import java.io.IOException;

public class FilterView extends AnchorPane {

    final ObservableList<String>  names = FXCollections.observableArrayList();

    @FXML
    private ListView<String> filterList = new ListView(names);


    private FilterPresenter filterPresenter;

    public FilterView() {
        this.filterPresenter = new FilterPresenter(this);
    }

    public ListView getFilterView(){
        return filterList;
    }
    public FilterPresenter getFilterPresenter() {
        return filterPresenter;
    }

    @FXML
    public void newFilterBtnClicked(ActionEvent actionEvent) throws IOException {
        filterPresenter.openNewFilterDialog();
    }


    public void updateFilterListView(String name){
        if(!names.contains(name)) {
            names.add(name);
            filterList.setItems(names);
        }
    }

    public void onDoubleClickListItem(){
        filterList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent click) {
                if(click.getClickCount() == 2){
                    String selectedFilter = filterList.getSelectionModel().getSelectedItem();
                    try {
                        filterPresenter.openFilterDialog(selectedFilter);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }

    public void onDragListItem(){
        filterList.setOnDragDetected(event -> {

            Dragboard db = filterList.startDragAndDrop(TransferMode.ANY);

            /* Put a string on a dragboard */
            ClipboardContent content = new ClipboardContent();
            content.put(WorkView.FILTER, filterList.getSelectionModel().getSelectedItem());
            db.setContent(content);

            event.consume();
        });
    }

    public void deleteFilter(){
        filterList.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                String filterToRemove = filterList.getSelectionModel().getSelectedItem();
                names.remove(filterToRemove);
                filterList.setItems(names);
            }
        });
    }
}
