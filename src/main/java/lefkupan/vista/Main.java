package lefkupan.vista;

public class Main { //clase principal que inicia el sistema.
    public static void main(String[] args) {
        System.out.println("Bienvenido al sistema Lef Küpan - Registro Rapido"); //CAMBIO: interaccion inicial simple, modular y clara.

        Menu menu = new Menu(); //se instancia el menu que maneja la logica de la vista por consola.

        menu.mostrarMenuPrincipal(); //se llama al metodo mostrarMenuPrincipal() que gestiona el flujo completo del programa.
    }
}