module com.movietheater.manager.movietheatermanager {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.movietheater.manager.movietheatermanager to javafx.fxml;
    exports com.movietheater.manager.movietheatermanager;
}