package ContractsLobby;

/**
 * Contrato para las vistas del Lobby.
 * Permite que las pantallas de registro y espera reaccionen 
 * cuando un jugador se une o cambia su estado.
 */
public interface IObserverLobby {
    void update(ILobbyReadModel lobby);
}
