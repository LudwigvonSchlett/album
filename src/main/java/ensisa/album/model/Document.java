package ensisa.album.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Document {

    private final ObservableList<ImageModel> images;

    public Document() {
        this.images = FXCollections.observableArrayList();
    }

    public ObservableList<ImageModel> getImages() {
        return images;
    }

}
