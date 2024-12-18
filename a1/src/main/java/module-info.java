module com.example.a1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;


    opens com.example.a1 to javafx.fxml;
    exports com.example.a1;
    exports com.example.a1.model;
    opens com.example.a1.model to javafx.fxml;
    exports com.example.a1.view;
    opens com.example.a1.view to javafx.fxml;
}