package lefkupan.vista;

import lefkupan.controlador.ControladorLogin;
import lefkupan.controlador.ControladorHoras;
import lefkupan.modelo.usuario.Administrador;
import lefkupan.modelo.usuario.Ayudante;

import java.util.Scanner;

public class Main { //clase principal que inicia el sistema
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Bienvenido al sistema Lef Küpan - Registro Rapido"); //CAMBIO: interaccion inicial simple, modular y clara

        System.out.print("Ingrese su matricula: "); //CAMBIO: autenticacion por consola
        String matricula = scanner.nextLine();

        System.out.print("Ingrese su contraseña: ");
        String contrasena = scanner.nextLine();

        //CAMBIO: el login puede retornar un Ayudante o un Administrador
        Object usuario = ControladorLogin.autenticar(matricula, contrasena);

        if (usuario instanceof Ayudante ayudante) { //determina el tipo de usuario usando instanceof
            //CAMBIO: si es ayudante, se carga el menu de registro de horas
            ControladorHoras controlador = new ControladorHoras(ayudante);
            Menu menu = new Menu(controlador);
            menu.mostrarMenuPrincipal();

        } else if (usuario instanceof Administrador administrador) {
            //CAMBIO: si es administrador, se carga el menu de gestion
            MenuAdministrador menuAdministrador = new MenuAdministrador(administrador);
            menuAdministrador.mostrarMenuAdministrador();

        } else {
            System.out.println("Matricula o contraseña incorrecta, intente de nuevo");
        }

        scanner.close();
    }
}