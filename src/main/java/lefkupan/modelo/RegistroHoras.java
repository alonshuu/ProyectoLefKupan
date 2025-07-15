package lefkupan.modelo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class RegistroHoras { //registro de horas trabajadas en una fecha específica
    private final LocalDate fecha;
    private final double cantidad;
    private final TipoActividad tipoActividad; //CAMBIO: uso de enum en vez de String

    public RegistroHoras(LocalDate fecha, double cantidad, TipoActividad tipoActividad) { //constructor de RegistroHoras
        if (cantidad <= 0) {
            throw new IllegalArgumentException("Hora invalida");
        }
        this.fecha = fecha;
        this.cantidad = cantidad;
        this.tipoActividad = tipoActividad;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public double getCantidad() {
        return cantidad;
    }

    public TipoActividad getTipoActividad() {
        return tipoActividad;
    }

    @Override
    public String toString() {
        return String.format("Fecha: %s | Horas: %.2f | Actividad: %s",
                fecha.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                cantidad,
                tipoActividad.name());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof RegistroHoras other)) return false;
        return cantidad == other.cantidad &&
                Objects.equals(fecha, other.fecha) &&
                tipoActividad == other.tipoActividad;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fecha, cantidad,tipoActividad);
    }
}
