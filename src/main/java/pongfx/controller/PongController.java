package pongfx.controller;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import pongfx.model.GameState;
import pongfx.view.PongView;

public class PongController {
    private GameState gameState;
    private PongView view;
    private AnimationTimer gameLoop;

    public PongController(GameState gameState, PongView view, Scene scene) {
        this.gameState = gameState;
        this.view = view;

        setupInput(scene);
        setupGameLoop();
    }

    // Configura a leitura de teclas
    private void setupInput(Scene scene) {
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.W) {
                gameState.getJogador().setUp(true);
            } else if (event.getCode() == KeyCode.S) {
                gameState.getJogador().setDown(true);
            }
        });

        scene.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.W) {
                gameState.getJogador().setUp(false);
            } else if (event.getCode() == KeyCode.S) {
                gameState.getJogador().setDown(false);
            }
        });
    }

    // O Game Loop (substitui o seu 'run()' e 'Thread.sleep(1000/30)')
    private void setupGameLoop() {
        gameLoop = new AnimationTimer() {
            private long lastUpdate = 0;
            private final double FPS = 30.0;
            private final double NANO_PER_FRAME = 1_000_000_000.0 / FPS;

            @Override
            public void handle(long now) {
                // Controla a taxa de atualização (FPS) - Similar ao seu Thread.sleep(1000/30)
                if (now - lastUpdate >= NANO_PER_FRAME) {
                    gameState.atualizar(); // 1. Atualiza o Model
                    view.atualizarView(gameState); // 2. Atualiza a View
                    lastUpdate = now;
                }
            }
        };
    }

    public void startGame() {
        gameLoop.start();
    }

    public void stopGame() {
        gameLoop.stop();
    }
}