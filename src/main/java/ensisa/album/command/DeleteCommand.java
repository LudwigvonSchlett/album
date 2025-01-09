package ensisa.album.command;

import ensisa.album.AlbumController;
import ensisa.album.model.ImageModel;

import java.util.ArrayList;
import java.util.List;

public class DeleteCommand implements UndoableCommand {
    private AlbumController controller;
    private List<ImageModel> allImages;
    private List<ImageModel> imagesToDelete;

    public DeleteCommand(List<ImageModel> imagesToDelete, AlbumController controller) {
        this.imagesToDelete = imagesToDelete;
        this.controller = controller;
        allImages = new ArrayList<>(controller.getDocument().getImages());
    }

    @Override
    public void execute() {
        controller.getDocument().getImages().removeAll(imagesToDelete);
    }

    @Override
    public void undo() {
        controller.deselectAll();
        controller.getDocument().getImages().clear();
        controller.getDocument().getImages().addAll(allImages);
        controller.getSelectedImages().addAll(imagesToDelete);
    }
}

