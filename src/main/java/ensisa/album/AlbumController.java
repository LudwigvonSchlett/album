package ensisa.album;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public class AlbumController {


    public AlbumController() {

    }

    public void initialize() {

    }

    @FXML
    public Pane testPane;

    @FXML
    private void quitMenuAction() {
        Platform.exit();
    }

}