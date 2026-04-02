/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercer_Turno.Ejecutador;

import Entidades.*;
import Fachadas.FachadaJuego;
import Ejercer_Turno.MVC.*;
import Ejercer_Turno.Interfaces.*;
import Cambiar_Color.MVC.ModeloColor;
import Cambiar_Color.MVC.ControlColor;
import Cambiar_Color.MVC.PanelSelectorColor;
import contenido.AudioManager;
import java.awt.Color;
import java.awt.Frame;
import java.util.ArrayList;
import java.util.List;
/**
 * 
 * @author lagar
 */
public class Ejecutador {
    public static void main(String[] args) {
        Color cAzul = Color.CYAN;
        Color cRojo = Color.PINK;
        Color cAmarillo = Color.ORANGE;
        Color cVerde = Color.MAGENTA;
        Color cNegro = Color.BLACK;

        List<Jugador> listaJugadores = new ArrayList<>();
        listaJugadores.add(new Jugador("Xrapayel", "/avatares/XD.jpg"));
        listaJugadores.add(new Jugador("Mondongo", "/avatares/mondongo.jpg"));
        listaJugadores.add(new Jugador("Verch", "/avatares/queHiciste.jpg"));
        listaJugadores.add(new Jugador("Gilberto", "/avatares/gilberto.jpg"));

        Tablero tablero = new Tablero(
            listaJugadores, 0, 9,
            true, true, true, true, true, 
            cAzul, cRojo, cAmarillo, cVerde, cNegro
        );

        Mazo mazo = tablero.getMazo();
        for (Jugador j : listaJugadores) {
            for (int i = 0; i < 7; i++) {
                j.agregarCarta(mazo.tomarUnaCarta());
            }
        }

        FachadaJuego fachada = new FachadaJuego();
        fachada.inyectarTablero(tablero);

        ModeloJuego modeloReal = new ModeloJuego(fachada);

        IServicioSeleccionColor servicioColor = new IServicioSeleccionColor() {
            @Override
            public void solicitarColor(Frame padre, Color[] opciones, IResultadoColor callback) {
                ModeloColor mColor = new ModeloColor();
                mColor.registrar(contexto -> callback.onResultado(contexto.getDatosColor()));
                
                ControlColor cColor = new ControlColor(mColor, opciones[0], opciones[1], opciones[2], opciones[3]);
                PanelSelectorColor vistaColor = new PanelSelectorColor(padre, cColor);
                vistaColor.setVisible(true);
            }
        };

        ControlJuego control = new ControlJuego(modeloReal, servicioColor);

        AudioManager audioModel = new AudioManager();
        audioModel.loadMusic("/sound/music/dkc1_achuatic.wav");
        audioModel.loadEffect("tirar", "/sound/effect/tirar.wav", 5);
        audioModel.loadEffect("jalar", "/sound/effect/jalar.wav", 5);
        audioModel.loadEffect("uno", "/sound/effect/uno.wav", 5);
        audioModel.loadEffect("alerta", "/sound/effect/alerta.wav", 5);

        java.awt.EventQueue.invokeLater(() -> {
            FrameTablero ft = new FrameTablero(control, modeloReal, audioModel);
            ft.setVisible(true);
        });
    }
}