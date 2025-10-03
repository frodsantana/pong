module pongfx.pongfx {
    requires javafx.controls;
    requires javafx.fxml;


    opens pongfx.pongfx to javafx.fxml;
    exports pongfx.pongfx;
}