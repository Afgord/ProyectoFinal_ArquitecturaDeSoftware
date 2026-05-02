/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.directorios;
/**
 * 
 * @author lagar
 */
public class Conexion {
    private String idJugador;
    private String ip;
    private int puerto;

    public Conexion(String idJugador, String ip, int puerto) {
        this.idJugador = idJugador;
        this.ip = ip;
        this.puerto = puerto;
    }

    public String getIdJugador() { return idJugador; }
    public String getIp() { return ip; }
    public int getPuerto() { return puerto; }
}