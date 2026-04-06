/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.decodificador;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
/**
 * 
 * @author lagar
 */
public class Deserializador {

    public <T extends Serializable> T bytesAObjeto(byte[] datos, Class<T> tipo) {
        if (datos == null || datos.length == 0) return null;

        try (ByteArrayInputStream bis = new ByteArrayInputStream(datos);
             ObjectInputStream ois = new ObjectInputStream(bis)) {
            
            Object objeto = ois.readObject();
            return tipo.cast(objeto);
            
        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            System.err.println("Error en Deserializador: " + e.getMessage());
            return null;
        }
    }
}
