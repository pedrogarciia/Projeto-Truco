import java.util.Scanner;
import java.util.Random;

public class Partida {
    private Jogador jogador;
    private Jogador computador;
    private Scanner sc = new Scanner(System.in);
    private Random r = new Random();

    private int pontosJogador = 0;
    private int pontosComputador = 0;
    private int pontosRodada = 1;

    public Partida(Jogador jogador, Jogador computador) {
        this.jogador = jogador;
        this.computador = computador;
    }

    public void iniciar() {
        System.out.println("==== Início do Jogo de Truco ====");

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

    private void prepararBaralho(Jogador j1, Jogador j2) {
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
    }

    private void jogarMao() {
        pontosRodada = 1; // reinicia os pontos da mão
        prepararBaralho(jogador, computador);

        // Perguntar truco antes de mostrar cartas
        System.out.print("Quer pedir truco antes de ver suas cartas? (s/n): ");
        String resp = sc.nextLine();
        if (resp.equalsIgnoreCase("s")) {
            pedirAumentoJogador();
        }

        // Agora mostra as cartas do jogador
        System.out.println("Suas cartas: " + jogador.getMao());

        int vitoriasJogador = 0;
        int vitoriasComputador = 0;

        for (int rodada = 1; rodada <= 3; rodada++) {
            System.out.println("\n--- Rodada " + rodada + " ---");

            // Jogador pode pedir aumento
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
        } else if (vitoriasComputador > vitoriasJogador) {
            System.out.println("Computador venceu e ganhou " + pontosRodada + " pontos!");
            pontosComputador += pontosRodada;
        } else {
            System.out.println("A mão terminou empatada! Ninguém pontua.");
        }
    }

    /**
     * Jogador pede aumento (truco/seis/nove/doze)
     */
    private void pedirAumentoJogador() {
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
            avancarPontosRodada();
            System.out.println("Computador aceitou! Agora vale " + pontosRodada + " pontos.");
        } else {
            System.out.println("Computador correu! Você ganhou " + pontosRodada + " pontos.");
            pontosJogador += pontosRodada;
            throw new RuntimeException("Fim da mão — computador correu.");
        }
    }

    /**
     * Computador pede aumento
     */
    private void pedirAumentoComputador() {
        String aumentoNome = switch (pontosRodada) {
            case 1 -> "TRUCO";
            case 3 -> "SEIS";
            case 6 -> "NOVE";
            case 9 -> "DOZE";
            default -> "TRUCO";
        };

        System.out.println("⚠️ O computador pediu " + aumentoNome + "!");

        System.out.print("Você aceita? (s/n): ");
        String resp = sc.nextLine();

        if (resp.equalsIgnoreCase("s")) {
            avancarPontosRodada();
            System.out.println("Você aceitou! Agora vale " + pontosRodada + " pontos.");
        } else {
            System.out.println("Você correu! Computador ganhou " + pontosRodada + " pontos.");
            pontosComputador += pontosRodada;
            throw new RuntimeException("Fim da mão — jogador correu.");
        }
    }

    /**
     * Avança a pontuação da rodada (truco → 3 → 6 → 9 → 12)
     */
    private void avancarPontosRodada() {
        if (pontosRodada == 1) pontosRodada = 3;
        else if (pontosRodada == 3) pontosRodada = 6;
        else if (pontosRodada == 6) pontosRodada = 9;
        else if (pontosRodada == 9) pontosRodada = 12;
    }

    /**
     * Computador decide aceitar ou não
     */
    private boolean aceitarAumentoComputador() {
        int chance;
        switch (pontosRodada) {
            case 1 -> chance = 70; // truco
            case 3 -> chance = 50; // seis
            case 6 -> chance = 35; // nove
            case 9 -> chance = 20; // doze
            default -> chance = 50;
        }
        return r.nextInt(100) < chance;
    }

    /**
     * Computador decide pedir truco/aumento
     */
    private boolean computadorPodePedir() {
        // Chance pequena de pedir truco logo de cara
        int chance = switch (pontosRodada) {
            case 1 -> 20; // truco
            case 3 -> 15; // seis
            case 6 -> 10; // nove
            case 9 -> 5;  // doze
            default -> 0;
        };
        return r.nextInt(100) < chance;
    }
}


