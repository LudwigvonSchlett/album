package ensisa.album;

import ensisa.album.model.ImageModel;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.HashMap;
import java.util.Map;

public class ImageEditor {

    private final Pane editorPane;
    private final Map<ImageModel, ImageView> images;
    private final Map<ImageModel, Rectangle> topLeftSelectionSquares;
    private final Map<ImageModel, Rectangle> topRightSelectionSquares;
    private final Map<ImageModel, Rectangle> bottomLeftSelectionSquares;
    private final Map<ImageModel, Rectangle> bottomRightSelectionSquares;
    private static final int selectionSquareWidth = 6;


    public ImageEditor(Pane testPane) {
        this.editorPane = testPane;
        images = new HashMap<>();
        topLeftSelectionSquares = new HashMap<>();
        topRightSelectionSquares = new HashMap<>();
        bottomLeftSelectionSquares = new HashMap<>();
        bottomRightSelectionSquares = new HashMap<>();
    }

    public void createImage(ImageModel image) {
        ImageView imageView = new ImageView();
        images.put(image, imageView);
        bind(imageView, image);
        editorPane.getChildren().add(imageView);
    }

    public void removeImage(ImageModel image) {
        ImageView imageView = images.remove(image);
        editorPane.getChildren().remove(imageView);
    }

    private void bind(ImageView imageView, ImageModel image) {
        imageView.xProperty().bind(image.xProperty());
        imageView.yProperty().bind(image.yProperty());
        imageView.imageProperty().bind(image.imageProperty());
        //imageView.viewportProperty().bind(image.viewportProperty());
    }

    private Rectangle createSelectionSquare() {
        var square = new Rectangle();
        square.setWidth(selectionSquareWidth);
        square.setHeight(selectionSquareWidth);
        square.setFill(Color.WHITE);
        square.setStroke(Color.BLACK);
        return square;
    }

    private void bind(Rectangle topLeftSelectionSquare, Rectangle topRightSelectionSquare,
                      Rectangle bottomLeftSelectionSquare, Rectangle bottomRightSelectionSquare, ImageModel image) {

        topLeftSelectionSquare.xProperty().bind(image.xProperty().subtract(selectionSquareWidth/2));
        topLeftSelectionSquare.yProperty().bind(image.yProperty().subtract(selectionSquareWidth/2));

        topRightSelectionSquare.xProperty().bind(image.xProperty().subtract(selectionSquareWidth/2).add(image.getWidth()));
        topRightSelectionSquare.yProperty().bind(image.yProperty().subtract(selectionSquareWidth/2));

        bottomLeftSelectionSquare.xProperty().bind(image.xProperty().subtract(selectionSquareWidth/2));
        bottomLeftSelectionSquare.yProperty().bind(image.yProperty().subtract(selectionSquareWidth/2).add(image.getHeight()));

        bottomRightSelectionSquare.xProperty().bind(image.xProperty().subtract(selectionSquareWidth/2).add(image.getWidth()));
        bottomRightSelectionSquare.yProperty().bind(image.yProperty().subtract(selectionSquareWidth/2).add(image.getHeight()));

    }

    public void selectImage(ImageModel image) {
        var topLeftSelectionSquare = createSelectionSquare();
        var topRightSelectionSquare = createSelectionSquare();
        var bottomLeftSelectionSquare = createSelectionSquare();
        var bottomRightSelectionSquare = createSelectionSquare();

        topLeftSelectionSquares.put(image, createSelectionSquare());
        topRightSelectionSquares.put(image, createSelectionSquare());
        bottomLeftSelectionSquares.put(image, createSelectionSquare());
        bottomRightSelectionSquares.put(image, createSelectionSquare());

        bind(topLeftSelectionSquare, topRightSelectionSquare, bottomLeftSelectionSquare, bottomRightSelectionSquare, image);

        editorPane.getChildren().add(topLeftSelectionSquare);
        editorPane.getChildren().add(topRightSelectionSquare);
        editorPane.getChildren().add(bottomLeftSelectionSquare);
        editorPane.getChildren().add(bottomRightSelectionSquare);
    }

    public void deselectImage(ImageModel image) {
        var selectionSquare = topLeftSelectionSquares.get(image);
        editorPane.getChildren().remove(selectionSquare);
        selectionSquare = topRightSelectionSquares.get(image);
        editorPane.getChildren().remove(selectionSquare);
        selectionSquare = bottomLeftSelectionSquares.get(image);
        editorPane.getChildren().remove(selectionSquare);
        selectionSquare = bottomRightSelectionSquares.get(image);
        editorPane.getChildren().remove(selectionSquare);
    }

    public boolean isPointInImage(double x, double y, ImageModel image) {
        if ((x >= image.getX()) && (x <= (image.getX() + image.getWidth()))) {

            if ((y >= image.getY()) && (y <= (image.getY() + image.getHeight()))) {
                return true;
            }
        }
        return false;
    }

}
