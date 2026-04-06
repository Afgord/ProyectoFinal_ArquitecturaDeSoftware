/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.directorios.dominio;

public class Conexion {

    private String idJugador;
    private String ip;
    private int puerto;
    private Object canalComunicacion; 

    public Conexion(String idJugador, String ip, int puerto, Object canalComunicacion) {
        this.idJugador = idJugador;
        this.ip = ip;
        this.puerto = puerto;
        this.canalComunicacion = canalComunicacion;
    }

    public String getIdJugador() { return idJugador; }
    public String getIp() { return ip; }
    public int getPuerto() { return puerto; }
    public Object getCanalComunicacion() { return canalComunicacion; }
}