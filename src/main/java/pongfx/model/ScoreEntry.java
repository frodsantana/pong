package pongfx.model;

/**
 * Representa uma entrada no placar (Scoreboard),
 * contendo o nome do jogador (PlayerName) e a pontuação (Score).
 */
public class ScoreEntry {
    private final String name;
    private final int score;

    public ScoreEntry(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return name + " - " + score;
    }
}
