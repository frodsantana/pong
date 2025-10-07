package pongfx.model;

public class GameState {
    public static final int LARGURA = 450;
    public static final int ALTURA = 400;

    private int pontos = 0;
    private Jogador jogador;
    private Inimigo inimigo;
    private Bola bola;

    public GameState() {
        // Inicialização similar ao seu construtor Jogo()
        this.jogador = new Jogador(15, 150);
        this.inimigo = new Inimigo(LARGURA - 15 - Inimigo.LARGURA_INIMIGO, 170); // Ajuste a posição do inimigo
        this.bola = new Bola(LARGURA / 2, ALTURA / 2);
    }

    public void atualizar() {
        // Lógica de atualização do jogo
        jogador.atualizar();
        inimigo.atualizar(bola.getY()); // Inimigo precisa da posição da bola
        bola.atualizar(jogador, inimigo, this);

        // A velocidade e o controle do inimigo podem ser ajustados aqui
        // ou dentro de Inimigo.atualizar() como no seu código original.
    }

    // Getters e Setters
    public int getPontos() { return pontos; }
    public void setPontos(int pontos) { this.pontos = pontos; }
    public Jogador getJogador() { return jogador; }
    public Inimigo getInimigo() { return inimigo; }
    public Bola getBola() { return bola; }

    // Método para resetar o jogo após um ponto
    public void resetJogo(boolean pontoParaJogador) {
        if (pontoParaJogador) {
            pontos++;
        } else {
            pontos = 0; // Resetar a pontuação no caso de ponto para o inimigo
        }

        // Recria ou reposiciona a bola
        bola = new Bola(LARGURA / 2, ALTURA / 2);
        // Os pontos serão atualizados na View pelo Controller
    }
}