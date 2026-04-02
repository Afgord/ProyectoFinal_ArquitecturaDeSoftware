/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ejercer_Turno.MVC;

import org.uno.dto.CartaDTO;
import Ejercer_Turno.Interfaces.IModeloAcciones;
import Ejercer_Turno.Interfaces.IServicioSeleccionColor;
import java.awt.Color;
import java.awt.Frame;
/**
 * 
 * @author lagar
 */
public class ControlJuego {
    private final IModeloAcciones modeloAcciones;
    private final IServicioSeleccionColor servicioColor;

    public ControlJuego(IModeloAcciones modeloAcciones, IServicioSeleccionColor servicioColor) {
        this.modeloAcciones = modeloAcciones;
        this.servicioColor = servicioColor;
    }

    public void solicitarTirarCarta(CartaDTO carta) {
        modeloAcciones.tirarCarta(carta);
    }

    public void solicitarRobarCarta() {
        modeloAcciones.robarCarta();
    }

    public void solicitarAplicarCastigo() {
        modeloAcciones.aplicarCastigo();
    }

    public void solicitarSeleccionColor(CartaDTO carta, Frame padre) {
        Color[] colores = modeloAcciones.obtenerColoresConfigurados();
        
        servicioColor.solicitarColor(padre, colores, (resultado) -> {
            if (resultado != null) {
                modeloAcciones.tirarCartaNegra(carta, resultado.getColor(), resultado.getNombre());
            }
        });
    }
}