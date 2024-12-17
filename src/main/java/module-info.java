module ensisa.album {
    requires javafx.controls;
    requires javafx.fxml;


    opens ensisa.album to javafx.fxml;
    exports ensisa.album;
}