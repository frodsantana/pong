package pongfx.controller;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import pongfx.model.GameState;
import pongfx.model.GameState.ScreenState;
import pongfx.model.ScoreEntry;
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
        setupMenuActions(); // Configura a ação dos botões do menu/telas

        updateViewBasedOnState();
    }

    // Configura a leitura de teclas APENAS durante o jogo
    private void setupInput(Scene scene) {
        scene.setOnKeyPressed(event -> {
            if (gameState.getEstadoAtual() == ScreenState.JOGANDO) {
                if (event.getCode() == KeyCode.W) {
                    gameState.getJogador().setUp(true);
                } else if (event.getCode() == KeyCode.S) {
                    gameState.getJogador().setDown(true);
                }
            }
        });

        scene.setOnKeyReleased(event -> {
            if (gameState.getEstadoAtual() == ScreenState.JOGANDO) {
                if (event.getCode() == KeyCode.W) {
                    gameState.getJogador().setUp(false);
                } else if (event.getCode() == KeyCode.S) {
                    gameState.getJogador().setDown(false);
                }
            }
        });
    }

    // Configura as ações dos botões do Menu, Scoreboard e Game Over/EnterName
    private void setupMenuActions() {
        // Ações do Menu Principal
        view.getStartButton().setOnAction(event -> iniciarJogo());
        view.getScoreboardButton().setOnAction(event -> mostrarScoreboard());
        view.getQuitButton().setOnAction(event -> sair());

        // Ações do Scoreboard
        view.getBackToMenuButton().setOnAction(event -> mostrarMenu());

        // Ações do Game Over
        // Agora leva para a tela de registro de nome
        view.getRegisterScoreButton().setOnAction(event -> mostrarEnterName());

        // NOVO: Ações da Tela de Registro de Nome
        view.getSaveScoreButton().setOnAction(event -> salvarScore());
    }

    private void iniciarJogo() {
        gameState.iniciarNovoJogo();
        updateViewBasedOnState();
        gameLoop.start();
    }

    private void mostrarMenu() {
        gameLoop.stop();
        gameState.setEstadoAtual(ScreenState.MENU);
        updateViewBasedOnState();
    }

    private void mostrarScoreboard() {
        gameLoop.stop();
        gameState.setEstadoAtual(ScreenState.SCOREBOARD);
        updateViewBasedOnState(); // A view agora formata o placar aqui
    }

    private void mostrarEnterName() {
        gameLoop.stop();
        gameState.setEstadoAtual(ScreenState.ENTER_NAME);
        updateViewBasedOnState();
    }

    /**
     * Lida com o fim de jogo, levando o jogador para a tela de registro.
     */
    private void handleGameOver() {
        gameLoop.stop();
        // O GameState já está em GAME_OVER, mas como só serve como um aviso,
        // podemos levá-lo diretamente para a tela de registro.
        // Se quisermos uma tela de aviso antes, o fluxo é: GAME_OVER -> ENTER_NAME.
        // Por enquanto, o GameState muda para ENTER_NAME (ver Bola.java).
        mostrarEnterName();
    }

    /**
     * Salva o nome e a pontuação no Scoreboard e retorna ao Menu.
     */
    private void salvarScore() {
        String name = view.getNameInputField().getText().trim();
        int score = gameState.getScoreFinal();

        // Se o nome estiver vazio, usa um placeholder
        if (name.isEmpty()) {
            name = "XXX";
        }

        // Cria a entrada e adiciona ao Scoreboard
        ScoreEntry newEntry = new ScoreEntry(name, score);
        gameState.adicionarScore(newEntry);

        // Volta ao Menu Principal
        mostrarMenu();
    }

    private void sair() {
        Platform.exit();
        System.exit(0);
    }

    /**
     * Atualiza a View com base no estado atual do GameState.
     */
    private void updateViewBasedOnState() {
        switch (gameState.getEstadoAtual()) {
            case MENU:
                view.showMenu();
                break;
            case JOGANDO:
                view.showGame();
                break;
            case SCOREBOARD:
                view.showScoreboard(gameState); // Passa o gameState para carregar as pontuações
                break;
            case GAME_OVER: // Tela de Aviso (agora só um passo antes de ENTER_NAME, se necessário)
                view.showGameOver();
                break;
            case ENTER_NAME: // NOVO: Tela de Registro de Nome
                view.showEnterName(gameState);
                break;
        }
    }

    // O Game Loop
    private void setupGameLoop() {
        gameLoop = new AnimationTimer() {
            private long lastUpdate = 0;
            private final double FPS = 30.0;
            private final double NANO_PER_FRAME = 1_000_000_000.0 / FPS;

            @Override
            public void handle(long now) {
                if (now - lastUpdate >= NANO_PER_FRAME) {
                    gameState.atualizar(); // 1. Atualiza o Model

                    // Checa se o GameState mudou para GAME_OVER ou ENTER_NAME
                    if (gameState.getEstadoAtual() == ScreenState.ENTER_NAME) {
                        handleGameOver();
                        return;
                    }

                    view.atualizarView(gameState); // 2. Atualiza a View
                    lastUpdate = now;
                }
            }
        };
    }
}
