import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Computador {
    private String nome;
    private List<Carta> mao;
    private Random random;

    // 🎯 probabilidades alteráveis por cartas
    private int chanceFuga = 20;
    private int chanceAceitarTruco = 50;

    public Computador(String nome) {
        this.nome = nome;
        this.mao = new ArrayList<>();
        this.random = new Random();
    }

    public String getNome() {
        return nome;
    }

    public List<Carta> getMao() {
        return mao;
    }

    public void limparMao() {
        mao.clear();
    }

    public void receberCarta(Carta carta) {
        mao.add(carta);
    }

    public Carta jogarCarta(int indice) {
        if (mao.isEmpty()) return null;
        if (indice < 0 || indice >= mao.size()) indice = random.nextInt(mao.size());
        return mao.remove(indice);
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

    @Override
    public String toString() {
        return nome;
    }
}
