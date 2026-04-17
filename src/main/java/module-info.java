module com.example.escapequizzz {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.net.http;
    requires com.google.gson;

    opens com.example.escapequizzz to javafx.fxml;
    exports com.example.escapequizzz;
}