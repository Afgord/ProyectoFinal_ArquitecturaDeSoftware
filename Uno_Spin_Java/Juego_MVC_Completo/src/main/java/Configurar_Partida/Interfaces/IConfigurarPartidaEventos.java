/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Configurar_Partida.Interfaces;

/**
 * Contrato outbound del CU1 - Configurar partida.
 * 
 * Permite que el MVC solicite la publicación de una configuración
 * sin conocer eventos, serialización ni detalles de red.
 */
public interface IConfigurarPartidaEventos {

    void emitirConfigurarPartida(
            int rangoMinimo,
            int rangoMaximo,
            int numeroComodines,
            int numeroCartasAccion,
            int tiempoMaximoMostrarCartas
    );
}
