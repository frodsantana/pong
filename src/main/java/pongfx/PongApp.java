package pongfx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pongfx.controller.PongController;
import pongfx.model.GameState;
import pongfx.view.PongView;

public class PongApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        // 1. Inicializa o Model
        GameState gameState = new GameState();

        // 2. Inicializa a View
        PongView view = new PongView();

        // 3. Configura a Scene
        Scene scene = new Scene(view);

        // 4. Inicializa o Controller (liga Model e View)
        PongController controller = new PongController(gameState, view, scene);

        // Configura o Stage (Janela)
        primaryStage.setTitle("PONG - JavaFX MVC");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

        // 5. Inicia o Game Loop
        controller.startGame();
    }

    public static void main(String[] args) {
        launch(args);
    }
}