package ensisa.album;

import ensisa.album.model.Document;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

public class AlbumController {

    private Document document;

    public AlbumController() {
        document = new Document();
    }

    public void initialize() {
        closeMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN));
        importPhotoMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.I, KeyCombination.CONTROL_DOWN));
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    @FXML
    public Pane testPane;

    @FXML
    private MenuItem closeMenuItem;

    @FXML
    private MenuItem importPhotoMenuItem;

    @FXML
    private void quitMenuAction() {
        Platform.exit();
    }

    @FXML
    private void importPhotoMenuAction() {
        FileChooser fileChooser = new FileChooser();
    }



}