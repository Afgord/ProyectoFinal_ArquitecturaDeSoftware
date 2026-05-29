package Ejercer_Turno.MVC.PanelesVista;

import Ejercer_Turno.Interfaces.IModeloDatos;
import Ejercer_Turno.MVC.ControlJuego;
import Ejercer_Turno.MVC.FrameTablero;
import Ejercer_Turno.MVC.UtilCarta;
import dtos.CartaDTO;
import dtos.JugadorDTO;
import java.awt.Frame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Mano del jugador local.
 *
 * Un clic selecciona la carta; un segundo clic (o doble clic) la juega.
 * La vista solo se actualiza cuando DominioSuscriptor responde por la red.
 */
public class PanelMano extends JPanel {
    private final ControlJuego control;
    private final IModeloDatos modeloJuego;
    private PanelCarta cartaSeleccionadaVista;

    public PanelMano(ControlJuego control, IModeloDatos modeloJuego) {
        this.control = control;
        this.modeloJuego = modeloJuego;
        setLayout(null);
        setOpaque(false);
    }

    public void refrescarMano() {
        removeAll();
        cartaSeleccionadaVista = null;

        JugadorDTO local = jugadorLocal();
        if (local == null) {
            limpiarZoom();
            revalidate();
            repaint();
            return;
        }

        List<CartaDTO> cartas = local.getMano();
        if (cartas == null || cartas.isEmpty()) {
            limpiarZoom();
            revalidate();
            repaint();
            return;
        }

        int n = cartas.size();
        int anchoPanel = getWidth() > 0 ? getWidth() : 800;
        int altoPanel = getHeight() > 0 ? getHeight() : 150;
        int anchoCarta = 100;
        int altoCarta = 140;
        int y = (altoPanel - altoCarta) / 2;
        int espacio = (n == 1) ? 0 : Math.min(70, (anchoPanel - anchoCarta) / (n - 1));
        int x = (n == 1) ? (anchoPanel - anchoCarta) / 2 : (anchoPanel - ((n - 1) * espacio + anchoCarta)) / 2;

        for (CartaDTO c : cartas) {
            PanelCarta pCarta = new PanelCarta(c, control);
            pCarta.setBounds(x, y, anchoCarta, altoCarta);
            configurarEventoCarta(pCarta);
            add(pCarta, 0);
            x += espacio;
        }

        revalidate();
        repaint();
    }

    private JugadorDTO jugadorLocal() {
        String idLocal = modeloJuego.getIdJugadorLocal();
        if (idLocal == null) return null;
        for (JugadorDTO j : modeloJuego.getJugadoresDTO()) {
            if (idLocal.equals(j.idJugador())) return j;
        }
        return null;
    }

    private boolean esMiTurno() {
        String idLocal = modeloJuego.getIdJugadorLocal();
        String idTurno = modeloJuego.getIdJugadorTurnoActual();
        return idLocal != null && idLocal.equals(idTurno);
    }

    private void configurarEventoCarta(PanelCarta pCarta) {
        pCarta.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() >= 2) {
                    intentarLanzar(pCarta.getModelo());
                    return;
                }

                if (cartaSeleccionadaVista != null
                        && cartaSeleccionadaVista.getModelo().equals(pCarta.getModelo())) {
                    intentarLanzar(pCarta.getModelo());
                    return;
                }

                if (cartaSeleccionadaVista != null) {
                    cartaSeleccionadaVista.setSeleccionada(false);
                }
                cartaSeleccionadaVista = pCarta;
                cartaSeleccionadaVista.setSeleccionada(true);
                mostrarZoom(pCarta.getModelo());
            }
        });
    }

    private void mostrarZoom(CartaDTO modelo) {
        java.awt.Window ventana = SwingUtilities.getWindowAncestor(this);
        if (ventana instanceof FrameTablero frame) {
            frame.getPanelTablero().getPanelZoom().mostrarCarta(modelo);
            frame.getPanelTablero().mostrarMensaje("Doble clic (o clic otra vez) para jugar");
        }
    }

    private void limpiarZoom() {
        java.awt.Window ventana = SwingUtilities.getWindowAncestor(this);
        if (ventana instanceof FrameTablero frame) {
            frame.getPanelTablero().getPanelZoom().limpiar();
        }
    }

    private void intentarLanzar(CartaDTO modeloCarta) {
        if (!esMiTurno()) {
            java.awt.Window ventana = SwingUtilities.getWindowAncestor(this);
            if (ventana instanceof FrameTablero frame) {
                frame.getPanelTablero().mostrarMensaje("No es tu turno");
            }
            return;
        }

        if (UtilCarta.esComodin(modeloCarta.getValor())) {
            Frame padre = (Frame) SwingUtilities.getWindowAncestor(this);
            control.solicitarSeleccionColor(modeloCarta, padre);
        } else {
            control.solicitarTirarCarta(modeloCarta);
        }

        cartaSeleccionadaVista = null;
        limpiarZoom();
        repaint();
    }
}
