package lefkupan.vista;

import lefkupan.controlador.ControladorHoras;
import lefkupan.controlador.ControladorLogin;
import lefkupan.modelo.Ayudante;
import lefkupan.modelo.HistorialTxt;

import java.util.Scanner;

public class Menu {
    private final Scanner scanner;
    private Ayudante ayudanteActual;
    private ControladorHoras controladorHoras;

    public Menu() {
        scanner = new Scanner(System.in);
    }

    public void mostrarMenuPrincipal() {
        login();
        HistorialTxt.cargarHistorial(ayudanteActual);

        boolean salir = false;
        while (!salir) {
            imprimirMenu();
            int opcion = leerOpcion();
            salir = ejecutarOpcion(opcion);
        }
    }

    private void login() {
        System.out.println("=== LOGIN DE AYUDANTE ===");

        while (true) {
            System.out.print("Ingrese matricula: ");
            String matricula = scanner.nextLine();

            System.out.print("Ingrese contraseña: ");
            String contrasena = scanner.nextLine();

            Ayudante ayudante = ControladorLogin.autenticar(matricula, contrasena);

            if (ayudante != null) {
                System.out.println("Inicio de sesión exitoso!");
                ayudanteActual = ayudante;
                controladorHoras = new ControladorHoras(ayudante);
                break;
            } else {
                System.out.println("Error. Intente de nuevo");
            }
        }
    }

    private void imprimirMenu() {
        System.out.println("\n===============================");
        System.out.println(" SISTEMA DE REGISTRO DE HORAS ");
        System.out.println("===============================");
        System.out.println("1. Registrar horas trabajadas");
        System.out.println("2. Ver resumen de horas y pago");
        System.out.println("3. Eliminar ayudantia");
        System.out.println("4. Eliminar un registro especifico (fecha y ramo): ");
        System.out.println("5. Salir");
        System.out.print("Selecciona una opcion: ");
    }

    private int leerOpcion() {
        int opcion;
        try {
            opcion = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            opcion = -1;
        }
        return opcion;
    }

    private boolean ejecutarOpcion(int opcion) {
        switch (opcion) {
            case 1:
                registrarHoras();
                break;

            case 2:
                mostrarResumen();
                break;

            case 3:
                eliminarAyudantia();
                break;

            case 4:
                eliminarRegistroEspecifico();
                break;

            case 5: {
                System.out.println("Adios");
                return true;
            }
            default:
                System.out.println("Opcion invalida. Intente de nuevo.");
        }
        return false;
    }

    private void registrarHoras() {
        System.out.print("Ingrese el nombre del ramo: ");
        String ramo = scanner.nextLine();

        System.out.print("Ingrese la cantidad de horas: ");
        double horas;
        try {
            horas = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Debe ingresar un numero.");
            return;
        }

        controladorHoras.registrarHoras(ramo, horas);
    }

    private void mostrarResumen() {
        System.out.print("Ingrese el valor por hora: ");
        double valor;
        try {
            valor = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Entrada invalida.");
            return;
        }

        controladorHoras.mostrarResumen(valor);
    }

    private void eliminarAyudantia() {
        System.out.print("Ingrese el nombre del ramo: ");
        String ramo = scanner.nextLine();

        boolean eliminada = ayudanteActual.eliminarAyudantia(ramo);

        if (eliminada) {
            HistorialTxt.eliminarAyudantiaDelArchivo(ayudanteActual, ramo);
            ayudanteActual.recalcularHorasTotales();
            System.out.println("Ayudantia eliminada");
            System.out.print("Ingrese el valor por hora: ");
            double valor;
            try {
                valor = Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Entrada invalida");
                valor = 0;
            }
            controladorHoras.mostrarResumen(valor);
        } else {
            System.out.println("No se encontro la ayudantia");
        }
    }

    private void eliminarRegistroEspecifico() {
        System.out.print("Ingrese el nombre del ramo: ");
        String ramo = scanner.nextLine();

        var ayudantia = ayudanteActual.getAyudantias().stream()
                .filter(a -> a.getNombreRamo().equalsIgnoreCase(ramo))
                .findFirst()
                .orElse(null);

        if (ayudantia == null) {
            System.out.println("No se encontro ayudantia");
            return;
        }

        var registros = ayudantia.getRegistrosHoras();
        if (registros.isEmpty()) {
            System.out.println("No hay registros");
            return;
        }

        System.out.println("Registros disponibles: ");
        for (int i = 0; i < registros.size(); i++) {
            System.out.println("[" + (i + 1) + "] " + registros.get(i));
        }

        System.out.print("Ingrese el numero del registro que desea eliminar: ");
        int seleccion;
        try {
            seleccion = Integer.parseInt(scanner.nextLine()) - 1;
        } catch (NumberFormatException e) {
            System.out.println("Entrada invalida.");
            return;
        }

        if (seleccion < 0 || seleccion >= registros.size()) {
            System.out.println("Error");
            return;
        }

        var registroEliminado = registros.remove(seleccion);
        ayudanteActual.recalcularHorasTotales();

        HistorialTxt.eliminarRegistroDelArchivo(ayudanteActual, ramo, registroEliminado);

        System.out.println("Ayudantia eliminada");

    }
}
