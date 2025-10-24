package pongfx.view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import pongfx.model.Bola;
import pongfx.model.GameState;
import pongfx.model.Inimigo;
import pongfx.model.Jogador;
import pongfx.model.ScoreEntry;

public class PongView extends Pane {
    // Componentes do Jogo
    private Rectangle jogadorRect;
    private Rectangle inimigoRect;
    private Rectangle bolaRect;
    private Text scoreText;
    private Text multiplierText;

    // Componentes do Menu
    private VBox menuBox;
    private Button startButton;
    private Button scoreboardButton;
    private Button quitButton;
    private Text titleText;

    // Componente do Scoreboard
    private VBox scoreboardBox;
    private Button backToMenuButton;
    private Text scoreboardTitle;
    private Text scoreboardContent; // Para exibir a lista de pontuações

    // Componentes do Game Over (Tela de Aviso)
    private VBox gameOverBox;
    private Text gameOverTitle;
    private Button backToMenuFromGameOverButton;
    // O botão 'Jogar Novamente' foi movido para a tela 'Enter Name' ou será re-utilizado.
    // Para simplificar, vamos mover a decisão de jogar novamente para o Menu,
    // e o Game Over agora SÓ leva para Enter Name.

    // NOVO: Componentes da Tela de Registro de Nome (Enter Name)
    private VBox enterNameBox;
    private Text enterNameTitle;
    private Text enterNameScore;
    private TextField nameInputField;
    private Button saveScoreButton;

    public PongView() {
        this.setPrefSize(GameState.LARGURA, GameState.ALTURA);
        this.setStyle("-fx-background-color: black;");

        setupGameElements();
        setupMenuElements();
        setupScoreboardElements();
        setupGameOverElements();
        setupEnterNameElements(); // NOVO: Configuração da tela de registro

        showMenu();
    }

    // --- Configuração dos Elementos ---

    private void setupGameElements() {
        // ... (Configuração dos elementos de jogo)
        // Jogador
        jogadorRect = new Rectangle(0, 0, Jogador.LARGURA_JOGADOR, Jogador.ALTURA_JOGADOR);
        jogadorRect.setFill(Color.WHITE);
        this.getChildren().add(jogadorRect);

        // Inimigo
        inimigoRect = new Rectangle(0, 0, Inimigo.LARGURA_INIMIGO, Inimigo.ALTURA_INIMIGO);
        inimigoRect.setFill(Color.WHITE);
        this.getChildren().add(inimigoRect);

        // Bola
        bolaRect = new Rectangle(0, 0, Bola.LARGURA_BOLA, Bola.ALTURA_BOLA);
        bolaRect.setFill(Color.WHITE);
        this.getChildren().add(bolaRect);

        // Score
        scoreText = new Text("Pontuação: 0");
        scoreText.setFont(Font.font("Arial", 18));
        scoreText.setFill(Color.WHITE);
        scoreText.setX(GameState.LARGURA / 2 - 50);
        scoreText.setY(20);
        this.getChildren().add(scoreText);

        // Multiplicador
        multiplierText = new Text("Multiplicador: x1");
        multiplierText.setFont(Font.font("Arial", 14));
        multiplierText.setFill(Color.YELLOW);
        multiplierText.setX(GameState.LARGURA / 2 - 50);
        multiplierText.setY(40);
        this.getChildren().add(multiplierText);

        // Linha central
        Rectangle centerLine = new Rectangle(GameState.LARGURA / 2 - 1, 0, 2, GameState.ALTURA);
        centerLine.setFill(Color.DARKGRAY);
        centerLine.setOpacity(0.5);
        this.getChildren().add(centerLine);

        // Coloca a linha e os textos de score/multiplicador na frente
        centerLine.toBack();
        scoreText.toFront();
        multiplierText.toFront();
    }

