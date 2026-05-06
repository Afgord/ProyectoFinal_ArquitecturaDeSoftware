/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.directorios;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 
 * @author lagar
 */
class DirectorioJuego implements IDirectorio{
    private Map<String, Conexion> conexiones;

    public DirectorioJuego() {
        this.conexiones = new HashMap<>();
    }

    @Override
    public void registrarConexion(Conexion conexion) {
        if (conexion != null && conexiones.size() < 5) {
            conexiones.put(conexion.getIdJugador(), conexion);
        }
    }

    @Override
    public Conexion obtenerConexion(String idJugador) {
        return conexiones.get(idJugador);
    }

    @Override
    public List<Conexion> obtenerTodos() {
        return new ArrayList<>(conexiones.values());
    }
}
