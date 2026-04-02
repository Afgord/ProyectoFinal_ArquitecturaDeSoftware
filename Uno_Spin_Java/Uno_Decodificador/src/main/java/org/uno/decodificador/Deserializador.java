/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uno.decodificador;

import org.uno.eventos.comun.MensajeRed;
import java.io.*;
/**
 * 
 * @author lagar
 */
public class Deserializador {
    public MensajeRed bytesAObjeto(byte[] datos) {
        if (datos == null || datos.length == 0) return null;
        try (ByteArrayInputStream bis = new ByteArrayInputStream(datos);
             ObjectInputStream ois = new ObjectInputStream(bis)) {
            return (MensajeRed) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error en Deserializador: " + e.getMessage());
            return null;
        }
    }
}
