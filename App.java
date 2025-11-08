public class App {
    public static void main(String[] args) {

        System.out.println("🎮 Bem-vindo ao TRUCO ROGUE LIKE 🃏");
        System.out.println("-----------------------------------");

        // 🔹 Criação dos jogadores
        Jogador jogador = new Jogador("Você");
        Jogador computador = new Jogador("Bot Caipira");

        // 🔹 Inicializa o sistema de cartas especiais
        GerenciadorCartasEspeciais.inicializarCartas();

        // 🔹 (opcional) Mostrar as cartas disponíveis no jogo
        System.out.println("\n📜 Cartas Especiais disponíveis no jogo:");
        for (CartaEspecial carta : GerenciadorCartasEspeciais.getTodasCartas()) {
            System.out.println("• " + carta.getNome() + " [" + carta.getCategoria() + "] - " + carta.getDescricao());
        }

        System.out.println("\n-----------------------------------");
        System.out.println("👉 A partida vai começar!\n");

        // 🔹 Cria e inicia a partida
        Partida partida = new Partida(jogador, computador);
        partida.iniciar();

        // 🔹 Após terminar a partida, mostra as cartas acumuladas
        System.out.println("\n🏁 Fim da partida!");
        System.out.println("Suas cartas especiais acumuladas:");
        for (CartaEspecial c : jogador.getCartasEspeciais()) {
            System.out.println("• " + c.getNome() + " [" + c.getCategoria() + "]");
        }

        System.out.println("\nObrigado por jogar o TRUCO ROGUE LIKE!");
    }
}
