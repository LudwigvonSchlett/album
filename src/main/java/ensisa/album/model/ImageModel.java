package ensisa.album.model;



import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
//import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;


public class ImageModel {

    private final DoubleProperty X;
    private final DoubleProperty Y;
    private final DoubleProperty height;
    private final DoubleProperty width;
    //private final ObjectProperty<Rectangle2D> viewport;

    private StringProperty imagePath = new SimpleStringProperty(this, "imagePath", "");

    private ObjectProperty<Image> image = new SimpleObjectProperty<>(this, "image");

    public ImageModel() {
        this.X = new SimpleDoubleProperty(0);
        this.Y = new SimpleDoubleProperty(0);
        this.height = new SimpleDoubleProperty(0);
        this.width = new SimpleDoubleProperty(0);
        //this.viewport = new SimpleObjectProperty<Rectangle2D>(new Rectangle2D(0, 0, 100, 100));

    }

    public ImageModel(String imageurl) {
        this.X = new SimpleDoubleProperty(0);
        this.Y = new SimpleDoubleProperty(0);
        //this.viewport = new SimpleObjectProperty<>(new Rectangle2D(0, 0, 100, 100));
        imagePath = new SimpleStringProperty(imageurl);

        /*image.bind(imagePath.map(path -> {
            System.out.println(getImagePath());
            System.out.println(getClass().getResource(getImagePath()).toExternalForm());
            var url = getClass().getResource(getImagePath()).toExternalForm();
            return new Image(url.toString());
        } ));*/

        this.image.bind(Bindings.createObjectBinding(() -> {
            if (getImagePath() == null || getImagePath().isEmpty()) {
                return null;
            }
            try {
                String path = getImagePath();
                if (path.startsWith("file:")) {
                    return new Image(path);
                } else {
                    var resource = getClass().getResource(path);
                    if (resource == null) {
                        System.err.println("Resource not found: " + path);
                        return null;
                    }
                    return new Image(resource.toExternalForm());
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }, this.imagePath));

        this.height = new SimpleDoubleProperty(image.get().getHeight());
        this.width = new SimpleDoubleProperty(image.get().getWidth());


    }

    public double getX() {
        return X.get();
    }

    public DoubleProperty xProperty() {
        return X;
    }

    public void setX(double X) {
        this.X.set(X);
    }

    public double getY() {
        return Y.get();
    }

    public DoubleProperty yProperty() {
        return Y;
    }

    public void setY(double Y) {
        this.Y.set(Y);
    }

    public double getHeight() {
        return height.get();
    }

    public DoubleProperty heightProperty() {
        return height;
    }

    public void setHeight(double height) {
        this.height.set(height);
    }

    public double getWidth() {
        return width.get();
    }

    public DoubleProperty widthProperty() {
        return width;
    }

    public void setWidth(double width) {
        this.width.set(width);
    }

    /*
    public Rectangle2D getViewport() {
        return viewport.get();
    }

    public ObjectProperty<Rectangle2D> viewportProperty() {
        return viewport;
    }

    public void setViewport(Rectangle2D viewport) {
        this.viewport.set(viewport);
    }
    */

    public String getImagePath() {
        return imagePath.get();
    }

    public StringProperty imagePathProperty() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath.set(imagePath);
    }

    public Image getImage() {
        return image.getValue();
    }

    public ObservableValue<Image> imageProperty() {
        return image;
    }

    public void setImage(Image image) {
        this.image.setValue(image);
    }

    public void offset(double dx, double dy) {
        setX(getX() + dx);
        setY(getY() + dy);
    }
}
