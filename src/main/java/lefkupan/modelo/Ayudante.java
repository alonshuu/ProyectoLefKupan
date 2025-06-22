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
        if (cantidad<=0) {
            throw new IllegalArgumentException("Cantidad de horas invalidas");
        }
        Ayudantia ayudantia = ayudantias.stream()
                .filter(a -> a.getNombreRamo().equalsIgnoreCase(ramo))
                .findFirst()
                .orElse(null);

        if (ayudantia == null) {
            ayudantia = new Ayudantia(ramo);
            ayudantias.add(ayudantia);
        }

        ayudantia.agregarHoras(cantidad);
        horasTrabajadas += cantidad;
    }

    public double calcularPago(double valorPorHora) {
        return horasTrabajadas * valorPorHora;
    }

    @Override
    public String toString(){
        return "Ayudante{"+ "matricula= " + matricula + ", horas= " + horasTrabajadas + "}";
    }
}
