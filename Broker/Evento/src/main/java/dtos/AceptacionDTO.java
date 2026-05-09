package dtos;

import java.io.Serializable;

public class AceptacionDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String idJugador;
    private final String nombre;
    private EstadoAceptacion estado;

    public AceptacionDTO(String idJugador, String nombre, EstadoAceptacion estado) {
        this.idJugador = idJugador;
        this.nombre = nombre;
        this.estado = estado;
    }

    public String getIdJugador() { return idJugador; }
    public String getNombre() { return nombre; }
    public EstadoAceptacion getEstado() { return estado; }
    public void setEstado(EstadoAceptacion estado) { this.estado = estado; }
}
