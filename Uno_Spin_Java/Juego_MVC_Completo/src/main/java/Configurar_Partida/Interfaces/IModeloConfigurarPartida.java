/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Configurar_Partida.Interfaces;

/**
 * Contrato de lectura y observación del modelo del CU1.
 */
public interface IModeloConfigurarPartida {

    void registrarObservador(ObservadorConfigurarPartida observador);

    boolean isConfiguracionExitosa();

    boolean isConfiguracionRechazada();

    String getMensajeResultado();
}
