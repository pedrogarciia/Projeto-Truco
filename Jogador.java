import java.util.ArrayList;
import java.util.List;

public class Jogador {
    private String nome;
    private List<Carta> mao;
    private List<CartaEspecial> cartasEspeciais;
    private boolean coragemCegaAtiva = false;

    public Jogador(String nome) {
        this.nome = nome;
        this.mao = new ArrayList<>();
        this.cartasEspeciais = new ArrayList<>();
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
        if (indice < 0 || indice >= mao.size()) return null;
        return mao.remove(indice);
    }

    public void receberCartaEspecial(CartaEspecial carta) {
        // adiciona cópia independente (para não alterar o modelo global)
        cartasEspeciais.add(new CartaEspecial(
            carta.getNome(), carta.getDescricao(), carta.getCategoria(), carta.getEfeito()
        ));
    }

    public List<CartaEspecial> getCartasEspeciais() {
        return cartasEspeciais;
    }

    // ==================================================
    // 💪 Coragem Cega
    // ==================================================
    public void ativarCoragemCega() {
        coragemCegaAtiva = true;
    }

    public boolean isCoragemCegaAtiva() {
        return coragemCegaAtiva;
    }

    // ==================================================
    // 🧩 Cartas de Alteração pré-jogo
    // ==================================================
    public void aplicarCartasDeAlteracao(Baralho baralho, Jogador adversario) {
        // aqui você pode aplicar efeitos pré-mão no futuro (se desejar)
    }

    @Override
    public String toString() {
        return nome;
    }
}

