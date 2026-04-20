/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.desc;

import java.io.Serializable;

/**
 *
 * @author lagar
 * @param <T>
 */
public interface IDeserializador<T extends Serializable> {
    T bytesAObjeto(byte[] datos);
}