module com.example.hbv4d {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.hbv4d to javafx.fxml;
    exports com.example.hbv4d;
}
