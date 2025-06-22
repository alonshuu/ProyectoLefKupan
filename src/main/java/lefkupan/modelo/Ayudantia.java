package lefkupan.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Ayudantia{
    private String nombreRamo;
    private List<RegistroHoras> registrosHoras;

    public Ayudantia(String nombreRamo){
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
        if (cantidad <= 0){
            throw new IllegalArgumentException("Horas invalidas");
        }
        RegistroHoras registro = new RegistroHoras(LocalDate.now(), cantidad);
        registrosHoras.add(registro);
    }
    public double getTotalHoras() {
        return registrosHoras.stream().mapToDouble(RegistroHoras::getCantidad).sum();
    }

    @Override
    public String toString(){
        return nombreRamo + " : " + getTotalHoras() + " hrs";
    }
}