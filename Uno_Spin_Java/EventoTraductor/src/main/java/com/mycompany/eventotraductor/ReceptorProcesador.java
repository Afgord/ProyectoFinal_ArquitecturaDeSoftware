

package com.mycompany.eventotraductor;

/**
 * Clase 2: ReceptorProcesador (Flujo Inbound)
 * Recibe de la red y ejecuta en el Modelo local.
 */
class ReceptorProcesador {

    private final IDeserializador<Evento> deserializador;
    private final IModeloAcciones modelo;
    private final EventoTraductor traductor;
    private final String idJugadorLocal;

    public ReceptorProcesador(IDeserializador<Evento> deserializador, 
                              IModeloAcciones modelo, 
                              EventoTraductor traductor, 
                              String idJugadorLocal) {
        this.deserializador = deserializador;
        this.modelo = modelo;
        this.traductor = traductor;
        this.idJugadorLocal = idJugadorLocal;
    }

    public void recibirYProcesar(byte[] datos) {
        Evento evento = deserializador.bytesAObjeto(datos);
        if (evento == null) return;

        // FILTRO DE ECO: Si el evento lo generé yo mismo, lo descarto.
        // Ya se ejecutó localmente antes de enviarse al servidor.
        if (evento instanceof EventoAccion) {
            EventoAccion accion = (EventoAccion) evento;
            if (accion.getIdJugador().equals(this.idJugadorLocal)) {
                return; 
            }
        }

        // Activamos bloqueo para evitar bucle infinito
        traductor.setBloqueadoPorRed(true);

        try {
            if (evento instanceof EventoTirarCarta) {
                EventoTirarCarta e = (EventoTirarCarta) evento;
                CartaDTO dto = e.getCarta();
                
                // Nota Crítica: Si es comodín, el modelo debe saber extraer el color de CartaDTO.
                // Si IModeloAcciones te obliga a usar tirarCartaNegra, tendrás que añadir lógica aquí
                // para distinguir si la carta es negra leyendo el DTO.
                if (dto.isComodin()) { 
                     // Suponiendo que CartaDTO tiene getNuevoColor()
                     modelo.tirarCartaNegra(dto, dto.getColor(), ""); 
                } else {
                     modelo.tirarCarta(dto);
                }
            } 
            else if (evento instanceof EventoRobarCarta) {
                modelo.robarCarta();
            }
            else if (evento instanceof EventoPasarTurno) {
                // Requiere método en IModeloAcciones si es aplicable
            }
        } finally {
            // Garantizar la liberación del lock incluso si el Modelo lanza excepción
            traductor.setBloqueadoPorRed(false);
        }
    }
}
