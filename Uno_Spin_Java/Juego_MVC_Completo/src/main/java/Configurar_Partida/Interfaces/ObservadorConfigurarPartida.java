/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Configurar_Partida.Interfaces;

/**
 * Observador del modelo de configuración de partida.
 */
public interface ObservadorConfigurarPartida {

    void notificarCambio(IModeloConfigurarPartida contexto);
}
