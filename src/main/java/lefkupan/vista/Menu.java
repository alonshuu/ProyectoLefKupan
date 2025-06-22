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
            System.out.print("Ingrese matrícula: ");
            String matricula = scanner.nextLine();

            System.out.print("Ingrese contraseña: ");
            String contrasena = scanner.nextLine();

            Ayudante ayudante = ControladorLogin.autenticar(matricula, contrasena);

            if (ayudante != null) {
                System.out.println("¡Inicio de sesión exitoso!");
                ayudanteActual = ayudante;
                controladorHoras = new ControladorHoras(ayudante);
                break;
            } else {
                System.out.println("Credenciales inválidas. Intente de nuevo.");
            }
        }
    }

    private void imprimirMenu() {
        System.out.println("\n===============================");
        System.out.println(" SISTEMA DE REGISTRO DE HORAS ");
        System.out.println("===============================");
        System.out.println("1. Registrar horas trabajadas");
        System.out.println("2. Ver resumen de horas y pago");
        System.out.println("3. Salir");
        System.out.print("Seleccione una opción: ");
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
            case 1 -> registrarHoras();
            case 2 -> mostrarResumen();
            case 3 -> {
                System.out.println("Chauuu");
                return true;
            }
            default -> System.out.println("Opcion invalida. Intente de nuevo.");
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
}

