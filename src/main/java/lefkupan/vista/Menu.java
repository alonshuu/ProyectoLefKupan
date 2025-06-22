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
    public void mostrarOpciones(){
        System.out.println("------------------");
        System.out.println("       MENÚ       ");
        System.out.println("------------------");
        System.out.println("1. Registrar horas trabajadas");
        System.out.println("2. Ver pago estimado");
        System.out.println("0. Salir");
    }
    public int leerOpcion() {
        System.out.print("Seleccione una opción: ");
        while (!scanner.hasNextInt()) {
            System.out.println("Por favor ingrese un número válido.");
            scanner.next();
    }
        int opcion = scanner.nextInt();;
        scanner.next();
        return opcion;
}