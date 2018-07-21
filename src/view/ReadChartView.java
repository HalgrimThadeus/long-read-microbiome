package view;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import presenter.ReadChartViewPresenter;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class ReadChartView implements Initializable {


    private ReadChartViewPresenter readChartViewPresenter;



    @FXML
    public VBox sequences;

    @FXML
    public VBox names;

    @FXML
    public NumberAxis xAxis;

    @FXML
    public Label filterLabel;

    @FXML
    public Spinner<Integer> barWidthSpinner;

    @FXML
    private Slider zoomSlider;

    @FXML
    private AnchorPane readChartPane;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private ToolBar toolBar;

    @FXML
    private Button searchGeneButton;

    @FXML
    private TextField searchGeneTextField;

    public IntegerProperty barWidth = new SimpleIntegerProperty(5);
    private DoubleProperty zoomFactor = new SimpleDoubleProperty(1);

    private final int BASIC_WIDTH = 740;

    public ObservableList<AnchorPane> genPane = FXCollections.observableArrayList();
    public ObservableList<Rectangle> reads = FXCollections.observableArrayList();
    public ObservableList<AnchorPane> reversedGenPane = FXCollections.observableArrayList();
    public ObservableList<Label> name = FXCollections.observableArrayList();





    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Spiner is initialized
        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(5, 200);
        barWidthSpinner.setValueFactory(valueFactory);
        barWidthSpinner.getValueFactory().setValue(barWidth.get());
        barWidthSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            barWidth.set(newValue);
        });

        //Bindings
        toolBar.prefWidthProperty().bind(scrollPane.widthProperty().subtract(10));
        zoomFactor.bind(zoomSlider.valueProperty());
        readChartPane.prefHeightProperty().bind(scrollPane.heightProperty());
        readChartPane.prefWidthProperty().bind(zoomFactor.multiply(BASIC_WIDTH));
        readChartPane.maxWidthProperty().bind(zoomFactor.multiply(BASIC_WIDTH));
        readChartPane.minWidthProperty().bind(zoomFactor.multiply(BASIC_WIDTH));


        sequences.setSpacing(10);
        names.setSpacing(10);
        onAction();
    }

    private void onAction(){
        reads.addListener((ListChangeListener<? super Rectangle>) change ->{
            while(change.next()) {

                if(change.wasAdded()) {
                    VBox vBox = new VBox();

                    vBox.getChildren().add(genPane.get(change.getFrom()));
                    vBox.getChildren().add(reads.get(change.getFrom()));
                    vBox.getChildren().add(reversedGenPane.get(change.getFrom()));

                    sequences.getChildren().add(vBox);
                    names.getChildren().add(name.get(change.getFrom()));
                }
            }
        });
    }
    //Setters/Getters-----------------------------------------------------------------------------------------------


    public void setReadChartViewPresenter(ReadChartViewPresenter readChartViewPresenter){
        this.readChartViewPresenter = readChartViewPresenter;
    }

    //Listeners------------------------------------------------------------------------------------------------------

    @FXML
    private void onSearchGeneButtonClicked(){
        String geneName = ""+ this.searchGeneTextField.getText();
        System.out.println(geneName);
        readChartViewPresenter.markSearchedGenes(geneName);
    }

    @FXML
    private void onZoomGeneButtonClicked() throws IOException {
        String geneName = ""+ this.searchGeneTextField.getText();
        readChartViewPresenter.searchForGenByName(geneName);
    }

}
