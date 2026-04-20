/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.code;

import java.io.Serializable;

/**
 *
 * @author lagar
 */
public class SerializadorFactory {
    public static <T extends Serializable> ISerializador<T> crearSerializador() {
        return new Serializador<>();
    }
}
