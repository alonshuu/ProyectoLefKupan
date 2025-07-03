package lefkupan.modelo;

import java.time.LocalDate;
import java.util.Objects;

public class RegistroHoras {
    private LocalDate fecha;
    private double cantidad;

    public RegistroHoras(LocalDate fecha, double cantidad) {
        this.fecha = Objects.requireNonNull(fecha,"La fecha no puede ser nula");
        if (cantidad <= 0) {
            throw new IllegalArgumentException("Horas invalidas");
        }
        this.cantidad = cantidad;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public double getCantidad() {
        return cantidad;
    }

    @Override
    public String toString() {
        return fecha + ": " + cantidad + " hrs";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RegistroHoras)) return false;
        RegistroHoras that = (RegistroHoras) o;
        return Double.compare(that.cantidad, cantidad) == 0 &&
                fecha.equals(that.fecha);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fecha, cantidad);
    }
}
