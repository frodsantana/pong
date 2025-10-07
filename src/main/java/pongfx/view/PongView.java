package pongfx.view;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import pongfx.model.Bola;
import pongfx.model.GameState;
import pongfx.model.Inimigo;
import pongfx.model.Jogador;

public class PongView extends Pane {
    private Rectangle jogadorRect;
    private Rectangle inimigoRect;
    private Rectangle bolaRect;
    private Text scoreText;

    public PongView() {
        this.setPrefSize(GameState.LARGURA, GameState.ALTURA);
        this.setStyle("-fx-background-color: black;");

        // Elementos da UI
        jogadorRect = new Rectangle(0, 0, Jogador.LARGURA_JOGADOR, Jogador.ALTURA_JOGADOR);
        jogadorRect.setFill(Color.WHITE);

        inimigoRect = new Rectangle(0, 0, Inimigo.LARGURA_INIMIGO, Inimigo.ALTURA_INIMIGO);
        inimigoRect.setFill(Color.WHITE);

        bolaRect = new Rectangle(0, 0, Bola.LARGURA_BOLA, Bola.ALTURA_BOLA);
        bolaRect.setFill(Color.WHITE);

        scoreText = new Text("Pontuação: 0");
        scoreText.setFont(Font.font("Arial", 18));
        scoreText.setFill(Color.WHITE);

        // Posicionamento do score no topo e centralizado
        scoreText.setX(GameState.LARGURA / 2 - scoreText.getLayoutBounds().getWidth() / 2);
        scoreText.setY(25); // Um pequeno deslocamento do topo

        this.getChildren().addAll(jogadorRect, inimigoRect, bolaRect, scoreText);
    }

    public void atualizarView(GameState gameState) {
        // Atualiza a posição dos elementos gráficos com base no Model
        jogadorRect.setX(gameState.getJogador().getX());
        jogadorRect.setY(gameState.getJogador().getY());

        inimigoRect.setX(gameState.getInimigo().getX());
        inimigoRect.setY(gameState.getInimigo().getY());

        bolaRect.setX(gameState.getBola().getX());
        bolaRect.setY(gameState.getBola().getY());

        // Atualiza o placar
        scoreText.setText("Pontuação: " + gameState.getPontos());

        // Centraliza o texto do score novamente (caso o tamanho mude)
        scoreText.setX(GameState.LARGURA / 2 - scoreText.getLayoutBounds().getWidth() / 2);
    }
}