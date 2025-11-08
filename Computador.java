import java.util.Random;

public class Computador extends Jogador {

    private Random random;

    // ==================================================
    // 🧩 Novos atributos
    // ==================================================
    private Personalidade personalidade;        // Tipo de comportamento (VALENTE, MEDROSO, etc)
    private NivelDificuldade nivel;             // Nível de dificuldade (0–3)

    // 🎯 Probabilidades básicas (podem ser alteradas pela personalidade e nível)
    private int chanceFuga = 20;
    private int chanceAceitarTruco = 50;

    // ==================================================
    // 🏗️ Construtor atualizado
    // ==================================================
    public Computador(String nome, Personalidade personalidade, NivelDificuldade nivel) {
        super(nome);
        this.random = new Random();
        this.personalidade = personalidade;
        this.nivel = nivel;
        ajustarComportamentoBaseadoNaPersonalidade();
    }

    // ==================================================
    // ⚙️ Métodos novos: personalização inicial
    // ==================================================
    private void ajustarComportamentoBaseadoNaPersonalidade() {
        switch (personalidade) {
            case VALENTE:
                chanceFuga = 5;
                chanceAceitarTruco = 90;
                break;
            case MEDROSO:
                chanceFuga = 60;
                chanceAceitarTruco = 30;
                break;
            case DOIDO:
                chanceFuga = 10;
                chanceAceitarTruco = 70;
                break;
            case MALANDRO:
                chanceFuga = 25;
                chanceAceitarTruco = 60;
                break;
            case CALCULISTA:
                chanceFuga = 15;
                chanceAceitarTruco = 65;
                break;
        }
    }

    // ==================================================
    // 🧠 Efeitos estratégicos (mantidos do seu código)
    // ==================================================
    public void aumentarChanceFuga(int valor) {
        chanceFuga = Math.min(100, chanceFuga + valor);
    }

    public void aumentarChanceAceitarTruco(int valor) {
        chanceAceitarTruco = Math.min(100, chanceAceitarTruco + valor);
    }

    public int getChanceAceitarTruco() {
        return chanceAceitarTruco;
    }

    public int getChanceFuga() {
        return chanceFuga;
    }

    // ==================================================
    // 🎮 Lógica do bot (ajustada para levar em conta o nível)
    // ==================================================
    public boolean deveFugirDoTruco() {
        int roll = random.nextInt(100);

        // Aumenta ou reduz o risco conforme o nível
        if (nivel == NivelDificuldade.NIVEL_0) return roll < chanceFuga;
        if (nivel == NivelDificuldade.NIVEL_1) return roll < chanceFuga - 5;
        if (nivel == NivelDificuldade.NIVEL_2) return roll < chanceFuga - 10;
        if (nivel == NivelDificuldade.NIVEL_3) return roll < chanceFuga - 15;

        return roll < chanceFuga;
    }

    public boolean deveAceitarTruco() {
        int roll = random.nextInt(100);

        // Níveis mais altos aceitam mais truco (menos medo)
        if (nivel == NivelDificuldade.NIVEL_0) return roll < chanceAceitarTruco;
        if (nivel == NivelDificuldade.NIVEL_1) return roll < chanceAceitarTruco + 5;
        if (nivel == NivelDificuldade.NIVEL_2) return roll < chanceAceitarTruco + 10;
        if (nivel == NivelDificuldade.NIVEL_3) return roll < chanceAceitarTruco + 15;

        return roll < chanceAceitarTruco;
    }

    // ==================================================
    // 🧩 Getters
    // ==================================================
    public Personalidade getPersonalidade() {
        return personalidade;
    }

    public NivelDificuldade getNivel() {
        return nivel;
    }
}
