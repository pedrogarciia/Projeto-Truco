import java.util.Random;

public class FabricaDeBots {

    private static final Random random = new Random();

    public static Computador gerarOponente(int numeroDaPartida) {
        // 1️⃣ Define o nível de dificuldade com base na partida
        NivelDificuldade nivel;
        if (numeroDaPartida <= 2) nivel = NivelDificuldade.NIVEL_0;
        else if (numeroDaPartida <= 4) nivel = NivelDificuldade.NIVEL_1;
        else if (numeroDaPartida <= 6) nivel = NivelDificuldade.NIVEL_2;
        else nivel = NivelDificuldade.NIVEL_3;

        // 2️⃣ Escolhe uma personalidade aleatória
        Personalidade[] personalidades = Personalidade.values();
        Personalidade personalidade = personalidades[random.nextInt(personalidades.length)];

        // 3️⃣ Gera o nome do bot (opcional)
        String nome = gerarNomePorPersonalidade(personalidade);

        // 4️⃣ Retorna o novo bot configurado
        return new Computador(nome, personalidade, nivel);
    }

    private static String gerarNomePorPersonalidade(Personalidade p) {
        switch (p) {
            case VALENTE: return "Bot Valente";
            case MEDROSO: return "Bot Medroso";
            case DOIDO: return "Bot Doido";
            case MALANDRO: return "Bot Malandro";
            case CALCULISTA: return "Bot Calculista";
            default: return "Bot Misterioso";
        }
    }
}
