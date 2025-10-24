package pongfx.model;

public class Inimigo {
    public static final int LARGURA_INIMIGO = 7;
    public static final int ALTURA_INIMIGO = 30;

    private double x, y;

    public Inimigo(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void atualizar(double bolaY) {
        // Lógica de IA do inimigo (ajustada para JavaFX/MVC)
        // Note que o inimigo move-se para o centro da bola com um "damping" (0.1)
        double targetY = bolaY - (ALTURA_INIMIGO / 2.0);
        y += (targetY - y) * 1; // Ajuste a velocidade de 0.1 (lento) a 1.0 (imediato)

        // Trava nas bordas
        if (y < 0) {
            y = 0;
        } else if (y + ALTURA_INIMIGO > GameState.ALTURA) {
            y = GameState.ALTURA - ALTURA_INIMIGO;
        }
    }

    // Getters
    public double getX() { return x; }
    public double getY() { return y; }
}