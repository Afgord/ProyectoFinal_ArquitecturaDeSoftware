/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Ejercer_Turno.Interfaces;

import java.util.List;

public interface IManoReadOnly {
    List<? extends ICartaReadOnly> getCartasParaVista();
    int getSize();
}