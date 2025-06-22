package lefkupan.modelo;

import java.time.LocalDate;

public class RegistroHoras {
    private LocalDate fecha;
    private double cantidad;

    public RegistroHoras(LocalDate fecha, double cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
        }
        this.fecha = fecha;
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
        return fecha + " : " + cantidad + "hrs";
    }
}
