import java.util.Random;

public class Computador extends Jogador {

    private Random random;
    private int chanceFuga = 20;
    private int chanceAceitarTruco = 50;

    public Computador(String nome) {
        super(nome);
        this.random = new Random();
    }

    // ==================================================
    // 🧠 Efeitos estratégicos (controlados pelas cartas)
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
    // 🎮 Lógica simples do bot
    // ==================================================
    public boolean deveFugirDoTruco() {
        int roll = random.nextInt(100);
        return roll < chanceFuga;
    }

    public boolean deveAceitarTruco() {
        int roll = random.nextInt(100);
        return roll < chanceAceitarTruco;
    }
}
