package lefkupan.controlador;

import lefkupan.modelo.Ayudante;
import lefkupan.modelo.BaseDatos;
import java.util.List;

public class ControladorHoras {
    private List<Ayudante> listaAyudantes;

    public ControladorHoras(List<Ayudante> listaAyudantes) {
        this.listaAyudantes = listaAyudantes;
    }
    public void registrarHoras(Ayudante ayudante, String ramo, double cantidad) {
        ayudante.registrarHoras(ramo, cantidad);
        BaseDatos.guardarAyudantes(listaAyudantes);
    }
    public double calcularPago(Ayudante ayudante, double valorHora) {
        return ayudante.calcularPago(valorHora);
    }
}