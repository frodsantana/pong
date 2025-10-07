package pongfx.model;

public class Jogador {
    public static final int LARGURA_JOGADOR = 4;
    public static final int ALTURA_JOGADOR = 100;
    private static final int VELOCIDADE = 10;

    private double x, y;
    private boolean up, down;

    public Jogador(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void atualizar() {
        if (up) {
            y -= VELOCIDADE;
        } else if (down) {
            y += VELOCIDADE;
        }

        // Trava nas bordas
        if (y < 0) {
            y = 0;
        } else if (y + ALTURA_JOGADOR > GameState.ALTURA) {
            y = GameState.ALTURA - ALTURA_JOGADOR;
        }
    }

    // Getters e Setters
    public double getX() { return x; }
    public double getY() { return y; }
    public void setUp(boolean up) { this.up = up; }
    public void setDown(boolean down) { this.down = down; }
}