/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.directorios;

/**
 *
 * @author lagar
 */
public class DirectorioFactory {
    public static IDirectorio crearNuevoDirectorio() {
        return new DirectorioJuego(); 
    }
}
