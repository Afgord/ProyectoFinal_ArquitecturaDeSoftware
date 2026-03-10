/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Ejercer_Turno.Interfaces;

import Ejercer_Turno.Dominio.Descarte;
import Ejercer_Turno.Dominio.Jugador;
import Ejercer_Turno.Dominio.Mazo;
import Ejercer_Turno.Dominio.Tablero;
import java.util.List;

/**
 *
 * @author lagar
 */
public interface IModeloDatos {
    Tablero getTablero();
    Mazo getMazo();
    Descarte getDescarte();
    List<Jugador> getJugadores();
    void registrarObservador(Observador o);
}
