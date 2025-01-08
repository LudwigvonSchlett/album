module ensisa.album {
    requires javafx.controls;
    requires javafx.fxml;


    opens ensisa.album to javafx.fxml;
    exports ensisa.album;
    exports ensisa.album.model;
    exports ensisa.album.command;
    exports ensisa.album.tools;
    opens ensisa.album.command to javafx.fxml;
}