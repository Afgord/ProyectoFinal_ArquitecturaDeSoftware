/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fachadas;

import dtos.AceptacionDTO;
import dtos.CartaDTO;
import dtos.EstadoAceptacion;
import dtos.EstadoPartidaInicialDTO;
import dtos.JugadorDTO;
import dtos.ResultadoLobbyDTO;
import dtos.ResultadoSolicitudDTO;
import entidades.Carta;
import entidades.Descarte;
import entidades.Jugador;
import entidades.Lobby;
import entidades.Mano;
import entidades.Mazo;
import entidades.Ruleta;
import entidades.Tablero;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class FachadaJuego implements FachadaDominio {

    private static final int CARTAS_INICIALES_POR_JUGADOR = 7;

    private Tablero tablero;
    private Lobby lobby;

    public FachadaJuego() {
    }

    /**
     * Constructor antiguo: instancia la fachada con un Tablero ya
     * construido (modo single-game / Ejecutador local). Se conserva por
     * compatibilidad con el flujo previo a Iniciar Partida.
     */
    public FachadaJuego(Tablero tablero) {
        this.tablero = tablero;
    }

    @Override
    public Object validarYPlay(CartaDTO carta) {
        System.out.println("[Fachada] Procesando jugada...");
        return tablero.ejecutarJugada(carta);
    }

    @Override
    public Object robarCarta() {
        System.out.println("[Fachada] Procesando robo...");
        return tablero.robarYPasar();
    }

    @Override
    public Object pasarTurno() {
        System.out.println("[Fachada] Procesando pasar turno...");
        return tablero.pasarTurno();
    }
    
    @Override
    public Object gritarUno(JugadorDTO datosGrito) {
        System.out.println("[Fachada] Procesando grito de UNO...");
        return tablero.procesarGritoUno(datosGrito);
    }

    // === Caso de uso: Iniciar Partida ===

    @Override
    public ResultadoLobbyDTO crearPartida(JugadorDTO host) {
        if (lobby != null && lobby.getEstado() != Lobby.Estado.INICIADA) {
            // Una sola partida activa por instancia de subscriptor.
            return new ResultadoLobbyDTO(false, "Ya existe un lobby activo",
                    lobby.getIdPartida(), lobby.getIdHost(), snapshotJugadores());
        }
        String idPartida = generarIdPartida();
        Jugador hostEntidad = new Jugador(host.getIdJugador(), host.getNombre(),
                host.getUrlAvatar(), new Mano());
        this.lobby = new Lobby(idPartida, hostEntidad);
        System.out.println("[Fachada] Lobby creado idPartida=" + idPartida + " host=" + host.getNombre());
        return new ResultadoLobbyDTO(true, null, idPartida, hostEntidad.getIdJugador(), snapshotJugadores());
    }

    @Override
    public ResultadoLobbyDTO unirsePartida(String idPartida, JugadorDTO jugador) {
        if (lobby == null) {
            return new ResultadoLobbyDTO(false, "No hay lobby activo", idPartida, null, new ArrayList<>());
        }
        if (!lobby.getIdPartida().equals(idPartida)) {
            return new ResultadoLobbyDTO(false, "ID de partida invalido",
                    lobby.getIdPartida(), lobby.getIdHost(), snapshotJugadores());
        }
        if (lobby.estaLleno()) {
            return new ResultadoLobbyDTO(false, "Lobby lleno",
                    lobby.getIdPartida(), lobby.getIdHost(), snapshotJugadores());
        }
        Jugador entidad = new Jugador(jugador.getIdJugador(), jugador.getNombre(),
                jugador.getUrlAvatar(), new Mano());
        boolean ok = lobby.agregar(entidad);
        if (!ok) {
            return new ResultadoLobbyDTO(false, "No se pudo unir al lobby",
                    lobby.getIdPartida(), lobby.getIdHost(), snapshotJugadores());
        }
        System.out.println("[Fachada] Jugador unido: " + jugador.getNombre()
                + " (" + lobby.totalJugadores() + "/" + Lobby.CAPACIDAD_MAXIMA + ")");
        return new ResultadoLobbyDTO(true, null, lobby.getIdPartida(),
                lobby.getIdHost(), snapshotJugadores());
    }

    @Override
    public ResultadoLobbyDTO abandonarLobby(String idJugador) {
        if (lobby == null) {
            return new ResultadoLobbyDTO(false, "No hay lobby activo", null, null, new ArrayList<>());
        }
        boolean ok = lobby.remover(idJugador);
        if (!ok) {
            return new ResultadoLobbyDTO(false, "Jugador no estaba en el lobby",
                    lobby.getIdPartida(), lobby.getIdHost(), snapshotJugadores());
        }
        System.out.println("[Fachada] Jugador abandono: " + idJugador
                + " (" + lobby.totalJugadores() + "/" + Lobby.CAPACIDAD_MAXIMA + ")");
        if (lobby.totalJugadores() == 0) {
            lobby = null;
            return new ResultadoLobbyDTO(true, null, null, null, new ArrayList<>());
        }
        return new ResultadoLobbyDTO(true, null, lobby.getIdPartida(),
                lobby.getIdHost(), snapshotJugadores());
    }

    @Override
    public ResultadoSolicitudDTO solicitarInicio(String idJugadorSolicitante) {
        if (lobby == null) {
            return new ResultadoSolicitudDTO(false, "No hay lobby activo", false,
                    null, null, new ArrayList<>(), null);
        }
        if (!lobby.contiene(idJugadorSolicitante)) {
            return new ResultadoSolicitudDTO(false, "El solicitante no esta en el lobby", false,
                    null, null, new ArrayList<>(), null);
        }
        if (lobby.totalJugadores() < 2) {
            return new ResultadoSolicitudDTO(false, "Se necesitan al menos 2 jugadores", false,
                    null, null, new ArrayList<>(), null);
        }
        lobby.iniciarSolicitud(idJugadorSolicitante);
        Jugador solicitante = lobby.getJugador(idJugadorSolicitante);
        System.out.println("[Fachada] Solicitud de inicio del jugador " + solicitante.getNombre());
        return new ResultadoSolicitudDTO(true, null, true,
                solicitante.getIdJugador(), solicitante.getNombre(),
                snapshotAceptaciones(), null);
    }

    @Override
    public ResultadoSolicitudDTO responderSolicitud(String idJugador, boolean acepta) {
        if (lobby == null) {
            return new ResultadoSolicitudDTO(false, "No hay lobby activo", false,
                    null, null, new ArrayList<>(), null);
        }
        boolean ok = lobby.responderSolicitud(idJugador, acepta);
        if (!ok) {
            return new ResultadoSolicitudDTO(false, "Respuesta invalida", false,
                    lobby.getIdJugadorSolicitante(),
                    nombreSolicitante(),
                    snapshotAceptaciones(), null);
        }
        System.out.println("[Fachada] Respuesta de " + idJugador + ": acepta=" + acepta);
        EstadoPartidaInicialDTO partida = null;
        if (lobby.todosAceptaron()) {
            partida = iniciarPartida();
        }
        return new ResultadoSolicitudDTO(true, null, false,
                lobby.getIdJugadorSolicitante(), nombreSolicitante(),
                snapshotAceptaciones(), partida);
    }

    // === Internos ===

    private EstadoPartidaInicialDTO iniciarPartida() {
        System.out.println("[Fachada] Todos aceptaron, iniciando partida " + lobby.getIdPartida());
        Mazo mazo = new Mazo(0, 9, true, true, true, true, true);
        Carta inicio = mazo.sacarCartaInicialValida();
        Descarte descarte = new Descarte(inicio);
        Ruleta ruleta = new Ruleta();

        List<Jugador> jugadoresEntidad = lobby.getJugadoresOrdenados();
        for (Jugador j : jugadoresEntidad) {
            j.setMano(new Mano());
            for (int i = 0; i < CARTAS_INICIALES_POR_JUGADOR; i++) {
                Carta c = mazo.tomarUnaCarta();
                if (c != null) j.agregarCarta(c);
            }
        }

        this.tablero = new Tablero(mazo, descarte, jugadoresEntidad, ruleta);
        lobby.marcarIniciada();

        List<JugadorDTO> jugadoresDTO = jugadoresEntidad.stream().map(this::aDTOConMano).toList();
        CartaDTO descarteDTO = new CartaDTO(inicio.getValor(), inicio.getColor());
        String idTurno = jugadoresEntidad.get(0).getIdJugador();
        return new EstadoPartidaInicialDTO(lobby.getIdPartida(), jugadoresDTO, descarteDTO, idTurno);
    }

    private List<JugadorDTO> snapshotJugadores() {
        if (lobby == null) return new ArrayList<>();
        List<JugadorDTO> out = new ArrayList<>();
        for (Jugador j : lobby.getJugadoresOrdenados()) {
            out.add(new JugadorDTO(j.getIdJugador(), j.getNombre(), j.getUrlAvatar()));
        }
        return out;
    }

    private List<AceptacionDTO> snapshotAceptaciones() {
        if (lobby == null) return new ArrayList<>();
        List<AceptacionDTO> out = new ArrayList<>();
        Map<String, EstadoAceptacion> aceptaciones = lobby.getAceptaciones();
        for (Jugador j : lobby.getJugadoresOrdenados()) {
            EstadoAceptacion estado = aceptaciones.getOrDefault(j.getIdJugador(), EstadoAceptacion.PENDIENTE);
            out.add(new AceptacionDTO(j.getIdJugador(), j.getNombre(), estado));
        }
        return out;
    }

    private String nombreSolicitante() {
        if (lobby == null || lobby.getIdJugadorSolicitante() == null) return null;
        Jugador j = lobby.getJugador(lobby.getIdJugadorSolicitante());
        return j == null ? null : j.getNombre();
    }

    private JugadorDTO aDTOConMano(Jugador j) {
        List<CartaDTO> mano = new ArrayList<>();
        for (Carta c : j.getMano().getCartasReales()) {
            mano.add(new CartaDTO(c.getValor(), c.getColor()));
        }
        return new JugadorDTO(j.getIdJugador(), j.getNombre(), mano, false, j.getUrlAvatar());
    }

    private static String generarIdPartida() {
        // 6 caracteres alfanumericos en mayusculas, estilo "ABC123".
        String alfabeto = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";
        Random r = new Random();
        StringBuilder sb = new StringBuilder(6);
        for (int i = 0; i < 6; i++) sb.append(alfabeto.charAt(r.nextInt(alfabeto.length())));
        return sb.toString();
    }
}
