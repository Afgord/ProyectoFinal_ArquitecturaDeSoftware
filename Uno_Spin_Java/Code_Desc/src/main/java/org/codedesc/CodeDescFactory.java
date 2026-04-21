/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.codedesc;

import java.io.Serializable;

/**
 *
 * @author lagar
 */
public class CodeDescFactory {
    public static <T extends Serializable> ISerializador<T> crearSerializador() {
        return new Serializador<>();
    }
    public static <T extends Serializable> IDeserializador<T> crearDeserializador() {
        return new Deserializador<>();
    }
}
