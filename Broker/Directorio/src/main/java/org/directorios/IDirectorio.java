/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.directorios;

import java.util.List;

/**
 *
 * @author lagar
 */
public interface IDirectorio {
    void registrarConexion(Conexion conexion);
    Conexion obtenerConexion(String idJugador);
    List<Conexion> obtenerTodosMenos(String idEmisor);
    List<Conexion> obtenerTodos();
    void eliminarConexion(String idJugador);
    int totalConectados();
    void limpiarDirectorio();
}
