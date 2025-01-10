package ensisa.album.command;

import ensisa.album.AlbumController;
import ensisa.album.model.ImageModel;
import javafx.collections.ObservableList;

import java.util.List;

public class ImageBackgroundBackwardCommand implements UndoableCommand {
    private AlbumController controller;
    private ImageModel imageToMoveBackward;
    private ObservableList<ImageModel> allImages;
    private int indexAllImages;
    private ObservableList<ImageModel> allImagesUndo;

    public ImageBackgroundBackwardCommand(ObservableList<ImageModel> allImages, int indexAllImages, ImageModel imageToMoveBackward, AlbumController controller) {
        this.imageToMoveBackward = imageToMoveBackward;
        this.controller = controller;
        this.allImages = allImages;
        this.indexAllImages = indexAllImages;
        this.allImagesUndo = allImages;
    }

    @Override
    public void undo() {
        controller.deselectAll();
        new ImageBackgroundForwardCommand(allImages, indexAllImages - 1, imageToMoveBackward, controller).execute();

        System.out.println("Click on ImageBackgroundBackwardCommand undo");
        controller.getUndoRedoHistory().printStacks();
        //        ImageModel nextImage = allImages.get(indexAllImages);
//        allImages.set(indexAllImages, imageToMoveBackward);
//        allImages.set(indexAllImages - 1, nextImage);
    }

    @Override
    public void execute() {
        controller.deselectAll();
        // Echange avec l'image précédente
        ImageModel previousImage = allImages.get(indexAllImages - 1);
        allImages.set(indexAllImages - 1, imageToMoveBackward);
        allImages.set(indexAllImages, previousImage);

        controller.getDocument().getImages().setAll(allImages);
        System.out.println("Click on ImageBackgroundBackwardCommand");
        controller.getUndoRedoHistory().printStacks();
    }
}
