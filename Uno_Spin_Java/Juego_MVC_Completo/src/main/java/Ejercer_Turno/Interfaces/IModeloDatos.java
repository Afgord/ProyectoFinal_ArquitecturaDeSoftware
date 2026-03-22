/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Ejercer_Turno.Interfaces;

import DTOs.CartaDTO;
import DTOs.JugadorDTO;
import DTOs.MazoDTO;
import DTOs.TableroDTO;
import java.awt.Color;
import java.util.List;
/**
 * 
 * @author Luis Rafael
 */
public interface IModeloDatos {
    TableroDTO getTableroDTO();
    MazoDTO getMazoDTO();
    CartaDTO getCartaDescarteDTO();
    List<JugadorDTO> getJugadoresDTO();
    boolean isUltimaJugadaValida();
    Color[] obtenerColoresConfigurados();
    void registrarObservador(Observador o);
    void tirarCarta(CartaDTO carta);
    void tirarCartaNegra(CartaDTO carta, Color nuevoColor, String nombreColor);
    void robarCarta();
}