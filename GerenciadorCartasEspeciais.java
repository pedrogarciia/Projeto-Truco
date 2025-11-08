import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GerenciadorCartasEspeciais {
    private static List<CartaEspecial> todasCartas = new ArrayList<>();
    private static Random random = new Random();

    public static void inicializarCartas() {
        todasCartas.clear();

        // ============================
        // 💥 Cartas de Alteração de Mão
        // ============================

        // 🌀 ZAP ETERNO
        todasCartas.add(new CartaEspecial(
            "ZAP ETERNO",
            "Concede um pseudo zap (perde apenas para o zap verdadeiro).",
            "Alteração",
            ctx -> {
                Jogador j = ctx.getJogador();
                if (j.getMao().isEmpty()) return;
                Carta maisFraca = j.getMao().stream()
                    .min((a, b) -> Integer.compare(a.getPeso(), b.getPeso()))
                    .get();
                maisFraca.setPeso(15); // força abaixo da manilha real
                System.out.println("⚡ Sua carta " + maisFraca + " foi energizada com poder ZAP!");
            }
        ));

        // 🤡 CORINGA CAIPIRA
        todasCartas.add(new CartaEspecial(
            "CORINGA CAIPIRA",
            "Transforma uma carta aleatória em uma pseudo manilha.",
            "Alteração",
            ctx -> {
                Jogador j = ctx.getJogador();
                if (j.getMao().isEmpty()) return;
                Carta aleatoria = j.getMao().get(random.nextInt(j.getMao().size()));
                aleatoria.setPeso(13 + random.nextInt(2)); // quase manilha
                System.out.println("🤠 Sua carta " + aleatoria + " virou uma pseudo manilha!");
            }
        ));

        // 🔁 CESAR ME DÁ
        todasCartas.add(new CartaEspecial(
            "CESAR ME DÁ",
            "Troca a carta mais forte do adversário com outra do baralho.",
            "Alteração",
            ctx -> {
                Computador c = ctx.getComputador();
                if (c.getMao().isEmpty()) return;
                Carta maisForteBot = c.getMao().stream()
                    .max((a, b) -> Integer.compare(a.getPeso(), b.getPeso()))
                    .get();
                c.getMao().remove(maisForteBot);
                Carta nova = new Baralho().distribuir();
                c.receberCarta(nova);
                System.out.println("🃏 Você fez o bot trocar sua carta mais forte por uma nova aleatória!");
            }
        ));

        // ============================
        // 🧠 Cartas de Efeito Estratégico
        // ============================

        // 🚗 PISA FUNDO
        todasCartas.add(new CartaEspecial(
            "PISA FUNDO",
            "+30% de chance do bot fugir do truco.",
            "Estratégico",
            ctx -> {
                ctx.getComputador().aumentarChanceFuga(30);
                System.out.println("🚗 O bot ficou mais medroso diante da sua ousadia!");
            }
        ));

        // 🧠 BLEFADOR NATO
        todasCartas.add(new CartaEspecial(
            "BLEFADOR NATO",
            "+30% de chance do bot aceitar o truco.",
            "Estratégico",
            ctx -> {
                ctx.getComputador().aumentarChanceAceitarTruco(30);
                System.out.println("😈 O bot está mais inclinado a aceitar seus blefes!");
            }
        ));

        // 👻 TRUCO FANTASMA
        todasCartas.add(new CartaEspecial(
            "TRUCO FANTASMA",
            "Revela a chance do bot aceitar o truco.",
            "Estratégico",
            ctx -> {
                int chance = ctx.getComputador().getChanceAceitarTruco();
                System.out.println("👻 O espírito do truco sussurra: o bot tem " + chance + "% de chance de aceitar seu truco.");
            }
        ));

        // 👀 MÃO DE ONZE
        todasCartas.add(new CartaEspecial(
            "MÃO DE ONZE",
            "Permite ver as cartas do bot.",
            "Estratégico",
            ctx -> {
                System.out.println("👀 As cartas do bot são: " + ctx.getComputador().getMao());
            }
        ));

        // ============================
        // ⚖️ Cartas de Risco e Recompensa
        // ============================

        // 🎲 JOGO DE AZAR
        todasCartas.add(new CartaEspecial(
            "JOGO DE AZAR",
            "Toda mão começa valendo o dobro.",
            "Risco",
            ctx -> {
                ctx.getPartida().dobrarPontosRodada();
                System.out.println("🎲 A rodada agora vale o DOBRO!");
            }
        ));

        // 💪 CORAGEM CEGA
        todasCartas.add(new CartaEspecial(
            "CORAGEM CEGA",
            "Toda vez que pedir truco, perde 1 ponto; se vencer, ganha o dobro.",
            "Risco",
            ctx -> {
                ctx.getJogador().ativarCoragemCega();
                System.out.println("💪 Você ativou a Coragem Cega!");
            }
        ));

        // 🔄 COMUNISTA
        todasCartas.add(new CartaEspecial(
            "COMUNISTA",
            "Troca sua carta mais forte com a mais forte do bot.",
            "Risco",
            ctx -> {
                Jogador j = ctx.getJogador();
                Computador bot = ctx.getComputador();
                if (j.getMao().isEmpty() || bot.getMao().isEmpty()) return;

                Carta forteJogador = j.getMao().stream()
                    .max((a, b) -> Integer.compare(a.getPeso(), b.getPeso()))
                    .get();
                Carta forteBot = bot.getMao().stream()
                    .max((a, b) -> Integer.compare(a.getPeso(), b.getPeso()))
                    .get();

                j.getMao().remove(forteJogador);
                bot.getMao().remove(forteBot);

                j.receberCarta(forteBot);
                bot.receberCarta(forteJogador);

                System.out.println("🔄 Sua carta mais forte foi trocada com a do bot!");
            }
        ));
    }

    // =========================================
    // 🎁 Recompensas e utilitários
    // =========================================

    public static void concederRecompensas(Jogador jogador) {
        System.out.println("💎 Recompensa: Você recebeu uma carta de cada categoria!");
        List<String> categorias = List.of("Alteração", "Estratégico", "Risco");

        for (String categoria : categorias) {
            List<CartaEspecial> cartasCategoria = filtrarPorCategoria(categoria);
            if (cartasCategoria.isEmpty()) continue;
            CartaEspecial carta = cartasCategoria.get(random.nextInt(cartasCategoria.size()));
            jogador.receberCartaEspecial(carta);
            System.out.println("• Nova carta: " + carta.getNome() + " [" + carta.getCategoria() + "]");
        }
    }

    private static List<CartaEspecial> filtrarPorCategoria(String categoria) {
        List<CartaEspecial> filtradas = new ArrayList<>();
        for (CartaEspecial c : todasCartas) {
            if (c.getCategoria().equalsIgnoreCase(categoria)) {
                filtradas.add(c);
            }
        }
        return filtradas;
    }

    public static List<CartaEspecial> getTodasCartas() {
        return Collections.unmodifiableList(todasCartas);
    }
}
