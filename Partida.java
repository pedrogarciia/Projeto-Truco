import java.util.Scanner;
import java.util.Random;

public class Partida {
    private Jogador jogador;
    private Jogador computador;
    private Baralho baralho;
    private int pontosRodada = 1;
    private Scanner sc = new Scanner(System.in);
    private Random r = new Random();

    public Partida(Jogador jogador, Jogador computador) {
        this.jogador = jogador;
        this.computador = computador;
    }

    public void iniciar() {
        baralho = new Baralho();
        baralho.embaralhar();
        baralho.definirManilha();

        jogador.limparMao();
        computador.limparMao();

        for (int i = 0; i < 3; i++) {
            jogador.receberCarta(baralho.distribuir());
            computador.receberCarta(baralho.distribuir());
        }

        System.out.println("Vira: " + baralho.getVira());

        // Perguntar truco antes de mostrar cartas
        System.out.print("Quer pedir truco antes de ver suas cartas? (s/n): ");
        String resp = sc.nextLine();
        if (resp.equalsIgnoreCase("s")) {
            pedirAumento();
        }

        // Agora mostra as cartas do jogador
        System.out.println("Suas cartas: " + jogador.getMao());

        jogarPartida();
    }

    /**
     * Pede aumento (truco, seis, nove, doze)
     */
    private void pedirAumento() {
        String aumentoNome = switch (pontosRodada) {
            case 1 -> "TRUCO";
            case 3 -> "SEIS";
            case 6 -> "NOVE";
            case 9 -> "DOZE";
            default -> "TRUCO";
        };

        System.out.println(jogador.getNome() + " pediu " + aumentoNome + "!");

        boolean aceitou = aceitarAumentoComputador();

        if (aceitou) {
            if (pontosRodada == 1) pontosRodada = 3;
            else if (pontosRodada == 3) pontosRodada = 6;
            else if (pontosRodada == 6) pontosRodada = 9;
            else if (pontosRodada == 9) pontosRodada = 12;

            System.out.println("Computador aceitou! Agora vale " + pontosRodada + " pontos.");
        } else {
            System.out.println("Computador correu! Você ganhou " + pontosRodada + " pontos.");
            System.exit(0);
        }
    }

    /**
     * Computador decide aceitar ou não
     */
    private boolean aceitarAumentoComputador() {
        // Chance menor de aceitar aumentos altos
        int chance;
        switch (pontosRodada) {
            case 1 -> chance = 70; // truco → 70% aceitar
            case 3 -> chance = 50; // seis → 50%
            case 6 -> chance = 35; // nove → 35%
            case 9 -> chance = 20; // doze → 20%
            default -> chance = 50;
        }
        return r.nextInt(100) < chance;
    }

    private void jogarPartida() {
        int vitoriasJogador = 0;
        int vitoriasComputador = 0;

        for (int rodada = 1; rodada <= 3; rodada++) {
            System.out.println("\n--- Rodada " + rodada + " ---");

            // Antes da rodada, jogador pode pedir aumento
            System.out.print("Quer pedir aumento (truco/seis/nove/doze)? (s/n): ");
            sc.nextLine(); // consumir quebra de linha
            String resp = sc.nextLine();
            if (resp.equalsIgnoreCase("s")) {
                pedirAumento();
            }

            if (jogador.getMao().isEmpty() || computador.getMao().isEmpty()) break;

            // Jogador escolhe carta
            System.out.println("Suas cartas: " + jogador.getMao());
            System.out.print("Escolha uma carta (0 a " + (jogador.getMao().size() - 1) + "): ");
            int escolha = sc.nextInt();
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

            // Se alguém já venceu 2 rodadas, encerra
            if (vitoriasJogador == 2 || vitoriasComputador == 2) break;
        }

        // Resultado final
        System.out.println("\n===== Resultado da Partida =====");
        if (vitoriasJogador > vitoriasComputador) {
            System.out.println("Você venceu e ganhou " + pontosRodada + " pontos!");
        } else if (vitoriasComputador > vitoriasJogador) {
            System.out.println("Computador venceu e ganhou " + pontosRodada + " pontos!");
        } else {
            System.out.println("A partida terminou empatada!");
        }
    }
}

