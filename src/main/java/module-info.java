module com.example.escapequizzz {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.escapequizzz to javafx.fxml;
    exports com.example.escapequizzz;
}