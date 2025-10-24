package pongfx.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GameState {
    public static final int LARGURA = 450;
    public static final int ALTURA = 400;

    // Enum para gerenciar as telas
    public enum ScreenState {
        MENU,
        JOGANDO,
        SCOREBOARD,
        GAME_OVER,
        ENTER_NAME // NOVO ESTADO: Para registrar o nome após o Game Over
    }

    private ScreenState estadoAtual = ScreenState.MENU; // Começa no Menu
    private int pontos = 0;
    private int scoreMultiplier = 1;

    // Armazena a pontuação final antes de ir para a tela de registro
    private int scoreFinal = 0;

    // NOVO: Placar
    private List<ScoreEntry> scoreboard;

    private Jogador jogador;
    private Inimigo inimigo;
    private Bola bola;

    public GameState() {
        this.jogador = new Jogador(15, 150);
        this.inimigo = new Inimigo(LARGURA - 15 - Inimigo.LARGURA_INIMIGO, 170);
        this.bola = new Bola(LARGURA / 2, ALTURA / 2);

        // Inicializa o placar com alguns valores fictícios
        this.scoreboard = new ArrayList<>();
        // AdicionarScore é o método que usaremos internamente
        adicionarScore(new ScoreEntry("GEM", 500));
        adicionarScore(new ScoreEntry("BOT", 300));
        adicionarScore(new ScoreEntry("P1", 100));
    }

    public void atualizar() {
        if (estadoAtual != ScreenState.JOGANDO) return;

        jogador.atualizar();
        inimigo.atualizar(bola.getY());
        bola.atualizar(jogador, inimigo, this);
    }

    // --- Métodos de Scoreboard ---

    /**
     * Adiciona uma nova pontuação ao placar e o mantém ordenado.
     */
    public void adicionarScore(ScoreEntry entry) {
        this.scoreboard.add(entry);
        // Ordena o placar em ordem decrescente de pontuação
        Collections.sort(this.scoreboard, Comparator.comparingInt(ScoreEntry::getScore).reversed());
        // Limita o placar aos top 10 (opcional, mas bom para performance)
        if (this.scoreboard.size() > 10) {
            this.scoreboard = this.scoreboard.subList(0, 10);
        }
    }

    // --- Métodos de Jogo e Pontuação ---

    public void adicionarPontos(int pontosGanhos) {
        this.pontos += pontosGanhos;
    }

    public void duplicarScoreMultiplier() {
        this.scoreMultiplier *= 2;
    }

    public void resetScoreMultiplier() {
        this.scoreMultiplier = 1;
    }

    // Método para resetar o jogo após um ponto ou no início de um novo jogo
    public void resetJogo(boolean pontoParaJogador) {
        // Reinicializa a posição dos objetos
        jogador = new Jogador(15, 150);
        inimigo = new Inimigo(LARGURA - 15 - Inimigo.LARGURA_INIMIGO, 170);
        bola = new Bola(LARGURA / 2, ALTURA / 2);

        if (pontoParaJogador) {
            estadoAtual = ScreenState.JOGANDO;
        } else {
            // Ponto para o inimigo: FIM DE JOGO
            scoreFinal = pontos; // Salva o score
            pontos = 0;
            scoreMultiplier = 1;
            estadoAtual = ScreenState.ENTER_NAME; // MUDA PARA REGISTRAR NOME
        }
    }

    /**
     * Prepara e inicia um novo jogo do zero.
     */
    public void iniciarNovoJogo() {
        pontos = 0;
        scoreMultiplier = 1;
        scoreFinal = 0;
        resetJogo(true); // Reinicia a posição dos objetos e define estado como JOGANDO
    }

    // --- Getters e Setters ---
    public Jogador getJogador() { return jogador; }
    public Inimigo getInimigo() { return inimigo; }
    public Bola getBola() { return bola; }

    public int getPontos() { return pontos; }
    public int getScoreMultiplier() { return scoreMultiplier; }
    public int getScoreFinal() { return scoreFinal; }

    // NOVO: Getter para o placar
    public List<ScoreEntry> getScoreboard() { return scoreboard; }

    public ScreenState getEstadoAtual() {
        return estadoAtual;
    }

    public void setEstadoAtual(ScreenState estadoAtual) {
        this.estadoAtual = estadoAtual;
    }
}
