package Clases;

import java.io.*;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);




    public static void main(String[] args) {

    }
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
        } catch (IOException e) {
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
        System.out.println("Has iniciado sesión correctamente!");




    }









}