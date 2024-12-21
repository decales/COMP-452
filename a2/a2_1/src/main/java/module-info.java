module com.example.a2_1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens com.example.a2_1 to javafx.fxml;
    exports com.example.a2_1;
    exports com.example.a2_1.model;
    opens com.example.a2_1.model to javafx.fxml;
    exports com.example.a2_1.view;
    opens com.example.a2_1.view to javafx.fxml;
}
