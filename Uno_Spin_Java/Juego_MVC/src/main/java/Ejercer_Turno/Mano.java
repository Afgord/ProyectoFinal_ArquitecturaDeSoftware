package Ejercer_Turno;

import java.util.ArrayList;
import java.util.List;

public class Mano implements IManoReadOnly {
    private final List<Carta> cartas;

    public Mano() {
        this.cartas = new ArrayList<>();
    }

    public void agregarCarta(Carta carta) {
        if (carta != null) {
            cartas.add(carta);
        }
    }

    public void removerCarta(Carta carta) {
        cartas.remove(carta);
    }


    @Override
    public List<ICartaReadOnly> getCartasParaVista() {
        return new ArrayList<>(cartas); 
    }

    @Override
    public int getSize() {
        return cartas.size();
    }

    public List<Carta> getCartasReales() {
        return cartas;
    }
}