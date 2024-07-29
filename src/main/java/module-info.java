module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.demo to javafx.fxml;
    exports com.example.demo;
    exports com.example.demo.substitution;
    opens com.example.demo.substitution to javafx.fxml;
    exports com.example.demo.shift;
    opens com.example.demo.shift to javafx.fxml;
    exports com.example.demo.base;
    opens com.example.demo.base to javafx.fxml;
}