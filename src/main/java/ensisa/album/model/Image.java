package ensisa.album.model;

import javafx.beans.property.*;
import javafx.geometry.Rectangle2D;

public class Image {

    private final DoubleProperty X;
    private final DoubleProperty Y;
    private final ObjectProperty<Rectangle2D> rectangle;

    private StringProperty imagePath = new SimpleStringProperty(this, "imagePath", "");

    private Property<Image> image = new SimpleObjectProperty<>(this, "image");

    public Image() {
        this.X = new SimpleDoubleProperty(0);
        this.Y = new SimpleDoubleProperty(0);
        this.rectangle = new SimpleObjectProperty<Rectangle2D>(new Rectangle2D(0, 0, 100, 100));

    }

    public Image(String imageurl) {
        this.X = new SimpleDoubleProperty(0);
        this.Y = new SimpleDoubleProperty(0);
        this.rectangle = new SimpleObjectProperty<>(new Rectangle2D(0, 0, 100, 100));
        imagePath = new SimpleStringProperty(imageurl);
    }

    public double getX() {
        return X.get();
    }

    public DoubleProperty XProperty() {
        return X;
    }

    public void setX(double X) {
        this.X.set(X);
    }

    public double getY() {
        return Y.get();
    }

    public DoubleProperty YProperty() {
        return Y;
    }

    public void setY(double Y) {
        this.Y.set(Y);
    }

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

    public Property<Image> imageProperty() {
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
