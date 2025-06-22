package lefkupan.vista;

import lefkupan.controlador.ControladorHoras;
import lefkupan.modelo.Ayudante;
import lefkupan.modelo.Ayudantia;
import lefkupan.modelo.RegistroHoras;

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
        acciones.put(3, this::eliminarAyudantia);
        acciones.put(4, this::verAyudantias);
        acciones.put(5, this::verHistorialPorAyudantia);
        acciones.put(0, this::salir);
    }
    public void mostrarOpciones(){
        System.out.println("------------------");
        System.out.println("       MENÚ       ");
        System.out.println("------------------");
        System.out.println("1. Registrar horas trabajadas");
        System.out.println("2. Ver pago estimado");
        System.out.println("3. Eliminar ayudantía");
        System.out.println("4. Ver ayudantías registradas");
        System.out.println("5. Ver historial detallado por ayudantía");
        System.out.println("0. Salir");
    }
    public int leerOpcion() {
        System.out.print("Seleccione una opción: ");
        while (!scanner.hasNextInt()) {
            System.out.println("Por favor ingrese un número válido.");
            scanner.next();
    }
        int opcion = scanner.nextInt();
        scanner.nextLine();
        return opcion;
   }
   public void procesarOpcion(int opcion){
        Runnable accion = acciones.get(opcion);
        if (accion !=null){
            accion.run();
        } else {
            System.out.println("Opción inválida. Intente nuevamente.");
        }
   }
    public void iniciar(){
        int opcion;
        do {
            mostrarOpciones();
            opcion = leerOpcion();
            procesarOpcion(opcion);
        } while (opcion != 0);
    }
    private void registrarHoras() {
        System.out.print("Ingrese nombre del ramo: ");
        String ramo = scanner.nextLine();
        System.out.print("Ingrese cantidad de horas: ");
        double horas = scanner.nextDouble();
        scanner.nextLine();
        controladorHoras.registrarHoras(ayudante, ramo, horas);
        System.out.println("Horas registradas correctamente.");
    }
    private void verPagoEstimado() {
        System.out.print("Ingrese valor por hora: ");
        double valor = scanner.nextDouble();
        scanner.nextLine();
        double total = controladorHoras.calcularPago(ayudante, valor);
        System.out.println("Pago estimado: $" + total);
    }
    private void salir() {
        System.out.println("Saliendo del sistema...");
    }
    private void eliminarAyudantia() {
        System.out.print("Ingrese el nombre del ramo que desea eliminar: ");
        String ramo = scanner.nextLine().trim();

        boolean eliminado = ayudante.eliminarAyudantia(ramo);

        if (eliminado) {
            controladorHoras.registrarHoras(ayudante, "", 0);
            System.out.println("Ayudantía eliminada correctamente.");
        } else {
            System.out.println("No se encontró una ayudantía con ese nombre.");
        }
    }
    private void verAyudantias() {
        List<Ayudantia> lista = ayudante.getAyudantias();

        if (lista.isEmpty()) {
            System.out.println("No tienes ayudantías registradas.");
            return;
        }

        System.out.println("Tus ayudantías registradas:");
        for (Ayudantia a : lista) {
            System.out.println("- " + a.getNombreRamo() + " (Total horas: " + a.getTotalHoras() + ")");
        }
    }
    private void verHistorialPorAyudantia() {
        List<Ayudantia> lista = ayudante.getAyudantias();
        if (lista.isEmpty()) {
            System.out.println("No tienes ayudantías registradas.");
            return;
        }

        for (Ayudantia a : lista) {
            System.out.println("Ramo: " + a.getNombreRamo());
            List<RegistroHoras> registros = a.getRegistrosHoras();
            if (registros.isEmpty()) {
                System.out.println("  Sin registros.");
            } else {
                for (RegistroHoras rh : registros) {
                    System.out.println("  Fecha: " + rh.getFecha() + " - Horas: " + rh.getCantidad());
                }
            }
            System.out.println("----------------------------------");
        }
    }

}