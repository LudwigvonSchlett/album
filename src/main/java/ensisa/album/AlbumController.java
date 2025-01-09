package ensisa.album;

import ensisa.album.command.AddImageCommand;
import ensisa.album.command.DeleteCommand;
import ensisa.album.model.Document;
import ensisa.album.model.ImageModel;
import ensisa.album.tools.SelectTool;
import ensisa.album.tools.Tool;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AlbumController {

    private double offsetX = 10;
    private double offsetY = 10;

    private Document document;
    private ImageEditor imageEditor;
    private ObservableSet<ImageModel> selectedImages;
    private final ObjectProperty<Tool> currentTool;
    private final SelectTool selectTool;
    private final UndoRedoHistory undoRedoHistory;

    public AlbumController() {
        document = new Document();
        selectTool = new SelectTool(this);
        currentTool = new SimpleObjectProperty<>(selectTool);
        selectedImages = FXCollections.observableSet();
        undoRedoHistory = new UndoRedoHistory();
    }

    public void initialize() {
        imageEditor = new ImageEditor(testPane);
        closeMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN));
        importPhotoMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.I, KeyCombination.CONTROL_DOWN));
        initializeMenus();
        observeDocument();
        observeSelection();
    }

    private void observeDocument() {
        document.getImages().addListener(new ListChangeListener<ImageModel>() {
            public void onChanged(ListChangeListener.Change<? extends ImageModel> c) {
                while (c.next()) {
                    // Des lignes ont été supprimées du modèle
                    for (ImageModel image : c.getRemoved()) {
                        deselectImage(image);
                        imageEditor.removeImage(image);
                    }
                    // Des lignes ont été ajoutées au modèle
                    for (ImageModel image : c.getAddedSubList()) {
                        imageEditor.createImage(image);
                    }
                }
            }
        });
    }

    private void observeSelection() {
        selectedImages.addListener(new SetChangeListener<ImageModel>() {
            @Override
            public void onChanged(Change<? extends ImageModel> change) {
                if (change.wasRemoved()) {
                    imageEditor.deselectImage(change.getElementRemoved());
                }
                if (change.wasAdded()) {
                    imageEditor.selectImage(change.getElementAdded());
                }
            }
        });
    }

    public Document getDocument() {
        return document;
    }

    public UndoRedoHistory getUndoRedoHistory() {
        return undoRedoHistory;
    }

    public ImageModel findImageForPoint(double x, double y) {
        // Parcours les images en sens inverse (de la dernière ajoutée à la première, cela permet de sélectionner
        // l'image la plus en avant au niveau du plan)
        for (var iterator = getDocument().getImages().listIterator(getDocument().getImages().size()); iterator.hasPrevious(); ) {
            var image = iterator.previous();
            if (imageEditor.isPointInImage(x, y, image)){
                return image;
            }
        }
        return null;
    }

    public ObservableSet<ImageModel> getSelectedImages() {
        return selectedImages;
    }

    public void setSelectedImages(ObservableSet<ImageModel> selectedImages) {
        this.selectedImages = selectedImages;
    }

    public void selectImage(ImageModel image) {
        selectedImages.add(image);
    }

    public void deselectAll() {
        selectedImages.clear();
    }

    public void deselectImage(ImageModel image) {
        selectedImages.remove(image);
    }

    public ObjectProperty<Tool> currentToolProperty() {
        return currentTool;
    }

    public Tool getCurrentTool() {
        return currentTool.get();
    }

    public void setCurrentTool(Tool currentTool) {
        this.currentTool.set(currentTool);
    }

    private void initializeMenus() {
        undoItem.disableProperty().bind(undoRedoHistory.canUndoProperty().not());
        redoItem.disableProperty().bind(undoRedoHistory.canRedoProperty().not());
        deleteMenuItem.disableProperty().bind(Bindings.createBooleanBinding(() ->
                selectedImages.isEmpty(), selectedImages));
    }

    @FXML
    public Pane testPane;

    @FXML
    private MenuItem closeMenuItem;

    @FXML
    private MenuItem importPhotoMenuItem;

    @FXML
    private Button undoItem;

    @FXML
    private Button redoItem;

    @FXML
    private MenuItem deleteMenuItem;

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
                //imageEditor.createImage(image);
                undoRedoHistory.execute(new AddImageCommand(image, this));

                offsetX += 20; // Adjust the offset as needed
                offsetY += 20; // Adjust the offset as needed
            }
        }
    }

    @FXML
    private void imageForwardsBackground() {
        //ObservableSet<ImageModel> newSelectedImages = selectedImages;
//        ObservableList<ImageModel> newImages = document.getImages();
//        for (ImageModel image : newImages) {
//            document.getImages().remove(image);
//            document.getImages().add(image);
//        }


//        for (var iterator = getDocument().getImages().listIterator(getDocument().getImages().size()); iterator.hasNext(); ) {
//            var image = iterator.next();
//            if (iterator.hasNext()) {
//                var nextImage = iterator.next();
//                for (ImageModel selectedImage : selectedImages) {
//                    if (nextImage.equals(selectedImage)) {
//                        document.getImages().remove(image);
//                        document.getImages().remove(nextImage);
//                        document.getImages().add(nextImage);
//                        document.getImages().add(image);
//                    }
//                }
//            }
//
//        }

        ObservableList<ImageModel> images = document.getImages();
        for (int i = 0; i < images.size() - 1; i++) {
            ImageModel image = images.get(i);
            ImageModel nextImage = images.get(i + 1);
            if (selectedImages.contains(nextImage)) {
                images.set(i, nextImage);
                images.set(i + 1, image);
                i++; // Ignore la prochaine image comme elle a déjà été traitée
            }
        }
        //setSelectedImages(newSelectedImages);
    }

    @FXML
    private void imageBackwardsBackground() {
        ObservableList<ImageModel> images = document.getImages();
        for (int i = images.size(); i > 1; i--) {
            ImageModel image = images.get(i);
            ImageModel nextImage = images.get(i - 1);
            if (selectedImages.contains(nextImage)) {
                // Echange une photo sélectionnée avec celle qui se trouve au plan juste derrière (donc plus proche de l'indice 0 dans la liste document.getImages())
                images.set(i, nextImage);
                images.set(i - 1, image);
                i--; // Ignore la prochaine image comme elle a déjà été traitée
            }
        }
        //setSelectedImages(newSelectedImages);
    }

    @FXML
    private void mousePressedInEditor(MouseEvent event) {
        getCurrentTool().mousePressed(event);
    }

    @FXML
    private void mouseDraggedInEditor(MouseEvent event) {
        getCurrentTool().mouseDragged(event);
    }

    @FXML
    private void mouseReleasedInEditor(MouseEvent event) {
        getCurrentTool().mouseReleased(event);
    }

    @FXML
    private void mouseEntered(MouseEvent event) {
        getCurrentTool().mouseEntered(event);
    }

    @FXML
    void mouseExited(MouseEvent event) {
        getCurrentTool().mouseExited(event);
    }

    /*
    @FXML
    private void selectToolAction() {
        setCurrentTool(selectTool);
    }
    */

    @FXML
    private void undoMenuItemAction() {
        undoRedoHistory.undo();
    }
    @FXML
    private void redoMenuItemAction() {
        undoRedoHistory.redo();
    }

    @FXML
    private void deleteMenuItemAction() {
        List<ImageModel> imagesToDelete = new ArrayList<>(selectedImages);
        undoRedoHistory.execute(new DeleteCommand(imagesToDelete, this));
    }

}