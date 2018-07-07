package presenter;

import javafx.stage.Stage;

public class ComparatorViewPresenter {
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public ComparatorViewPresenter(){
        stage.setScene();
    }
}
