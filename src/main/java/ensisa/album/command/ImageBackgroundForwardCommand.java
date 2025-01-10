package ensisa.album.command;

import ensisa.album.AlbumController;
import ensisa.album.model.ImageModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ImageBackgroundForwardCommand implements UndoableCommand {
    private AlbumController controller;
    private ImageModel imageToMoveForward; // Image à déplacer
    private ObservableList<ImageModel> allImages; // Liste des images
    private ObservableList<ImageModel> allImagesUndo; // Snapshot de la liste initiale
    private int indexAllImages; // Index de l'image à déplacer

    public ImageBackgroundForwardCommand(ObservableList<ImageModel> allImages, int indexAllImages, ImageModel imageToMoveForward, AlbumController controller) {
        this.imageToMoveForward = imageToMoveForward;
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

        // Échange avec l'image suivante
        ImageModel nextImage = allImages.get(indexAllImages + 1);
        allImages.set(indexAllImages + 1, imageToMoveForward);
        allImages.set(indexAllImages, nextImage);

        controller.getDocument().getImages().setAll(allImages);
    }
}
