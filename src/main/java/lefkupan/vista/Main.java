package lefkupan.vista;

import lefkupan.controlador.ControladorLogin;
import lefkupan.controlador.ControladorHoras;
import lefkupan.modelo.Ayudante;

import java.util.Scanner;

public class Main { //clase principal que inicia el sistema
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Bienvenido al sistema Lef Küpan - Registro Rapido"); //CAMBIO: interaccion inicial simple, modular y clara

        System.out.print("Ingrese su matricula: "); //CAMBIO: autenticacion por consola
        String matricula = scanner.nextLine();

        System.out.print("Ingrese su contraseña: ");
        String contrasena = scanner.nextLine();

        Ayudante ayudante = ControladorLogin.autenticar(matricula, contrasena);

        if(ayudante != null) {
            //CAMBIO: flujo modular con controlador y menu
            ControladorHoras controlador = new ControladorHoras(ayudante);
            Menu menu = new Menu(controlador);
            menu.mostrarMenuPrincipal();
        } else {
            System.out.println("Matricula o contraseña incorrecta, intente de nuevo");
        }
        scanner.close();
    }
}