    private void setupMenuElements() {
        titleText = new Text("PONG CLÁSSICO");
        titleText.setFont(Font.font("Arial", 48));
        titleText.setFill(Color.WHITE);

        startButton = new Button("Iniciar Jogo");
        scoreboardButton = new Button("Scoreboard");
        quitButton = new Button("Sair");

        // Estilização simples para os botões do menu
        String buttonStyle = "-fx-background-color: #333333; -fx-text-fill: white; -fx-font-size: 18px; -fx-padding: 10 20;";
        startButton.setStyle(buttonStyle);
        scoreboardButton.setStyle(buttonStyle);
        quitButton.setStyle(buttonStyle);

        menuBox = new VBox(20, titleText, startButton, scoreboardButton, quitButton);
        menuBox.setAlignment(Pos.CENTER);
        menuBox.setPrefSize(GameState.LARGURA, GameState.ALTURA);

        this.getChildren().add(menuBox);
    }

    private void setupScoreboardElements() {
        scoreboardTitle = new Text("SCOREBOARD");
        scoreboardTitle.setFont(Font.font("Arial", 36));
        scoreboardTitle.setFill(Color.WHITE);

        scoreboardContent = new Text(""); // Será preenchido dinamicamente
        scoreboardContent.setFont(Font.font("Monospaced", 18));
        scoreboardContent.setFill(Color.LIGHTGRAY);

        backToMenuButton = new Button("Voltar ao Menu");
        backToMenuButton.setStyle("-fx-background-color: #555555; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 8 16;");

        scoreboardBox = new VBox(20, scoreboardTitle, scoreboardContent, backToMenuButton);
        scoreboardBox.setAlignment(Pos.CENTER);
        scoreboardBox.setPrefSize(GameState.LARGURA, GameState.ALTURA);

        this.getChildren().add(scoreboardBox);
    }

    private void setupGameOverElements() {
        gameOverTitle = new Text("GAME OVER!");
        gameOverTitle.setFont(Font.font("Arial", 48));
        gameOverTitle.setFill(Color.RED);

        backToMenuFromGameOverButton = new Button("Registrar Pontuação"); // Agora leva para a tela de registro
        backToMenuFromGameOverButton.setStyle("-fx-background-color: #D32F2F; -fx-text-fill: white; -fx-font-size: 18px; -fx-padding: 10 20;");

        gameOverBox = new VBox(20, gameOverTitle, backToMenuFromGameOverButton);
        gameOverBox.setAlignment(Pos.CENTER);
        gameOverBox.setPrefSize(GameState.LARGURA, GameState.ALTURA);

        this.getChildren().add(gameOverBox);
    }

