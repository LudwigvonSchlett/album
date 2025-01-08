package ensisa.album.command;

import ensisa.album.AlbumController;
import ensisa.album.model.ImageModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DeleteCommand implements UndoableCommand {
    private AlbumController controller;
    private List<ImageModel> savedImages;
    private Set<ImageModel> savedSelectedImages;

    public DeleteCommand(AlbumController controller) {
        this.controller = controller;
        savedImages = new ArrayList<>(controller.getDocument().getImages());
        savedSelectedImages = controller.getSelectedImages();
    }

    @Override
    public void execute() {
        controller.deselectAll();
        controller.getDocument().getImages().removeAll(savedSelectedImages);
    }

    @Override
    public void undo() {
        controller.deselectAll();
        controller.getDocument().getImages().clear();
        controller.getDocument().getImages().addAll(savedImages);
        controller.getSelectedImages().addAll(savedSelectedImages);
    }
}

