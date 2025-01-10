package ensisa.album.command;

import ensisa.album.AlbumController;
import ensisa.album.model.ImageModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ImageBackgroundBackwardCommand implements UndoableCommand {
    private AlbumController controller;
    private ImageModel imageToMoveBackward; // Image à déplacer
    private ObservableList<ImageModel> allImages; // Liste des images
    private int indexAllImages; // Index de l'image à déplacer
    private ObservableList<ImageModel> allImagesUndo; // Snapshot de la liste initiale

    public ImageBackgroundBackwardCommand(ObservableList<ImageModel> allImages, int indexAllImages, ImageModel imageToMoveBackward, AlbumController controller) {
        this.imageToMoveBackward = imageToMoveBackward;
        this.controller = controller;
        this.allImages = allImages;
        this.indexAllImages = indexAllImages;
        this.allImagesUndo = FXCollections.observableArrayList(allImages);
    }

    @Override
    public void undo() {
        controller.deselectAll();

        allImages = FXCollections.observableArrayList(allImagesUndo);

        controller.getDocument().getImages().setAll(allImages);
    }

    @Override
    public void execute() {
        controller.deselectAll();

        // Echange avec l'image précédente
        ImageModel previousImage = allImages.get(indexAllImages - 1);
        allImages.set(indexAllImages - 1, imageToMoveBackward);
        allImages.set(indexAllImages, previousImage);

        controller.getDocument().getImages().setAll(allImages);
    }
}
