public class Bot extends Jogador {
    private Personalidade personalidade;
    private int nivelIA;

    public Bot(String nome, Personalidade personalidade, int nivelIA) {
        super(nome);
        this.personalidade = personalidade;
        this.nivelIA = nivelIA;
    }

    public void decidirJogada() {
        // Aqui vai a lógica do bot dependendo do nível e personalidade
    }

    public void reagirTruco() {
        // Reação do bot a um pedido de truco
    }

    public Personalidade getPersonalidade() {
        return personalidade;
    }
}
