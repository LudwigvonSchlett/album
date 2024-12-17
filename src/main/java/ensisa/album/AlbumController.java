package ensisa.album;

import ensisa.album.model.Document;
import ensisa.album.model.ImageEditor;
import ensisa.album.model.ImageModel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.List;

public class AlbumController {

    private double offsetX = 10;
    private double offsetY = 10;

    private Document document;
    private ImageEditor imageEditor;

    public AlbumController() {
        document = new Document();

    }

    public void initialize() {
        imageEditor = new ImageEditor(testPane);
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
    private void importPhotoAction() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));
        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(testPane.getScene().getWindow());
        if (selectedFiles != null) {
            for (File file : selectedFiles) {
                ImageModel image = new ImageModel(file.toURI().toString());
                image.offset(offsetX, offsetY);
                imageEditor.createImage(image);

                offsetX += 20; // Adjust the offset as needed
                offsetY += 20; // Adjust the offset as needed
            }
        }
    }
}