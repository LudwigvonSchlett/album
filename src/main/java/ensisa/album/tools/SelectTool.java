package ensisa.album.tools;

import ensisa.album.AlbumController;
import ensisa.album.command.MoveImageCommand;
import ensisa.album.model.ImageModel;
import javafx.scene.input.MouseEvent;

import java.util.Objects;

public class SelectTool implements Tool {

    private  AlbumController controller;
    enum State { initial, selection}
    private State state;

    private ImageModel image;
    private double lastX;
    private double lastY;

    public SelectTool(AlbumController controller) {
        this.controller = controller;
        this.state = State.initial;
    }

    @Override
    public void mousePressed(MouseEvent event){
        if (event.isPrimaryButtonDown()) {
            this.state = State.selection;
            this.lastX = event.getX();
            this.lastY = event.getY();
            this.image = controller.findImageForPoint(event.getX(), event.getY());


            if (this.image != null) {
                controller.selectImage(this.image);
            } else {
                controller.deselectAll();
            }

        }
    }

    @Override
    public void mouseReleased(MouseEvent event){
        state = State.initial;
    }

    @Override
    public void mouseDragged(MouseEvent event){
        if (event.isPrimaryButtonDown()) {
            double deltaX = event.getX() - lastX;
            double deltaY = event.getY() - lastY;
            if (Objects.requireNonNull(state) == State.selection) {
                for (var image : controller.getSelectedImages()) {
                    controller.getUndoRedoHistory().execute(new MoveImageCommand(image, deltaX, deltaY));
                }
            }
            lastX = event.getX();
            lastY = event.getY();
        }
    }

}
