module com.example.financial {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;


    opens com.example.financial to javafx.fxml;
    exports com.example.financial;
}