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
        if (conexion != null && conexiones.size() < 4) {
            conexiones.put(conexion.getIdJugador(), conexion);
        }
    }

    @Override
    public Conexion obtenerConexion(String idJugador) {
        return conexiones.get(idJugador);
    }

    @Override
    public List<Conexion> obtenerTodosMenos(String idEmisor) {
        List<Conexion> destinatarios = new ArrayList<>();
        for (Conexion c : conexiones.values()) {
            if (!c.getIdJugador().equals(idEmisor)) {
                destinatarios.add(c);
            }
        }
        return destinatarios;
    }

    @Override
    public List<Conexion> obtenerTodos() {
        return new ArrayList<>(conexiones.values());
    }

    @Override
    public void eliminarConexion(String idJugador) {
        conexiones.remove(idJugador);
    }

    @Override
    public int totalConectados() {
        return conexiones.size();
    }
    
    @Override
    public void limpiarDirectorio() {
        this.conexiones.clear();
    }
}
