package ensisa.album.model;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.HashMap;
import java.util.Map;

public class ImageEditor {

    private final Pane editorPane;
    private final Map<ImageModel, ImageView> images;

    public ImageEditor(Pane testPane) {
        this.editorPane = testPane;
        images = new HashMap<>();
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

}
