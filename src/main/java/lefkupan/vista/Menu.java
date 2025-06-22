package lefkupan.vista;

import lefkupan.controlador.ControladorHoras;
import lefkupan.modelo.Ayudante;

import java.util.*;

public class Menu {
    private final Scanner scanner = new Scanner(System.in);
    private final Ayudante ayudante;
    private final ControladorHoras controladorHoras;
    private final Map<Integer, Runnable> acciones = new HashMap<>();

    public Menu(Ayudante ayudante, ControladorHoras controladorHoras) {
        this.ayudante = ayudante;
        this.controladorHoras = controladorHoras;

        acciones.put(1, this::registrarHoras);
        acciones.put(2, this::verPagoEstimado);
        acciones.put(0, this::salir);
    }
    public void
}