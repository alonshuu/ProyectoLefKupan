package lefkupan.vista;

import lefkupan.vista.gui.AppGUI;

public class Main { //clase principal que inicia el sistema.
    public static void main(String[] args) {
        if (args.length > 0 && args[0].equalsIgnoreCase("cli")) {
            System.out.println("Bienvenido al sistema Lef Küpan - Registro Rapido");
            Menu menu = new Menu();
            menu.mostrarMenuPrincipal();
        } else {
            AppGUI.launch();
        }
    }
}
