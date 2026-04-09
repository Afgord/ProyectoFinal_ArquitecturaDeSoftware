/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package itson.broker;

import fachadas.FachadaJuego;
import fachadas.FachadaDominio;
import org.componentered.entrada.ColaEntrada;
import org.componentered.entrada.Receptor;
import org.componentered.entrada.ServidorTCP;
import org.directorios.implementacion.DirectorioJuego;
import java.util.Scanner;
/**
 * 
 * @author lagar
 */
public class Broker_App {

    public static void main(String[] args) {
        FachadaDominio dominio = new FachadaJuego();
        DirectorioJuego directorio = new DirectorioJuego();
        SuscriptorInterno suscriptor = new SuscriptorInterno(dominio);
        
        ColaEntrada colaEntrada = new ColaEntrada();
        Receptor receptor = new Receptor(colaEntrada, (byte[] datos) -> {
            suscriptor.atenderEventoDesdeRed(datos);
        });
        
        colaEntrada.agregarObservador(receptor);
        
        ServidorTCP servidor = new ServidorTCP(9000, colaEntrada);
        servidor.start();

        System.out.println("=== BROKER UNO SPIN ONLINE (PUERTO 9000) ===");
        manejarConsola();
    }

    private static void manejarConsola() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            String cmd = sc.nextLine();
            if (cmd.equalsIgnoreCase("EXIT")) System.exit(0);
        }
    }
}