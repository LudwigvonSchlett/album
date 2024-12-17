package ensisa.album.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Document {

    private final ObservableList<Image> images;

    public Document() {
        this.images = FXCollections.observableArrayList();
    }

    public ObservableList<Image> getImages() {
        return images;
    }

}
