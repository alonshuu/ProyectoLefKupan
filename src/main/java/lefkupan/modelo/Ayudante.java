package lefkupan.modelo;

import java.util.ArrayList;
import java.util.List;

public class Ayudante {
    private String matricula;
    private String contrasena;
    private double horasTrabajadas;
    private List<Ayudantia> ayudantias;

    public Ayudante(String matricula, String contrasena){
        this.matricula = matricula;
        this.contrasena = contrasena;
        this.horasTrabajadas = 0;
        this.ayudantias = new ArrayList<>();
    }

    public String getMatricula(){
        return matricula;
    }

    public String getContrasena(){
        return "****";
    }

    public double getHorasTrabajadas() {
        return horasTrabajadas;
    }

    public List<Ayudantia> getAyudantias() {
        return ayudantias;
    }

    public void agregarAyudantia(Ayudantia ayudantia) {
        ayudantias.add(ayudantia);
    }

    public void registrarHoras(String ramo, double cantidad) {
        if (cantidad <= 0) return;

        Ayudantia ayudantia = obtenerOcrearAyudantia(ramo);
        ayudantia.agregarHoras(cantidad);
        horasTrabajadas += cantidad;
    }

    private Ayudantia obtenerOcrearAyudantia(String ramo) {
        return ayudantias.stream()
                .filter(a -> a.getNombreRamo().equalsIgnoreCase(ramo))
                .findFirst()
                .orElseGet(() -> {
                    Ayudantia nueva = new Ayudantia(ramo);
                    ayudantias.add(nueva);
                    return nueva;
                });
    }

    public double calcularPago(double valorPorHora) {
        return horasTrabajadas * valorPorHora;
    }

    public boolean eliminarAyudantia(String nombreRamo) {
        Ayudantia ayudantia = obtenerAyudantia(nombreRamo);
        if (ayudantia == null) return false;

        horasTrabajadas -= ayudantia.getTotalHoras();
        return ayudantias.remove(ayudantia);
    }

    private Ayudantia obtenerAyudantia(String nombreRamo) {
        return ayudantias.stream()
                .filter(a -> a.getNombreRamo().equalsIgnoreCase(nombreRamo))
                .findFirst()
                .orElse(null);
    }

    public void recalcularHorasTotales() {
        this.horasTrabajadas = ayudantias.stream()
                .mapToDouble(Ayudantia::getTotalHoras)
                .sum();
    }

    @Override
    public String toString(){
        return "Ayudante{"+ "matricula= " + matricula + ", horas= " + horasTrabajadas + "}";
    }
}

