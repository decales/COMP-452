module com.example.a2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens com.example.a2 to javafx.fxml;
    exports com.example.a2;
    // exports com.example.a2.model;
    // opens com.example.a2.model to javafx.fxml;
    // exports com.example.a2.view;
    // opens com.example.a2.view to javafx.fxml;
}
