package lefkupan.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Ayudantia{
    private String nombreRamo;
    private List<RegistroHoras> registrosHoras;

    public Ayudantia(String nombreRamo){
        if (nombreRamo == null || nombreRamo.isBlank()) {
            throw new IllegalArgumentException("Nombre de ramo no puede estar vacio");
        }
        this.nombreRamo = nombreRamo;
        this.registrosHoras = new ArrayList<>();
    }

    public String getNombreRamo(){
        return nombreRamo;
    }

    public List<RegistroHoras> getRegistrosHoras(){
        return registrosHoras;
    }

    public void agregarHoras(double cantidad){
        agregarHoras(LocalDate.now(), cantidad);
    }

    public void agregarHoras(LocalDate fecha, double cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("Horas inválidas");
        }
        registrosHoras.add(new RegistroHoras(fecha, cantidad));
    }

    public double getTotalHoras() {
        return registrosHoras.stream()
                .mapToDouble(RegistroHoras::getCantidad).
                sum();
    }

    public boolean eliminarRegistro(LocalDate fecha, double cantidad) {
        return registrosHoras.removeIf(r ->
                r.getFecha().equals(fecha) && r.getCantidad() == cantidad);
    }


    @Override
    public String toString(){
        return nombreRamo + " : " + getTotalHoras() + " hrs";
    }
}