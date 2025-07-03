package lefkupan.vista;

import lefkupan.controlador.ControladorHoras;
import lefkupan.controlador.ControladorLogin;
import lefkupan.modelo.Ayudante;
import lefkupan.modelo.Ayudantia;
import lefkupan.modelo.HistorialTxt;
import lefkupan.modelo.RegistroHoras;

import java.util.List;
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
        System.out.println("4. Eliminar un registro especifico: ");
        System.out.println("5. Ver historial de pagos");
        System.out.println("6. Salir");
        System.out.print("Selecciona una opcion: ");
    }

    private int leerOpcion() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
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

            case 5:
                verHistorialPagos();
                break;

            case 6: {
                System.out.println("Adios");
                return true;
            }
            default:
                System.out.println("Opcion invalida. Intente de nuevo");
        }
        return false;
    }

    private void registrarHoras() {
        String ramo = solicitarTexto("Ingrese el nombre del ramo: ");
        double horas = solicitarNumero("Ingrese la cantidad de horas: ");
        controladorHoras.registrarHoras(ramo, horas);
    }

    private void mostrarResumen() {
        double valor = solicitarNumero("Ingrese el valor por hora: ");
        controladorHoras.mostrarResumen(valor);
    }

    private void eliminarAyudantia() {
        String ramo = solicitarTexto("Ingrese el nombre del ramo: ");
        boolean eliminada = ayudanteActual.eliminarAyudantia(ramo);

        if (eliminada) {
            HistorialTxt.eliminarAyudantiaDelArchivo(ayudanteActual, ramo);
            ayudanteActual.recalcularHorasTotales();
            System.out.println("Ayudantia eliminada correctamente");
        } else {
            System.out.println("No se encontro la ayudantia");
        }
    }

    private void eliminarRegistroEspecifico() {
        String ramo = solicitarTexto("Ingrese el nombre del ramo: ");
        Ayudantia ayudantia = buscarAyudantia(ramo);
        if (ayudantia == null || ayudantia.getRegistrosHoras().isEmpty()) {
            System.out.println("No hay registros");
            return;
        }

        mostrarRegistros(ayudantia);
        int index = solicitarIndice(ayudantia.getRegistrosHoras().size());
        if (index == -1) return;

        RegistroHoras eliminado = ayudantia.getRegistrosHoras().remove(index);
        ayudanteActual.recalcularHorasTotales();
        HistorialTxt.eliminarRegistroDelArchivo(ayudanteActual, ramo, eliminado);
        System.out.println("Registro eliminado correctamente");
    }

    private void verHistorialPagos() {
        double valor = solicitarNumero("Ingrese el valor por hora: ");
        HistorialTxt.mostrarHistorialPagos(ayudanteActual, valor);
    }

    private String solicitarTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }

    private double solicitarNumero(String mensaje) {
        System.out.print(mensaje);
        try {
            return Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Entrada invalida");
            return 0;
        }
    }

    private Ayudantia buscarAyudantia(String ramo) {
        return ayudanteActual.getAyudantias().stream()
                .filter(a -> a.getNombreRamo().equalsIgnoreCase(ramo))
                .findFirst()
                .orElse(null);
    }

    private void mostrarRegistros(Ayudantia ayudantia) {
        System.out.println("Registros disponibles:");
        List<RegistroHoras> registros = ayudantia.getRegistrosHoras();
        for (int i = 0; i < registros.size(); i++) {
            System.out.println("[" + (i + 1) + "] " + registros.get(i));
        }
    }

    private int solicitarIndice(int size) {
        System.out.print("Seleccione un registro: ");
        try {
            int seleccion = Integer.parseInt(scanner.nextLine()) - 1;
            if (seleccion >= 0 && seleccion < size) return seleccion;
        } catch (NumberFormatException ignored) {}
        System.out.println("Indice invalido");
        return -1;
    }
}

