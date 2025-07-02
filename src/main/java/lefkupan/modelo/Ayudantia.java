package lefkupan.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//CLASE Ayudantia: representa una ayudantia donde se registran horas trabajadas.
public class Ayudantia {
    private final String nombreRamo;
    private final List<RegistroHoras> registrosHoras;

    public Ayudantia(String nombreRamo) {
        //CAMBIO: constructor con validacion.
        if (nombreRamo == null || nombreRamo.isBlank()) {
            throw new IllegalArgumentException("Nombre de ramo no puede estar vacio");
        }
        this.nombreRamo = nombreRamo;
        this.registrosHoras = new ArrayList<>();
    }

    public void agregarHoras(double cantidadHoras) {
        //CAMBIO: Sobrecarga de metodos para agregar horas, se reutilizo codigo para permitir agregar con o sin fecha.
        agregarHoras(LocalDate.now(), cantidadHoras);
    }

    public void agregarHoras(LocalDate fecha, double cantidadHoras) {
        RegistroHoras nuevoRegistro = new RegistroHoras(fecha, cantidadHoras);
        registrosHoras.add(nuevoRegistro);
    }

    public boolean eliminarRegistro(LocalDate fecha, double cantidad) {
        return registrosHoras.removeIf(r -> r.getFecha().equals(fecha) && r.getCantidad() == cantidad);
    }

    public double getTotalHoras() {
        return registrosHoras.stream().mapToDouble(RegistroHoras::getCantidad).sum();
    }

    public String getNombreRamo() {
        return nombreRamo;
    }

    public List<RegistroHoras> getRegistrosHoras() {
        //CAMBIO: encapsulamiento.
        return new ArrayList<>(registrosHoras);
    }

    @Override
    //CAMBIO: implementacion de equals/hashCode por nombre de ramo.
    public String toString() {
        return nombreRamo + " - Total: " + getTotalHoras() + " hrs";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof Ayudantia that) {
            return Objects.equals(nombreRamo, that.nombreRamo);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombreRamo);
    }
}