package ensisa.album.command;

import ensisa.album.AlbumController;
import ensisa.album.model.ImageModel;

public class MoveImageCommand implements UndoableCommand {

    public ImageModel image;
    public double dx;
    public double dy;

    public MoveImageCommand(ImageModel image, double deltaX, double deltaY) {
        this.image = image;
        this.dx = deltaX;
        this.dy = deltaY;
    }

    @Override
    public void undo() {
        image.offset(-dx, -dy);
    }

    @Override
    public void execute() {
        image.offset(dx, dy);
    }
}
