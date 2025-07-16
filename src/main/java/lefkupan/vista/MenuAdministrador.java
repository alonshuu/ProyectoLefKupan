package lefkupan.vista;

import lefkupan.modelo.usuario.Administrador;
import lefkupan.modelo.usuario.RolUsuario;

import java.util.Scanner;

public class MenuAdministrador { //menu exclusivo para administradores del sistema
    private final Administrador administrador;
    private final Scanner scanner;

    public MenuAdministrador(Administrador administrador) {
        this.administrador = administrador;
        this.scanner = new Scanner(System.in);
    }

    public void mostrarMenuAdministrador() {
        int opcion;
        do {
            System.out.println("\n ---- Menu Administrador ----");
            System.out.println("1. Registrar nuevo usuario");
            System.out.println("2. Ver historial de todos los ayudantes");
            System.out.println("0. Salir");
            System.out.print("Selecciona una opcion: ");

            opcion = solicitarNumeroEntero();

            switch (opcion) {
                case 1: registrarUsuario();  break; //CAMBIO: permite crear usuarios
                case 2: administrador.verHistorialTodosLosAyudantes();  break; //CAMBIO: lee todos los archivos de datos
                case 0: System.out.println("Volviendo al login...");  break;
                default: System.out.println("Opcion invalida");  break;
            }
        } while (opcion !=0);
    }

    private void registrarUsuario() {
        System.out.println("Ingrese matricula: ");
        String matricula = scanner.nextLine();

        System.out.println("Ingrese contraseña: ");
        String contrasena = scanner.nextLine();

        System.out.println("Seleccione rol: ");
        System.out.println("1. Ayudante");
        System.out.println("2. Administrador");
        int rolOpcion = solicitarNumeroEntero();

        RolUsuario rol = (rolOpcion == 2) ? RolUsuario.ADMINISTRADOR : RolUsuario.AYUDANTE;

        administrador.registrarNuevoUsuario(matricula, contrasena, rol);
    }

    private int solicitarNumeroEntero() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un numero valido");
            }
        }
    }
}