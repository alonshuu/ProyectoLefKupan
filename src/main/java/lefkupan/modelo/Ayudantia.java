package lefkupan.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Ayudantia{ //clase que representa una ayudantía con múltiples registros de horas
    //CAMBIO: se agrego final
    private final String nombreRamo;
    private final List<RegistroHoras> registrosHoras;

    public Ayudantia(String nombreRamo){ //constructor de Ayudantia
        if (nombreRamo == null || nombreRamo.isBlank()) { //validacion de campo obligatorio
            throw new IllegalArgumentException("El nombre del ramo no puede estar vacio");
        }
        this.nombreRamo = nombreRamo;
        this.registrosHoras = new ArrayList<>();
    }

    public void agregarHoras(LocalDate fecha, double cantidad, TipoActividad tipoActividad) { //agrega un nuevo registro de horas
        if (cantidad <= 0) {
            throw new IllegalArgumentException("Horas inválidas");
        }
        registrosHoras.add(new RegistroHoras(fecha, cantidad, tipoActividad));
    }

    public void agregarHoras(double cantidad) {
        //CAMBIO: metodo restaurado con valor por defecto AYUDANTIA
        agregarHoras(LocalDate.now(), cantidad, TipoActividad.AYUDANTIA);
    }

    public boolean eliminarRegistro(LocalDate fecha, double cantidad) { //elimina un registro de horas especifico por fecha y cantidad
        Iterator<RegistroHoras> it = registrosHoras.iterator();
        while (it.hasNext()) {
            RegistroHoras registrohoras= it.next();
            if (registrohoras.getFecha().isEqual(fecha) && registrohoras.getCantidad() == cantidad) {
                it.remove();
                return true;
            }
        }
        return false;
    }

    public double getTotalHoras() { //obtiene el total de horas registradas en esta ayudantia
        return registrosHoras.stream()
                .mapToDouble(RegistroHoras::getCantidad).
                sum();
    }

    public String getNombreRamo(){
        return nombreRamo;
    }

    public List<RegistroHoras> getRegistrosHoras(){
        return registrosHoras;
    }

    @Override
    public String toString(){
        return String.format("Ramo: %s | Total horas: %.2f", nombreRamo, getTotalHoras());
    }
}