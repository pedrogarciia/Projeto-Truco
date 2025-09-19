import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Baralho {
    private List<Carta> cartas = new ArrayList<>();
    private Carta vira;
    private String[] valores = {"4", "5", "6", "7", "Q", "J", "K", "A", "2", "3"};
    private String[] naipes = {"Paus", "Ouros", "Copas", "Espadas"}; // <-- corrigido

    public Baralho() {
        for (String valor : valores) {
            for (String naipe : naipes) {
                cartas.add(new Carta(valor, naipe));
            }
        }
    }

    public void embaralhar() {
        Collections.shuffle(cartas);
    }

    public Carta distribuir() {
        return cartas.remove(0);
    }

    public void definirManilha() {
        vira = distribuir();
        String manilhaValor = proximoValor(vira.getValor());

        for (Carta c : cartas) {
            if (c.getValor().equals(manilhaValor)) {
                int bonus = switch (c.getNaipe()) {
                    case "Paus" -> 1;
                    case "Ouros" -> 2;
                    case "Copas" -> 3;
                    case "Espadas" -> 4;
                    default -> 0;
                };
                c.setPeso(100 + bonus);
            } else {
                int pesoNormal = indexOf(valores, c.getValor());
                c.setPeso(pesoNormal);
            }
        }
    }

    private String proximoValor(String valor) {
        for (int i = 0; i < valores.length; i++) {
            if (valores[i].equals(valor)) {
                return valores[(i + 1) % valores.length];
            }
        }
        return "4"; // fallback
    }

    private int indexOf(String[] array, String v) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(v)) return i;
        }
        return -1;
    }

    public Carta getVira() {
        return vira;
    }
}
