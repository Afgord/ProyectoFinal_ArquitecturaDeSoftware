package Ejercer_Turno.MVC.PanelesVista;

import Girar_Ruleta.PanelRuleta;
import org.uno.dto.JugadorDTO;
import Ejercer_Turno.MVC.ControlJuego;
import Ejercer_Turno.Interfaces.IModeloDatos; 
import java.awt.*;
import contenido.AudioManager;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.*;
/**
 * 
 * @author lagar
 */
public class PanelTablero extends JPanel {

    private final ControlJuego control;
    private final IModeloDatos modelo; 

    private PanelMazo panelMazo;
    private PanelDescarte panelDescarte;
    private PanelMano panelManoJugador;
    private PanelCartaSeleccionada panelZoom;
    private PanelUno panelUno;
    private PanelRuleta panelRuleta;
    private AudioManager audioModel;

    public PanelTablero(ControlJuego control, IModeloDatos modelo, AudioManager audioModel) {
        this.control = control;
        this.modelo = modelo;
        this.audioModel = audioModel;

        setPreferredSize(new Dimension(1200, 750));
        setBackground(Color.RED);
        setOpaque(true);
        setLayout(null);

        inicializarComponentes();
    }

    private void inicializarComponentes() {
        panelUno = new PanelUno(control, audioModel);
        panelUno.setBounds(1040, 600, 150, 100);
        add(panelUno);

        panelMazo = new PanelMazo(control, modelo);
        panelMazo.setBounds(610, 280, 100, 120);
        add(panelMazo);

        panelDescarte = new PanelDescarte(modelo);
        panelDescarte.setBounds(720, 280, 100, 120);
        add(panelDescarte);

        panelManoJugador = new PanelMano(control, modelo);
        panelManoJugador.setBounds(300, 590, 730, 150);
        add(panelManoJugador);

        panelZoom = new PanelCartaSeleccionada();
        panelZoom.setBounds(630, 450, 100, 120);
        add(panelZoom);

        panelRuleta = new PanelRuleta();
        panelRuleta.setBounds(240, 200, 300, 300);
        add(panelRuleta);

        actualizarVisualizacionTotal();
    }

    public void actualizarVisualizacionTotal() {
        for (Component c : getComponents()) {
            if (c instanceof PanelJugador || c instanceof PanelManoSecundaria) {
                remove(c);
            }
        }
        List<JugadorDTO> todos = modelo.getJugadoresDTO();
        JugadorDTO local = modelo.getJugadorLocalDTO();
        if (local != null) {
            PanelJugador pjLocal = new PanelJugador(local);
            pjLocal.setBounds(20, 670, 250, 80); 
            add(pjLocal);
        }
        List<JugadorDTO> rivales = todos.stream()
                .filter(j -> !j.getNombre().equals(local.getNombre()))
                .collect(Collectors.toList());

        int rivalIdx = 0;
        for (JugadorDTO r : rivales) {
            PanelJugador pjRival = new PanelJugador(r);
            PanelManoSecundaria pms;

            switch (rivalIdx) {
                case 0 -> { 
                    pjRival.setBounds(0, 0, 250, 80);
                    pms = new PanelManoSecundaria(r, "izquierda");
                    pms.setBounds(0, 100, 120, 400);
                    add(pjRival); add(pms);
                }
                case 1 -> { 
                    pjRival.setBounds(350, 120, 250, 80);
                    pms = new PanelManoSecundaria(r, "arriba");
                    pms.setBounds(350, 0, 500, 120);
                    add(pjRival); add(pms);
                }
                case 2 -> {
                    pjRival.setBounds(920, 0, 250, 80);
                    pms = new PanelManoSecundaria(r, "derecha");
                    pms.setBounds(1070, 100, 120, 400);
                    add(pjRival); add(pms);
                }
            }
            rivalIdx++;
        }
        panelManoJugador.refrescarMano();
        panelMazo.repaint();
        panelDescarte.repaint();
        
        revalidate();
        repaint();
    }

    public void refrescarTurno() { 
        actualizarVisualizacionTotal(); 
    }
    
    public PanelCartaSeleccionada getPanelZoom() { 
        return this.panelZoom; 
    }
}