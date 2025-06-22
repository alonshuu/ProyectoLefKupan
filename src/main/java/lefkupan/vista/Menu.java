package lefkupan.vista;

import lefkupan.controlador.ControladorHoras;
import lefkupan.modelo.Ayudante;

import java.util.Scanner;

public class Menu {
    private final Scanner scanner = new Scanner(System.in);
    private final Ayudante ayudante;
    private final ControladorHoras controladorHoras;

    public Menu(Ayudante ayudante, ControladorHoras controladorHoras){
        this.ayudante = ayudante;
        this.controladorHoras = controladorHoras;
    }
    public void iniciar(){
        int opcion = -1;
        do {
            System.out.println("--------------------");
            System.out.println("        MENÚ        ");
            System.out.println("--------------------");
        }
    }
}
