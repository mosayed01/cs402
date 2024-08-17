module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;

    exports com.example.demo.ciphers.aes;
    opens com.example.demo.ciphers.aes to javafx.fxml;
    exports com.example.demo.ciphers.otp;
    opens com.example.demo.ciphers.otp to javafx.fxml;
    exports com.example.demo.ciphers.substitution;
    opens com.example.demo.ciphers.substitution to javafx.fxml;
    exports com.example.demo.ciphers.des;
    opens com.example.demo.ciphers.des to javafx.fxml;
    exports com.example.demo.ciphers.shift;
    opens com.example.demo.ciphers.shift to javafx.fxml;
    exports com.example.demo.base;
    opens com.example.demo.base to javafx.fxml;
    exports com.example.demo.ciphers.affine;
    opens com.example.demo.ciphers.affine to javafx.fxml;
}