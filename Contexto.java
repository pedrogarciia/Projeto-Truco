public class ContextoJogo {
    private Jogador jogador;
    private Computador computador;
    private Partida partida;

    public ContextoJogo(Jogador jogador, Computador computador, Partida partida) {
        this.jogador = jogador;
        this.computador = computador;
        this.partida = partida;
    }

    public Jogador getJogador() {
        return jogador;
    }

    public Computador getComputador() {
        return computador;
    }

    public Partida getPartida() {
        return partida;
    }

    // ==================================================
    // 🔧 Utilitários rápidos (para facilitar efeitos)
    // ==================================================

    // Mostra o estado atual das mãos (debug ou efeitos)
    public void mostrarEstadoMao() {
        System.out.println("\n🂠 Estado Atual das Mãos:");
        System.out.println("• " + jogador.getNome() + ": " + jogador.getMao());
        System.out.println("• " + computador.getNome() + ": " + computador.getMao());
    }

    // Permite que efeitos forcem uma recarga parcial do baralho
    public void substituirCartaJogador(Carta antiga, Carta nova) {
        jogador.getMao().remove(antiga);
        jogador.getMao().add(nova);
        System.out.println("🪄 Carta " + antiga + " foi substituída por " + nova + "!");
    }

    public void substituirCartaBot(Carta antiga, Carta nova) {
        computador.getMao().remove(antiga);
        computador.getMao().add(nova);
        System.out.println("🤖 Carta do bot " + antiga + " foi substituída por " + nova + "!");
    }
}
