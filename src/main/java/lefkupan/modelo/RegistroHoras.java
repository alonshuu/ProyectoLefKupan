package lefkupan.modelo;

import java.time.LocalDate;
import java.util.Objects;

//CLASE RegistroHoras: representa un registro de horas en una fecha especifica.
public class RegistroHoras {
    //CAMBIO: atributos marcados como final para asegurar inmutabilidad.
    private final LocalDate fecha;
    private final double cantidad;

    public RegistroHoras(LocalDate fecha, double cantidad) {
        //CAMBIO: constructor validado, para evitar crear registros con datos invalidos o vacios.
        if(fecha == null){
            throw new IllegalArgumentException("La fecha no puede ser nula");
        }
        if (cantidad <= 0) {
            throw new IllegalArgumentException("Horas invalidas, debe ser mayor a 0");
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
    //CAMBIO: uso estandar del toString para imprimir info clara.
    public String toString() {
        return "Registro: " + fecha + " - " + cantidad + " hrs";
    }

    @Override
    //CAMBIO: implementacion de equals y hashCode, para comparacion logica util para eliminaciones o busquedas.
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RegistroHoras)) return false;
        RegistroHoras that = (RegistroHoras) o;
        return Double.compare(that.cantidad, cantidad) == 0 &&
                Objects.equals(fecha, that.fecha);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fecha, cantidad);
    }

}
