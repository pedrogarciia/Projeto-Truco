public class App {
    public static void main(String[] args) {
        Jogador jogador = new Jogador("Você");
        Jogador computador = new Jogador("Computador");

        Partida partida = new Partida(jogador, computador);
        partida.iniciar();
    }
}