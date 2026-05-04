package Ejercer_Turno.Interfaces;

import dtos.CartaDTO;

/**
 * Contrato Outbound.
 * Define las intenciones locales que el MVC quiere publicar a la red.
 * El EventoTraductor es la implementación que las convierte en eventos
 * serializables y las despacha vía IPublicador.
 */
public interface IModelEventos {
    void emitirTirarCarta(CartaDTO carta);
    void emitirRobarCarta();
    void emitirPasarTurno();
    void emitirGritar();
}
