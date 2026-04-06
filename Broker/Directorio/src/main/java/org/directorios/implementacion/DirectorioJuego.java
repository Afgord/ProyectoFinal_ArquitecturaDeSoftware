/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.directorios.implementacion;

import org.directorios.dominio.Conexion;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 
 * @author lagar
 */
public class DirectorioJuego {
    private Map<String, Conexion> conexiones;

    public DirectorioJuego() {
        this.conexiones = new HashMap<>();
    }

    public void registrarConexion(Conexion conexion) {
        if (conexion != null && conexiones.size() < 4) {
            conexiones.put(conexion.getIdJugador(), conexion);
        }
    }

    public Conexion obtenerConexion(String idJugador) {
        return conexiones.get(idJugador);
    }

    public List<Conexion> obtenerTodosMenos(String idEmisor) {
        List<Conexion> destinatarios = new ArrayList<>();
        for (Conexion c : conexiones.values()) {
            if (!c.getIdJugador().equals(idEmisor)) {
                destinatarios.add(c);
            }
        }
        return destinatarios;
    }

    public List<Conexion> obtenerTodos() {
        return new ArrayList<>(conexiones.values());
    }

    public void eliminarConexion(String idJugador) {
        conexiones.remove(idJugador);
    }

    public int totalConectados() {
        return conexiones.size();
    }
    
    public void limpiarDirectorio() {
        this.conexiones.clear();
    }
}
