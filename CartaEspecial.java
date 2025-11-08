import java.util.function.Consumer;

public class CartaEspecial {
    private String nome;
    private String descricao;
    private String categoria;
    private Consumer<ContextoJogo> efeito;

    public CartaEspecial(String nome, String descricao, String categoria, Consumer<ContextoJogo> efeito) {
        this.nome = nome;
        this.descricao = descricao;
        this.categoria = categoria;
        this.efeito = efeito;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getCategoria() {
        return categoria;
    }

    public void aplicarEfeito(ContextoJogo contexto) {
        if (efeito != null) {
            efeito.accept(contexto);
        }
    }

    @Override
    public String toString() {
        return nome + " [" + categoria + "]";
    }
}
