package lefkupan.modelo;

import java.time.LocalDate;

public class RegistroHoras {
    private LocalDate fecha;
    private double cantidad;

    public RegistroHoras(LocalDate fecha, double cantidad) {
        this.fecha = fecha;
        this.cantidad = cantidad;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public double getCantidad() {
        return cantidad;
    }

    public String toString() {
        return fecha + " : " + cantidad + "hrs";
    }
}
