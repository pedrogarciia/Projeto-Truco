public class Jogo {

    private int numeroDaPartida = 1; // controla a run (nível do oponente)
    private boolean jogando = true;

    public void iniciar() {
        System.out.println("=== 🃏 Bem-vindo ao TRUCO RUN ===");
        System.out.println("Derrote todos os bots para vencer a run completa!");
        System.out.println("Cada oponente tem uma personalidade e dificuldade diferentes.\n");

        while (jogando) {
            System.out.println("\n=== ⚔️ Partida " + numeroDaPartida + " ===");

            // 1️⃣ Cria o jogador humano (novo a cada run)
            Jogador humano = new Jogador("Você");

            // 2️⃣ Gera o bot da vez via fábrica
            Computador bot = FabricaDeBots.gerarOponente(numeroDaPartida);

            System.out.println("\n🧠 Enfrentando: " + bot.getNome() +
    " [" + bot.getPersonalidade() + " - " + bot.getNivel() + "]");


            // 3️⃣ Inicia a partida
            Partida partida = new Partida(humano, bot);
            boolean venceu = partida.iniciar(); // ✅ corrigido

            // 4️⃣ Gerencia a run
            if (venceu) {
                System.out.println("✅ Você venceu esta partida! Indo para o próximo oponente...");
                numeroDaPartida++;

                // limite de bots (pode ajustar)
                if (numeroDaPartida > 8) {
                    System.out.println("\n🏆 Parabéns! Você venceu todos os oponentes! Run completa!");
                    jogando = false;
                }

            } else {
                System.out.println("❌ Você perdeu! A run será reiniciada do início...");
                numeroDaPartida = 1;
            }

            // pausa antes da próxima partida
            if (jogando) {
                System.out.println("\nPressione ENTER para continuar...");
                try {
                    System.in.read();
                } catch (Exception e) {
                    // ignora erro de entrada
                }
            }
        }

        System.out.println("\n👋 Fim do jogo. Obrigado por jogar!");
    }
}
