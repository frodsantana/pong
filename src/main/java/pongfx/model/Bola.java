package pongfx.model;

import java.util.Random;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;

public class Bola {
    public static final int LARGURA_BOLA = 7;
    public static final int ALTURA_BOLA = 7;

    private double x, y;
    private double direcaoX, direcaoY;
    private double speedBola = 5;

    public Bola(int x, int y) {
        this.x = x;
        this.y = y;

        // Lógica de ângulo inicial
        int angulo = new Random().nextInt(70);
        direcaoX = Math.cos(Math.toRadians(angulo));
        direcaoY = Math.sin(Math.toRadians(angulo));

        // Assegura que a bola comece a se mover para a esquerda ou direita
        if (new Random().nextBoolean()) direcaoX *= -1;
    }

    public void atualizar(Jogador jogador, Inimigo inimigo, GameState gameState) {
        // Colisão com as paredes superior e inferior
        if (y + direcaoY * speedBola < 0 || y + direcaoY * speedBola + ALTURA_BOLA >= GameState.ALTURA) {
            direcaoY *= -1;
            speedBola += 0.5; // Aumentar speed
        }

        // Mapeamento e Colisão (usando Rectangle2D para colisão simples)
        Rectangle2D bolaBounds = new Rectangle2D(x + direcaoX * speedBola, y + direcaoY * speedBola, LARGURA_BOLA, ALTURA_BOLA);

        // Bounds do Jogador
        Rectangle2D jogadorBounds = new Rectangle2D(
                jogador.getX(), jogador.getY(), Jogador.LARGURA_JOGADOR, Jogador.ALTURA_JOGADOR
        );
        // Bounds do Inimigo
        Rectangle2D inimigoBounds = new Rectangle2D(
                inimigo.getX(), inimigo.getY(), Inimigo.LARGURA_INIMIGO, Inimigo.ALTURA_INIMIGO
        );

        // Colisão com o Jogador
        if (bolaBounds.intersects(jogadorBounds)) {
            direcaoX *= -1; // Inverte o X
            speedBola += 0.5; // Aumenta um pouco a velocidade
            if(direcaoX < 0) direcaoX *= -1; // Força a ir para a direita (longe do jogador)

            // Colisão com o Inimigo
        } else if (bolaBounds.intersects(inimigoBounds)) {
            direcaoX *= -1; // Inverte o X
            speedBola += 0.5; // Aumenta um pouco a velocidade
            if(direcaoX > 0) direcaoX *= -1; // Força a ir para a esquerda (longe do inimigo)
        }

        // Ponto
        if (x >= GameState.LARGURA) {
            gameState.resetJogo(true); // Ponto para o jogador
            return;
        } else if (x <= 0) {
            gameState.resetJogo(false); // Ponto para o inimigo (reset de pontuação)
            return;
        }

        // Movimento
        x += direcaoX * speedBola;
        y += direcaoY * speedBola;
    }

    // Getters (para a View)
    public double getX() { return x; }
    public double getY() { return y; }
}