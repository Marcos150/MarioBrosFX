module com.example.mariobrosfx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.mariobrosfx to javafx.fxml;
    exports com.example.mariobrosfx;
}