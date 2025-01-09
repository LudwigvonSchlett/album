package ensisa.album.command;

import ensisa.album.AlbumController;
import ensisa.album.model.ImageModel;
import javafx.collections.ObservableList;

import java.util.List;

public class ImageBackgroundForwardCommand implements UndoableCommand {
    private AlbumController controller;
    private ImageModel imageToMoveForward;
    private ObservableList<ImageModel> allImages;
    private ObservableList<ImageModel> allImagesUndo;
    private int indexAllImages;

    public ImageBackgroundForwardCommand(ObservableList<ImageModel> allImages, int indexAllImages, ImageModel imageToMoveForward, AlbumController controller) {
        this.imageToMoveForward = imageToMoveForward;
        this.controller = controller;
        this.allImages = allImages;
        this.indexAllImages = indexAllImages;
        this.allImagesUndo = allImages;
    }

    @Override
    public void undo() {
        controller.deselectAll();
        new ImageBackgroundBackwardCommand(allImages, indexAllImages + 1, imageToMoveForward, controller).execute();
//        ImageModel previousImage = allImages.get(indexAllImages);
//        allImages.set(indexAllImages, imageToMoveForward);
//        allImages.set(indexAllImages + 1, previousImage);
    }

    @Override
    public void execute() {
        controller.deselectAll();
        // Échange avec l'image suivante
        ImageModel nextImage = allImages.get(indexAllImages + 1);
        allImages.set(indexAllImages + 1, imageToMoveForward);
        allImages.set(indexAllImages, nextImage);
    }
}
