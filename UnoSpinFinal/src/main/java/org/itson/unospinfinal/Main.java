package org.itson.unospinfinal;

import MVCJugarTurno.MatchModel;
import MVCJugarTurno.MatchController;
import MVCJugarTurno.MatchView;
import Dominio.Player;
import Dominio.GameConfiguration;
import MVCLobby.LobbyModel;
import MVCLobby.LobbyController;
import MVCLobby.LobbyView;
import ContractsLobby.IObserverLobby;
import ContractsLobby.ILobbyReadModel;

import javax.swing.SwingUtilities;
import java.util.List;

/**
 * CLASE MAESTRA DE ENSAMBLADO.
 * Coordina la transición clínica entre el Lobby y el Juego Activo.
 */
public class Main {
    
    public static void main(String[] args) {
        // 1. Inicialización del Módulo de Lobby (Pre-juego)
        LobbyModel lobbyModel = new LobbyModel();
        LobbyController lobbyController = new LobbyController(lobbyModel);

        // 2. Observador de Transición Clínica
        lobbyModel.subscribe(new IObserverLobby() {
            private boolean gameStarted = false;

            @Override
            public void update(ILobbyReadModel lobby) {
                if (lobby.isMatchStarting() && !gameStarted) {
                    gameStarted = true;
                    iniciarCicloDeJuego(lobbyModel);
                }
            }
        });

        // 3. Lanzamiento del Lobby (3 ventanas para prueba en una PC)
        SwingUtilities.invokeLater(() -> {
            for (int i = 0; i < 3; i++) {
                LobbyView lobbyWindow = new LobbyView(lobbyController, lobbyModel);
                lobbyWindow.setLocation(50 + (i * 420), 50);
                lobbyWindow.setVisible(true);
            }
        });
    }

    /**
     * MÉTODO DE TRANSICIÓN: Mapea los datos del Lobby al Motor de Juego Real.
     */
    private static void iniciarCicloDeJuego(LobbyModel lobbyModel) {
        System.out.println("[SISTEMA]: Iniciando transferencia de estado al motor de juego...");

        // A. Extraer configuración y jugadores validados del Lobby
        GameConfiguration config = lobbyModel.getFinalConfig();
        List<Player> playersFromLobby = lobbyModel.getJoinedPlayers();

        // B. Instanciar el Corazón del Juego
        MatchModel matchModel = new MatchModel("partida-real-001", config);
        MatchController matchController = new MatchController(matchModel);

        // C. Sincronización Clínica de Jugadores
        for (int i = 0; i < playersFromLobby.size(); i++) {
            Player p = playersFromLobby.get(i);
            // 1. Registrar en el nuevo modelo
            matchModel.addPlayerToMatch(p.getName(), p.getAvatarPath(), p.getRepresentativeColor());
            
            // 2. CORRECCIÓN CRÍTICA: Obtener el nuevo ID generado y marcar como LISTO
            // Sin este paso, el MatchModel rechaza el comando startMatch()
            String newId = matchModel.getPlayer(i).getId();
            matchModel.togglePlayerReady(newId);
        }

        // D. Activar el Motor de Turnos (Ahora sí cumplirá el quórum)
        matchModel.startMatch();

        // E. Desplegar las Vistas de Juego
        SwingUtilities.invokeLater(() -> {
            int xOffset = 50;
            for (int i = 0; i < matchModel.getPlayerCount(); i++) {
                Player p = matchModel.getPlayer(i);
                MatchView gameView = new MatchView(p.getId(), matchController, matchModel);
                gameView.setLocation(xOffset, 150);
                gameView.setVisible(true);
                
                // Forzar primer renderizado
                gameView.update(matchModel);
                xOffset += 320;
            }
            System.out.println("[SISTEMA]: Partida iniciada con éxito. Repartiendo cartas...");
        });
    }
}
