package pongfx.model;

import java.util.Random;
// Não precisamos mais do Bounds, pois usaremos apenas Rectangle2D
// import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;

public class Bola {
    public static final int LARGURA_BOLA = 5;
    public static final int ALTURA_BOLA = 5;

    private double x, y;
    private double direcaoX, direcaoY;
    private double speedBola = 12.5;

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
            // speedBola += 0.5; // Aumentar speed (opcional)
        }

        // --- Lógica de Mapeamento e Colisão (usando apenas Rectangle2D) ---

        // 1. Cria o retângulo da bola na próxima posição
        Rectangle2D bolaBounds = new Rectangle2D(
                x + direcaoX * speedBola,
                y + direcaoY * speedBola,
                LARGURA_BOLA,
                ALTURA_BOLA
        );

        // 2. Cria os retângulos dos jogadores (que são estáticos)
        Rectangle2D jogadorBounds = new Rectangle2D(
                jogador.getX(),
                jogador.getY(),
                Jogador.LARGURA_JOGADOR,
                Jogador.ALTURA_JOGADOR
        );

        Rectangle2D inimigoBounds = new Rectangle2D(
                inimigo.getX(),
                inimigo.getY(),
                Inimigo.LARGURA_INIMIGO,
                Inimigo.ALTURA_INIMIGO
        );

        // Colisão com o Jogador
        // Agora, bolaBounds.intersects(jogadorBounds) funciona perfeitamente.
        if (bolaBounds.intersects(jogadorBounds)) {
            // Só inverte se estiver vindo para a esquerda, para evitar múltiplas colisões.
            if (direcaoX < 0) {
                direcaoX *= -1; // Inverte o X

                // LÓGICA DE PONTUAÇÃO DO JOGADOR: Adiciona o score atual e duplica o multiplicador
                gameState.adicionarPontos(gameState.getScoreMultiplier());
                gameState.duplicarScoreMultiplier();

                // Força a ir para a direita (longe do jogador)
                if(direcaoX < 0) direcaoX *= -1;
            }

            // Colisão com o Inimigo
        } else if (bolaBounds.intersects(inimigoBounds)) {
            // Só inverte se estiver vindo para a direita, para evitar múltiplas colisões.
            if (direcaoX > 0) {
                direcaoX *= -1; // Inverte o X

                // LÓGICA DE PONTUAÇÃO DO INIMIGO: O multiplicador não é resetado aqui.

                // Força a ir para a esquerda (longe do inimigo)
                if(direcaoX > 0) direcaoX *= -1;
            }
        }

        // Move a bola
        x += direcaoX * speedBola;
        y += direcaoY * speedBola;


        // Ponto
        if (x >= GameState.LARGURA) {
            gameState.resetJogo(true); // Ponto para o jogador (continua o jogo)
            return;
        } else if (x <= 0) {
            gameState.resetJogo(false); // Ponto para o inimigo (AGORA MUDA PARA GAME OVER)
            return;
        }

        // O jogo continua, não há game over aqui.
    }

    // Getters e Setters
    public double getX() { return x; }
    public double getY() { return y; }
    public double getDirecaoX() { return direcaoX; }
}
