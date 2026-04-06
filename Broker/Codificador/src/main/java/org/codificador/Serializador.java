package org.codificador;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
/**
 * 
 * @author lagar
 */
public class Serializador {
    public <T extends Serializable> byte[] objetoABytes(T objeto) {
        if (objeto == null) return null;
        
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            
            oos.writeObject(objeto);
            oos.flush();
            return bos.toByteArray();
            
        } catch (IOException e) {
            System.err.println("Error en Serializador: " + e.getMessage());
            return null;
        }
    }
}