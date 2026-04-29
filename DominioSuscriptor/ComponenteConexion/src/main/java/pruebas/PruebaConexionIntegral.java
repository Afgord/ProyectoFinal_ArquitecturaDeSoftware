/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pruebas;

import entrada.IReceptorExterno;
import entrada.Receptor;
import entrada.ServidorTCP;
import salida.DispatcherFactory;
import salida.IDispatcher;

/**
 *
 * @author cmartinez
 */
public class PruebaConexionIntegral {
    
    public static void main(String[] args) {
        final int puerto = 5000;

        IReceptorExterno receptorExterno = new IReceptorExterno() {
            @Override
            public void recibir(byte[] bytes) {
                System.out.println("[PRUEBA] Mensaje recibido: " + new String(bytes));
            }
        };

        ServidorTCP servidor = new ServidorTCP(puerto);
        Receptor receptor = new Receptor(receptorExterno);

        servidor.addObserver(receptor);
        servidor.iniciar();

        IDispatcher dispatcher = DispatcherFactory.crearDispatcher();

        try {
            Thread.sleep(1000);

            dispatcher.dispatch("localhost", puerto, "Hola desde Observer Push".getBytes());

            Thread.sleep(1500);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            servidor.detener();
            System.out.println("[PRUEBA] Prueba integral finalizada.");
        }
    }
    
}
