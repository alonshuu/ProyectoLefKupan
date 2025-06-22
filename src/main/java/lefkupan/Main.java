package lefkupan;

import lefkupan.controlador.ControladorHoras;
import lefkupan.controlador.ControladorLogin;
import lefkupan.modelo.Ayudante;
import lefkupan.vista.Menu;

import java.util.Scanner;

public class Main{
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        ControladorLogin controladorLogin = new ControladorLogin();

        System.out.println("Bienvenido al sistema de registro de horas de ayudantes.");

        Ayudante usuario = null;
        while (usuario == null) {
            System.out.print("Ingrese su matrícula: ");
            String matricula = scanner.nextLine();
            System.out.print("Ingrese su contraseña: ");
            String contrasena = scanner.nextLine();

            usuario = controladorLogin.autenticar(matricula, contrasena);

            if (usuario == null) {
                System.out.println("Usuario incorrecto. Intente nuevamente.\n");
            }
        }
        System.out.println("Sesión iniciada.\n");
        ControladorHoras controladorHoras = new ControladorHoras(controladorLogin.getListaAyudantes());
        Menu menu = new Menu(usuario, controladorHoras);
        menu.iniciar();
    }
}