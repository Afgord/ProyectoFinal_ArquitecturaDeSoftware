/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Ejercer_Turno.Interfaces;

import Ejercer_Turno.Dominio.*;
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