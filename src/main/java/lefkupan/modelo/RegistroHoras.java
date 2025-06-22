package lefkupan.modelo;

import java.time.LocalDate;

public class RegistrosHoras{
    private LocalDate fecha;
    private double cantidad;

    public RegistrosHoras (LocalDate fecha, double cantidad){
        this.fecha = fecha;
        this.cantidad = cantidad;
    }
    public LocalDate getFecha() {
        return fecha;
    }

    public double getCantidad() {
        return cantidad;
    }
}