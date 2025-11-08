import java.util.Scanner;
import java.util.Random;

public class Partida {
    private Jogador jogador;
    private Computador computador;

    private Scanner sc = new Scanner(System.in);
    private Random r = new Random();

    private int pontosJogador = 0;
    private int pontosComputador = 0;
    private int pontosRodada = 1;

    public Partida(Jogador jogador, Computador computador) {
        this.jogador = jogador;
        this.computador = computador;
    }

    public void iniciar() {
        System.out.println("==== Início do Jogo de Truco ====");

        // 🔹 NOVO: jogador recebe cartas especiais no começo da partida
        System.out.println("\n📦 Recebendo suas cartas especiais iniciais...");
        GerenciadorCartasEspeciais.concederRecompensas(jogador);

        while (pontosJogador < 12 && pontosComputador < 12) {
            try {
                jogarMao();
            } catch (RuntimeException e) {
                // mão encerrada por correria
            }

            System.out.println("\nPLACAR: Você " + pontosJogador + " x " + pontosComputador + " Computador");
        }

        if (pontosJogador >= 12) {
            System.out.println("\n🏆 Parabéns! Você venceu a partida de Truco!");
        } else {
            System.out.println("\n😢 O computador venceu a partida de Truco!");
        }
    }

    private void prepararBaralho(Jogador j1, Computador j2){
        Baralho baralho = new Baralho();
        baralho.embaralhar();
        baralho.definirManilha();

        j1.limparMao();
        j2.limparMao();

        for (int i = 0; i < 3; i++) {
            j1.receberCarta(baralho.distribuir());
            j2.receberCarta(baralho.distribuir());
        }

        System.out.println("Vira: " + baralho.getVira());

        // 🔹 Mantido: aplicar cartas de alteração antes de começar
        j1.aplicarCartasDeAlteracao(baralho, (Jogador) j2);

    }

    private void jogarMao() {
        pontosRodada = 1; // reinicia os pontos da mão
        prepararBaralho(jogador, computador);

        ContextoJogo contexto = new ContextoJogo(jogador, computador, this);

        int vitoriasJogador = 0;
        int vitoriasComputador = 0;

        for (int rodada = 1; rodada <= 3; rodada++) {
            System.out.println("\n--- Rodada " + rodada + " ---");

            // 🔹 NOVO: jogador pode usar uma carta especial antes da rodada
            if (!jogador.getCartasEspeciais().isEmpty()) {
                System.out.println("\nQuer usar uma carta especial? (s/n)");
                String usarCarta = sc.nextLine();

                if (usarCarta.equalsIgnoreCase("s")) {
                    System.out.println("Suas cartas especiais:");
                    for (int i = 0; i < jogador.getCartasEspeciais().size(); i++) {
                        System.out.println(i + " - " + jogador.getCartasEspeciais().get(i));
                    }

                    System.out.print("Escolha uma (ou -1 para cancelar): ");
                    int escolhaCarta = -1;
                    try {
                        escolhaCarta = sc.nextInt();
                    } catch (Exception e) {
                        sc.nextLine();
                    }
                    sc.nextLine(); // limpar buffer

                    if (escolhaCarta >= 0 && escolhaCarta < jogador.getCartasEspeciais().size()) {
                        CartaEspecial cartaUsada = jogador.getCartasEspeciais().remove(escolhaCarta);
                        System.out.println("✨ Você usou " + cartaUsada.getNome() + "!");
                        cartaUsada.aplicarEfeito(contexto);
                    }
                }
            }

            // 🔹 Jogador pode pedir aumento
            System.out.print("Quer pedir aumento (truco/seis/nove/doze)? (s/n): ");
            String respRodada = sc.nextLine();
            if (respRodada.equalsIgnoreCase("s")) {
                pedirAumentoJogador();
            }

            // Computador pode pedir aumento também (aleatório)
            if (computadorPodePedir()) {
                pedirAumentoComputador();
            }

            if (jogador.getMao().isEmpty() || computador.getMao().isEmpty()) break;

            // Jogador escolhe carta
            System.out.println("Suas cartas: " + jogador.getMao());
            System.out.print("Escolha uma carta (0 a " + (jogador.getMao().size() - 1) + "): ");
            int escolha = sc.nextInt();
            sc.nextLine(); // limpar buffer
            Carta cartaJogador = jogador.jogarCarta(escolha);

            // Computador joga carta aleatória
            Carta cartaComputador = computador.jogarCarta(r.nextInt(computador.getMao().size()));

            System.out.println(jogador.getNome() + " jogou " + cartaJogador);
            System.out.println(computador.getNome() + " jogou " + cartaComputador);

            if (cartaJogador.getPeso() > cartaComputador.getPeso()) {
                System.out.println("Você ganhou a rodada!");
                vitoriasJogador++;
            } else if (cartaComputador.getPeso() > cartaJogador.getPeso()) {
                System.out.println("Computador ganhou a rodada!");
                vitoriasComputador++;
            } else {
                System.out.println("Rodada empatada!");
            }

            if (vitoriasJogador == 2 || vitoriasComputador == 2) break;
        }

        // Resultado final da mão
        System.out.println("\n===== Resultado da Mão =====");
        if (vitoriasJogador > vitoriasComputador) {
            System.out.println("Você venceu e ganhou " + pontosRodada + " pontos!");
            pontosJogador += pontosRodada;

            // 🔹 OPCIONAL: remover se não quiser ganho extra por vitória
            // GerenciadorCartasEspeciais.concederRecompensas(jogador);

        } else if (vitoriasComputador > vitoriasJogador) {
            System.out.println("Computador venceu e ganhou " + pontosRodada + " pontos!");
            pontosComputador += pontosRodada;
        } else {
            System.out.println("A mão empatou! Ninguém pontua.");
        }
    }

    // 🔹 Método auxiliar usado pela carta "JOGO DE AZAR"
    public void dobrarPontosRodada() {
        this.pontosRodada *= 2;
    }

    // 🔹 Métodos originais do seu código (mantidos):
    private void pedirAumentoJogador() {
        // lógica original de aumento
        System.out.println("Você pediu aumento!");
        pontosRodada *= 2;
    }

    private void pedirAumentoComputador() {
        // lógica original do bot pedir aumento
        System.out.println("O computador pediu aumento!");
        pontosRodada *= 2;
    }

    private boolean computadorPodePedir() {
        // chance aleatória de o bot pedir aumento
        return r.nextInt(10) < 2; // 20% de chance
    }
}
