package ensisa.album.tools;

import ensisa.album.AlbumController;
import ensisa.album.command.MoveImageCommand;
import ensisa.album.model.ImageModel;
import javafx.scene.input.MouseEvent;

public class SelectTool implements Tool {

    private  AlbumController controller;
    enum State { initial, selection}
    private State state;

    private ImageModel image;
    private double startX;
    private double startY;
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
            this.startX = event.getX();
            this.lastX = event.getX();
            this.startY = event.getY();
            this.lastY = event.getY();
            this.image = controller.findImageForPoint(event.getX(), event.getY());


            if (this.image != null) {
                controller.deselectAll();
                controller.selectImage(this.image);
            } else {
                controller.deselectAll();
            }

        }
    }

    @Override
    public void mouseReleased(MouseEvent event){
        if (state == State.selection && image != null) {
            double deltaX = event.getX() - startX;
            double deltaY = event.getY() - startY;
            controller.getUndoRedoHistory().addCommand(new MoveImageCommand(image, deltaX, deltaY));
        }
        state = State.initial;
    }

    @Override
    public void mouseDragged(MouseEvent event){
        if (event.isPrimaryButtonDown() && state == State.selection) {
            double deltaX = event.getX() - lastX;
            double deltaY = event.getY() - lastY;
            for (var image : controller.getSelectedImages()) {
                new MoveImageCommand(image, deltaX, deltaY).execute();
            }
            lastX = event.getX();
            lastY = event.getY();
        }
    }

}
