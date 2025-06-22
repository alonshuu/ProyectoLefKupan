package lefkupan.controlador;

import lefkupan.modelo.Ayudante;
import lefkupan.modelo.HistorialTxt;

public class ControladorHoras {
    private Ayudante ayudante;

    public ControladorHoras(Ayudante ayudante) {
        this.ayudante = ayudante;
    }

    public void registrarHoras(String ramo, double cantidad) {
        try {
            ayudante.registrarHoras(ramo, cantidad);
            HistorialTxt.guardarRegistro(ayudante, ramo, cantidad);
            System.out.println("Horas registradas correctamente.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void mostrarResumen(double valorPorHora) {
        System.out.println("==================================");
        System.out.println("        RESUMEN DE HORAS           ");
        System.out.println("==================================");
        System.out.println("Total de horas trabajadas: " + ayudante.getHorasTrabajadas());
        System.out.println("Pago total estimado: $" + ayudante.calcularPago(valorPorHora));
        System.out.println("----------------------------------");

        for (var ayudantia : ayudante.getAyudantias()) {
            System.out.println("Ramo: " + ayudantia.getNombreRamo());
            for (var registro : ayudantia.getRegistrosHoras()) {
                System.out.println("  - " + registro);
            }
        }
    }

    public Ayudante getAyudante() {
        return ayudante;
    }
}
