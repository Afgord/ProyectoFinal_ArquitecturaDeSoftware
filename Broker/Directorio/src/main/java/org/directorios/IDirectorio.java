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
    public void registrarConexion(Conexion conexion);
    public void removerConexion(String idJugador);
    public Conexion obtenerConexion(String idJugador);
    public List<Conexion> obtenerTodos();

}