    // NOVO: Configuração da Tela de Registro de Nome
    private void setupEnterNameElements() {
        enterNameTitle = new Text("REGISTRAR PONTUAÇÃO");
        enterNameTitle.setFont(Font.font("Arial", 32));
        enterNameTitle.setFill(Color.WHITE);

        enterNameScore = new Text("Score: 0");
        enterNameScore.setFont(Font.font("Arial", 24));
        enterNameScore.setFill(Color.YELLOW);

        nameInputField = new TextField();
        nameInputField.setPromptText("Seu Nome (3 Letras)");
        nameInputField.setMaxWidth(150);
        nameInputField.setFont(Font.font("Monospaced", 18));
        nameInputField.setAlignment(Pos.CENTER);

        // Limita o input a 3 caracteres e apenas letras maiúsculas ou números
        nameInputField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 3) {
                nameInputField.setText(oldValue);
            } else if (!newValue.matches("[A-Z0-9]*")) {
                nameInputField.setText(newValue.replaceAll("[^A-Z0-9]", "").toUpperCase());
            } else {
                nameInputField.setText(newValue.toUpperCase());
            }
        });

        saveScoreButton = new Button("Salvar e Voltar ao Menu");
        saveScoreButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 18px; -fx-padding: 10 20;");

        enterNameBox = new VBox(20, enterNameTitle, enterNameScore, nameInputField, saveScoreButton);
        enterNameBox.setAlignment(Pos.CENTER);
        enterNameBox.setPrefSize(GameState.LARGURA, GameState.ALTURA);

        this.getChildren().add(enterNameBox);
    }


    // --- Métodos de Visibilidade (SHOW/HIDE) e Transição ---

    // Elementos do Jogo
    private void showGameElements() {
        jogadorRect.setVisible(true);
        inimigoRect.setVisible(true);
        bolaRect.setVisible(true);
        scoreText.setVisible(true);
        multiplierText.setVisible(true);
    }

    private void hideGameElements() {
        jogadorRect.setVisible(false);
        inimigoRect.setVisible(false);
        bolaRect.setVisible(false);
        scoreText.setVisible(false);
        multiplierText.setVisible(false);
    }

    private void hideAllScreenElements() {
        menuBox.setVisible(false);
        scoreboardBox.setVisible(false);
        gameOverBox.setVisible(false);
        enterNameBox.setVisible(false);
        hideGameElements();
    }

    public void showMenu() {
        hideAllScreenElements();
        menuBox.setVisible(true);
    }

    public void showGame() {
        hideAllScreenElements();
        showGameElements();
    }

    public void showScoreboard(GameState gameState) {
        hideAllScreenElements();

        // Formata o conteúdo do Scoreboard
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-10s %5s\n", "NOME", "SCORE"));
        sb.append("--------------------\n");

        for (int i = 0; i < gameState.getScoreboard().size(); i++) {
            ScoreEntry entry = gameState.getScoreboard().get(i);
            sb.append(String.format("%d. %-6s %5d\n", i + 1, entry.getName(), entry.getScore()));
        }

        scoreboardContent.setText(sb.toString());

        scoreboardBox.setVisible(true);
    }

    public void showGameOver() {
        hideAllScreenElements();
        gameOverBox.setVisible(true);
    }

    // NOVO: Exibe a tela de Registro de Nome
    public void showEnterName(GameState gameState) {
        hideAllScreenElements();

        // Atualiza o score e limpa o campo de input
        enterNameScore.setText("Sua Pontuação: " + gameState.getScoreFinal());
        nameInputField.setText("");
        enterNameBox.setVisible(true);
    }


    public void atualizarView(GameState gameState) {
        // Centraliza os textos de score/multiplicador (precisa ser feito a cada frame
        // se o texto mudar, mas é ineficiente. É melhor centralizar a VBox que os contém,
        // mas vamos manter assim por simplicidade no momento.)
        scoreText.setX(GameState.LARGURA / 2 - scoreText.getLayoutBounds().getWidth() / 2);
        multiplierText.setX(GameState.LARGURA / 2 - multiplierText.getLayoutBounds().getWidth() / 2);

        if (gameState.getEstadoAtual() == GameState.ScreenState.JOGANDO) {
            // Atualiza a posição dos elementos gráficos com base no Model
            jogadorRect.setX(gameState.getJogador().getX());
            jogadorRect.setY(gameState.getJogador().getY());

            inimigoRect.setX(gameState.getInimigo().getX());
            inimigoRect.setY(gameState.getInimigo().getY());

            bolaRect.setX(gameState.getBola().getX());
            bolaRect.setY(gameState.getBola().getY());

            // Atualiza o placar e o multiplicador
            scoreText.setText("Pontuação: " + gameState.getPontos());
            multiplierText.setText("Multiplicador: x" + gameState.getScoreMultiplier());
        }
    }

    // --- Getters para o Controller ---
    public Button getStartButton() { return startButton; }
    public Button getScoreboardButton() { return scoreboardButton; }
    public Button getQuitButton() { return quitButton; }
    public Button getBackToMenuButton() { return backToMenuButton; } // Botão do Scoreboard

    // Botão que leva do Game Over para Enter Name
    public Button getRegisterScoreButton() { return backToMenuFromGameOverButton; }

    // NOVO: Getters para a tela de Registro de Nome
    public TextField getNameInputField() { return nameInputField; }
    public Button getSaveScoreButton() { return saveScoreButton; }
}
