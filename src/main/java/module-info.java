module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.demo to javafx.fxml;
    exports com.example.demo;
    exports com.example.demo.substitution;
    opens com.example.demo.substitution to javafx.fxml;
    exports com.example.demo.logic;
    opens com.example.demo.logic to javafx.fxml;
}