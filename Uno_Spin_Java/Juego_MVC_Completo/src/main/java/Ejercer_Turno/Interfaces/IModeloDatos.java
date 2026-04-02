/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Ejercer_Turno.Interfaces;

import org.uno.dto.CartaDTO;
import org.uno.dto.JugadorDTO;
import org.uno.dto.MazoDTO;
import org.uno.dto.TableroDTO;
import java.awt.Color;
import java.util.List;

/**
 * @author Luis Rafael
 */
public interface IModeloDatos {
    TableroDTO getTableroDTO();
    MazoDTO getMazoDTO();
    CartaDTO getCartaDescarteDTO();
    List<JugadorDTO> getJugadoresDTO();
    JugadorDTO getJugadorLocalDTO(); 
    boolean isUltimaJugadaValida();
    Color[] obtenerColoresConfigurados();
    void registrarObservador(Observador o);
}