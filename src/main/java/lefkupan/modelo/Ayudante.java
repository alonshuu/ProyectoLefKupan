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
        return contrasena;
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

    public void registrarHoras(String ramo, double cantidad){
        Ayudantia encontrada = null;
        for (Ayudantia ayudantia : ayudantias){
            if (ayudantia.getNombreRamo().equalsIgnoreCase(ramo)){
                encontrada = ayudantia;
                break;
            }
        }
        if (encontrada == null) {
            encontrada = new Ayudantia(ramo);
            ayudantias.add(encontrada);
        }
        encontrada.agregarHoras(cantidad);
        horasTrabajadas += cantidad;
    }
    public double calcularPago(double valorPorHora) {
        return horasTrabajadas * valorPorHora;
    }
}
