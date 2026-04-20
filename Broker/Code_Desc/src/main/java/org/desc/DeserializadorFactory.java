/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.desc;

import java.io.Serializable;

/**
 *
 * @author lagar
 */
public class DeserializadorFactory {
    public static <T extends Serializable> IDeserializador<T> crearDeserializador() {
        return new Deserializador<>();
    }
}
