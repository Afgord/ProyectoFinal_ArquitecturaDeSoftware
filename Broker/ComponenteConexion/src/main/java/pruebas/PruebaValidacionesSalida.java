/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pruebas;

import salida.DispatcherFactory;
import salida.IDispatcher;

/**
 *
 * @author cmartinez
 */
public class PruebaValidacionesSalida {
    public static void main(String[] args) {
        IDispatcher dispatcher = DispatcherFactory.crearDispatcher();

        System.out.println("=== Caso 1: host inválido ===");
        dispatcher.dispatch("", 5000, "Hola".getBytes());

        System.out.println("=== Caso 2: puerto inválido ===");
        dispatcher.dispatch("localhost", -1, "Hola".getBytes());

        System.out.println("=== Caso 3: bytes nulos ===");
        dispatcher.dispatch("localhost", 5000, null);

        System.out.println("=== Caso 4: bytes vacíos ===");
        dispatcher.dispatch("localhost", 5000, new byte[0]);

        System.out.println("[PRUEBA] Prueba de validaciones finalizada.");
    }
}
