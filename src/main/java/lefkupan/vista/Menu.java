package lefkupan.vista;

import lefkupan.controlador.ControladorHoras;
import lefkupan.modelo.Ayudantia;
import lefkupan.modelo.HistorialTxt;
import lefkupan.modelo.RegistroHoras;
import lefkupan.modelo.TipoActividad;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Menu { //clase que gestiona el menu y la interaccion con el usuario desde consola
    private final Scanner scanner;
    private final ControladorHoras controlador;

    public Menu(ControladorHoras controlador) {
        this.controlador = controlador;
        this.scanner = new Scanner(System.in);
    }

    public void mostrarMenuPrincipal() { //muestra el menu principal de opciones
        int opcion;
        do {
            System.out.println("\n----- Menu Principal -----");
            System.out.println("1. Registrar horas");
            System.out.println("2. Ver resumen de ayudantias");
            System.out.println("3. Eliminar ayudantia");
            System.out.println("4. Eliminar registro especifico");
            System.out.println("5. Ver historial y pago estimado");
            System.out.println("0. Salir");
            System.out.println("Selecciona una opcion");

            opcion = solicitarNumeroEntero();

            switch (opcion) {
                case 1: registrarHoras(); break; //CAMBIO: menu modularizado
                case 2: controlador.mostrarDetalleAyudantias(); controlador.mostrarEncabezadoResumen(); break;
                case 3: eliminarAyudantia(); break;
                case 4: eliminarRegistroEspecifico(); break;
                case 5: HistorialTxt.mostrarHistorialPagos(controlador.getAyudante(), solicitarValorHora()); break;
                case 0: System.out.println("Hasta luego");
                default: System.out.println("Opcion invalida");
            }
        } while (opcion !=0);
    }

    private void registrarHoras() {
        System.out.println("\n --- Registro de horas --- ");
        String ramo = solicitarTexto("Nombre del ramo: ");
        LocalDate fecha = solicitarFecha();
        double horas = solicitarNumero("Cantidad de horas: ");
        TipoActividad tipo = solicitarTipoActividad(); //CAMBIO: uso de enum en vez de texto libre

        controlador.registrarHoras(ramo, fecha, horas, tipo);
        System.out.println("Registro exitoso");
    }

    private void eliminarAyudantia() {
        String ramo = solicitarTexto("Nombre del ramo a eliminar: ");
        boolean ok = controlador.eliminarAyudantia(ramo);
        if (ok) {
            System.out.println("Ayudantia eliminada");
        } else {
            System.out.println("No se encontro la ayudantia");
        }
    }

    private void eliminarRegistroEspecifico() {
        List<Ayudantia> ayudantias = controlador.getAyudante().getAyudantias();
        if (ayudantias.isEmpty()) {
            System.out.println("No hay ayudantias disponibles");
            return;
        }

        System.out.println("Selecciona una ayudantia:");
        for (int i = 0; i < ayudantias.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, ayudantias.get(i).getNombreRamo());
        }

        int indice = solicitarIndice(ayudantias.size());
        Ayudantia seleccionada = ayudantias.get(indice - 1);

        List<RegistroHoras> registros = seleccionada.getRegistrosHoras();
        if (registros.isEmpty()) {
            System.out.println("No hay registros en esta ayudantia.");
            return;
        }

        System.out.println("Selecciona un registro para eliminar: ");
        for (int i = 0; i < registros.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, registros.get(i));
        }

        int indiceRegistro = solicitarIndice(registros.size());
        RegistroHoras registro = registros.get(indiceRegistro - 1);

        boolean ok = controlador.eliminarRegistroEspecifico(seleccionada.getNombreRamo(), registro);
        if (ok) {
            System.out.println("Registro eliminado");
        } else {
            System.out.println("No se pudo eliminar el registro");
        }
    }

    private String solicitarTexto(String mensaje) {
        System.out.print(mensaje + " ");
        return scanner.nextLine();
    }

    private double solicitarNumero(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje + " ");
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un numero valido");
            }
        }
    }

    private LocalDate solicitarFecha() {
        while (true) {
            try {
                System.out.print("Fecha (YYYY-MM-DD): ");
                return LocalDate.parse(scanner.nextLine());
            } catch (DateTimeParseException e) {
                System.out.println("Formato de fecha invalido");
            }
        }
    }

    private TipoActividad solicitarTipoActividad() {
        System.out.println("Selecciona tipo de actividad: ");
        TipoActividad[] tipos = TipoActividad.values();
        for (int i = 0; i < tipos.length; i++) {
            System.out.printf("%d. %s\n", i + 1, tipos[i]);
        }
        int opcion = solicitarIndice(tipos.length);
        return tipos[opcion - 1]; // CAMBIO: retorno seguro desde enum
    }

    private int solicitarIndice(int max) {
        int valor;
        do {
            System.out.print("Selecciona opcion (1-" + max + "): ");
            valor = solicitarNumeroEntero();
        } while (valor < 1 || valor > max);
        return valor;
    }

    private int solicitarNumeroEntero() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un numero entero valido");
            }
        }
    }

    private double solicitarValorHora() {
        return solicitarNumero("Ingrese valor por hora: ");
    }
}

