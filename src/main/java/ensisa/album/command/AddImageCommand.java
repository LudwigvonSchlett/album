package ensisa.album.command;

import ensisa.album.AlbumController;
import ensisa.album.model.Document;
import ensisa.album.model.ImageModel;

public class AddImageCommand implements UndoableCommand {

    public ImageModel image;
    private AlbumController controller;

    public AddImageCommand(ImageModel image, AlbumController controller) {
        this.image = image;
        this.controller = controller;
    }

    @Override
    public void undo() {
        controller.deselectAll();
        controller.getDocument().getImages().remove(image);
    }

    @Override
    public void execute() {
        controller.deselectAll();
        controller.getDocument().getImages().add(image);
    }
}
