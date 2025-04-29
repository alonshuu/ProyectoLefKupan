package Clases;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static double totalHorasActuales = 0;
    static String matriculaActual = "";
    static ArrayList<String> historialHoras = new ArrayList<>();



    public static void main(String[] args) {
        menu();
    }

    // funciones de login y logica de este mismo, decidi separarlos en dos metodos para respetar las reglas del clean code (metodos que hagan solo 1 cosa ))

    public static boolean logicaLogin(String matricula, String contrasenia){
        try {
            InputStream is = Main.class.getClassLoader().getResourceAsStream("login.txt"); // input stream representa una corriente de datos de entrada, se usa el class loader para poder luego acceder a sus recursos, en estecaso el login.txt
            BufferedReader lector = new BufferedReader(new InputStreamReader(is));
            String linea;
            while ((linea = lector.readLine()) != null ){
                String[] partes = linea.split(",");
                if (partes.length == 2){
                    if (partes[0].equals(matricula) && partes[1].equals(contrasenia)) {
                        lector.close();
                        return true;
                    }
                }
            }
            lector.close();
        } catch (IOException e) { // en este caso el intellij me pedia añadir la execepcion
            System.out.println("Error al leer el archivo de usuarios.");
        }
        return false;
    }

    public static void login(){
        System.out.println("Por favor ingresa el numero de tu matricula");
        String nombre = scanner.nextLine();
        System.out.println("Por favor ingresa tu contraseña");
        String contraseña = scanner.nextLine();

        while(!logicaLogin(nombre,contraseña)){
            System.out.println("Por favor ingresa datos válidos");
            System.out.println("Por favor ingresa el numero de tu matricula");
            nombre = scanner.nextLine();
            System.out.println("Por favor ingresa tu contraseña");
            contraseña = scanner.nextLine();
        }
        matriculaActual = nombre;
        System.out.println("Has iniciado sesión correctamente!");




    }



    // metodos de horas

    public static double verHorasTotales() {
        return totalHorasActuales;
    }

    public static double calcularPago(double valorPorHora){
        return totalHorasActuales * valorPorHora;
    }
    public static void registrarHoras(double horas){
        if (horas > 0){
            totalHorasActuales += horas;
            System.out.println("Horas registradas de forma exitosa");
            guardarRegistro(horas);
        } else {
            System.out.println("No se pueden registrar horas negativas o iguales a 0");
        }
    }

    public static void mostrarResumen(double valorPorHora) {
        System.out.println("==================================");
        System.out.println("       RESUMEN DE HORAS           ");
        System.out.println("==================================");
        System.out.println("Total de horas trabajadas: " + totalHorasActuales);
        System.out.println("Pago total estimado: $" + calcularPago(valorPorHora));

        System.out.println("----------------------------------");

        System.out.println("\n Detalle de tus horas registradas a continuación:");

        for (String registro : historialHoras) {
            String[] partes = registro.split(",");
            if (partes.length == 2){
                System.out.println("- Fecha" + partes[0] + ", Horas: " + partes[1]);
            }
        }
    }







    // metodos de carga d horas y fechas

    public static void guardarRegistro(double horas){
        try {
            String fechaHoy = java.time.LocalDate.now().toString();
            String linea = fechaHoy + "," + horas;

            historialHoras.add(linea);

            String nombreArchivo = "historial_" + matriculaActual + ".txt";
            BufferedWriter escritor = new BufferedWriter(new FileWriter(nombreArchivo,true));
            escritor.write(linea);
            escritor.newLine();
            escritor.close();
        } catch (IOException e) {
            System.out.println("Error al guardar");
        }
    }

    public static void cargarHistorial() {
        try {
            String nombrearchivo = "historial_" + matriculaActual + ".txt";
            BufferedReader lector = new BufferedReader(new FileReader(nombrearchivo));
            String linea;
            while ((linea = lector.readLine()) != null){
                historialHoras.add(linea);

                String[] partes = linea.split(",");
                if (partes.length == 2) {
                    double horas = Double.parseDouble(partes[1]);
                    totalHorasActuales += horas;
                }
            }
            lector.close();
        } catch (FileNotFoundException e) {
            System.out.println("No existe historial para esta matricula");
        } catch (IOException e) {
            System.out.println("Error al cargar el historial");
        }
    }

    // metodos de simplificacion para el menu

    public static void registrarHorasUser() {
        System.out.print("Ingrese las horas trabajadas: ");
        double horas = scanner.nextDouble();
        scanner.nextLine();
        registrarHoras(horas);
    }

    public static void mostrarResumenUser() {
        System.out.print("Ingrese el valor por hora: ");
        double valor = scanner.nextDouble();
        scanner.nextLine();
        mostrarResumen(valor);
    }




    // metodos del menu bastante orgulloso de como salieron

    public static void printMenu(){
        System.out.println("==================================");
        System.out.println("| SISTEMA DE REGISTRO HORAS PAUU |");
        System.out.println("==================================\n");
        System.out.println("Por favor, selecciona una opción:");
        System.out.println("[1] Registrar horas trabajadas");
        System.out.println("[2] Ver resumen de horas y pagos");
        System.out.println("[3] Salir");
        System.out.println("\n===============================");
    }

    public static int leerOpcionMenu() {
        System.out.println("Elige una opcion");
        int opcion = scanner.nextInt();
        scanner.nextLine();
        return opcion;
    }

    public static boolean ejecutarOpcion(int opcion) {
        switch (opcion) {
            case 1:
                registrarHorasUser();
                return false;
            case 2:
                mostrarResumenUser();
                return false;
            case 3:
                System.out.println("Saliendo del programa...");
                System.out.println("....");
                System.out.println("..");
                System.out.println(".");
                return true;
            default:
                System.out.println("Opción no válida, intenta de nuevo.");
                return false;
        }
    }
    public static void menu(){
        login();
        cargarHistorial();
        boolean exit = false;
        while(!exit){
            printMenu();
            int opcion = leerOpcionMenu();
            exit = ejecutarOpcion(opcion);
        }
    }


}