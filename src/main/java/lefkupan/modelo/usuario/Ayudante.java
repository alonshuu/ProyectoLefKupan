package lefkupan.modelo.usuario;

import lefkupan.modelo.dominio.Ayudantia;

import java.util.ArrayList;
import java.util.List;

public class Ayudante { //representa un ayudante con su información de acceso y registros
    //CAMBIO: se agrego final
    private final String matricula;
    private final List<Ayudantia> ayudantias;

    public Ayudante(String matricula, String contrasena){ //constructor de ayudante
        // CAMBIO: validaciones básicas
        if(matricula == null || matricula.isBlank()){
            throw new IllegalArgumentException("El matricula es obligatoria");
        }
        this.matricula = matricula;
        this.ayudantias = new ArrayList<>();
    }

    public void agregarAyudantia(Ayudantia ayudantia) { // Agrega una nueva ayudantía al ayudante
        ayudantias.add(ayudantia);
    }

    public boolean eliminarAyudantia(String nombreRamo) { //elimina una ayudantia especifica por nombre
        return ayudantias.removeIf(ayudantia -> ayudantia.getNombreRamo().equalsIgnoreCase(nombreRamo));
    }

    public double getHorasTotales() { //obtiene el total de horas trabajadas entre todas las ayudantias
        return ayudantias.stream()
                .mapToDouble(Ayudantia::getTotalHoras)
                .sum();
    }

    public double calcularPago(double valorHora) { //calcula el pago total estimado
        return getHorasTotales() * valorHora;
    }

    public String getMatricula(){
        return matricula;
    }


    public List<Ayudantia> getAyudantias() {
        return ayudantias;
    }

    @Override
    public String toString(){
        return String.format("Ayudante: %s | Total horas: %.2f",  matricula, getHorasTotales());
    }
}

