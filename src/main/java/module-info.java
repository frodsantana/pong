module pongfx {
    requires javafx.controls;
    requires javafx.fxml;


    opens pongfx to javafx.fxml;
    exports pongfx;
}