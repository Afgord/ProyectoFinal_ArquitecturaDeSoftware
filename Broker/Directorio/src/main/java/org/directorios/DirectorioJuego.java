/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.directorios;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
/**
 * 
 * @author lagar
 */
class DirectorioJuego implements IDirectorio{
    private final Map<String, Conexion> conexiones;

    public DirectorioJuego() {
        this.conexiones = new LinkedHashMap<>();
    }

    @Override
    public void registrarConexion(Conexion conexion) {
        if (conexion == null) return;
        if (conexiones.containsKey(conexion.getIdJugador()) || conexiones.size() < 5) {
            conexiones.put(conexion.getIdJugador(), conexion);
            System.out.println("[Directorio] Registrada conexion idJugador=" + conexion.getIdJugador()
                    + " ip=" + conexion.getIp() + " puerto=" + conexion.getPuerto());
        } else {
            System.out.println("[Directorio] Capacidad llena, ignorando registro de " + conexion.getIdJugador());
        }
    }

    @Override
    public void removerConexion(String idJugador) {
        if (idJugador == null) return;
        Conexion removida = conexiones.remove(idJugador);
        if (removida != null) {
            System.out.println("[Directorio] Removida conexion idJugador=" + idJugador);
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
