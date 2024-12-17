package ensisa.album;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Pane;

public class AlbumController {


    public AlbumController() {

    }

    public void initialize() {
        closeMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN));
        importPhotoMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.I, KeyCombination.CONTROL_DOWN));
    }
    @FXML
    private MenuItem closeMenuItem;

    @FXML
    private MenuItem importPhotoMenuItem;

    @FXML
    public Pane testPane;

    @FXML
    private void quitMenuAction() {
        Platform.exit();
    }

}