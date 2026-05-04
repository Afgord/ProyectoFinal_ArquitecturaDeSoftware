package Ejercer_Turno.MVC.PanelesVista;

import Ejercer_Turno.Interfaces.IModeloDatos;
import Ejercer_Turno.MVC.ControlJuego;
import Girar_Ruleta.PanelRuleta;
import contenido.AudioManager;
import dtos.JugadorDTO;
import java.awt.*;
import java.util.List;
import javax.swing.*;

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
    private JLabel lbTextoEstado;

    private Timer timerError;

    public PanelTablero(ControlJuego control, IModeloDatos modelo, AudioManager audioModel) {
        this.control = control;
        this.modelo = modelo;
        this.audioModel = audioModel;

        setPreferredSize(new Dimension(1200, 750));
        setBackground(Color.RED);
        setLayout(null);

        lbTextoEstado = new JLabel("Selecciona una carta", SwingConstants.CENTER);
        lbTextoEstado.setBounds(700, 420, 320, 30);
        lbTextoEstado.setFont(new Font("Arial", Font.BOLD, 18));
        lbTextoEstado.setForeground(Color.WHITE);
        add(lbTextoEstado);

        timerError = new Timer(2000, e -> {
            lbTextoEstado.setText("Carta Seleccionada");
            lbTextoEstado.setForeground(Color.WHITE);
        });
        timerError.setRepeats(false);

        inicializarComponentes();
    }

    private void inicializarComponentes() {
        panelUno = new PanelUno(control, audioModel);
        panelUno.setBounds(1040, 600, 150, 100);
        add(panelUno);

        panelMazo = new PanelMazo(control);
        panelMazo.setBounds(610, 280, 100, 120);
        add(panelMazo);

        panelDescarte = new PanelDescarte(modelo);
        panelDescarte.setBounds(720, 280, 100, 120);
        add(panelDescarte);

        panelManoJugador = new PanelMano(control, modelo);
        panelManoJugador.setBounds(200, 590, 800, 150);
        add(panelManoJugador);

        panelZoom = new PanelCartaSeleccionada();
        panelZoom.setBounds(800, 450, 100, 120);
        add(panelZoom);

        panelRuleta = new PanelRuleta();
        panelRuleta.setBounds(300, 200, 300, 300);
        add(panelRuleta);

        actualizarRivales();
    }

    public void actualizarEstadoVisual(IModeloDatos contexto) {
        if (!contexto.isUltimaJugadaValida()) {
            if (timerError.isRunning()) timerError.restart();

            lbTextoEstado.setText("¡CARTA NO VÁLIDA!");
            lbTextoEstado.setForeground(Color.YELLOW);
            timerError.start();
        } else {
            timerError.stop();
            lbTextoEstado.setText("Carta Seleccionada");
            lbTextoEstado.setForeground(Color.WHITE);
        }
        lbTextoEstado.repaint();
    }

    public void actualizarRivales() {
        for (Component c : getComponents()) {
            if (c instanceof PanelJugador || c instanceof PanelManoSecundaria) {
                remove(c);
            }
        }

        List<JugadorDTO> jugadores = modelo.getJugadoresDTO();
        String idLocal = modelo.getIdJugadorLocal();
        String idTurno = modelo.getIdJugadorTurnoActual();

        int rivalIdx = 0;
        for (int i = 0; i < jugadores.size(); i++) {
            JugadorDTO j = jugadores.get(i);
            if (j.idJugador() != null && j.idJugador().equals(idLocal)) continue;

            boolean esTurno = j.idJugador() != null && j.idJugador().equals(idTurno);
            PanelJugador pj = new PanelJugador(j, i, esTurno);
            PanelManoSecundaria pms;

            switch (rivalIdx) {
                case 0 -> {
                    pj.setBounds(0, 0, 250, 80);
                    pms = new PanelManoSecundaria(j, "izquierda");
                    pms.setBounds(0, 100, 120, 400);
                    add(pj); add(pms);
                }
                case 1 -> {
                    pj.setBounds(350, 120, 250, 80);
                    pms = new PanelManoSecundaria(j, "arriba");
                    pms.setBounds(350, 0, 500, 120);
                    add(pj); add(pms);
                }
                case 2 -> {
                    pj.setBounds(920, 0, 250, 80);
                    pms = new PanelManoSecundaria(j, "derecha");
                    pms.setBounds(1070, 100, 120, 400);
                    add(pj); add(pms);
                }
            }
            rivalIdx++;
        }
        revalidate();
        repaint();
    }

    public void actualizarMazo() { panelMazo.repaint(); }
    public void actualizarDescarte() { panelDescarte.repaint(); }

    public void actualizarManos() {
        panelManoJugador.refrescarMano();
        actualizarRivales();
    }

    public void refrescarTurno() { actualizarManos(); }

    public PanelCartaSeleccionada getPanelZoom() { return panelZoom; }
    public PanelUno getPanelUno() { return panelUno; }
}
