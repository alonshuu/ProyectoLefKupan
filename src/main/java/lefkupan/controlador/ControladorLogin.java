package lefkupan.controlador;

import lefkupan.modelo.Ayudante;
import lefkupan.modelo.HistorialTxt;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class ControladorLogin { //encargada de autenticar a los usuarios a partir del archivo usuarios.txt
    private static final String ARCHIVO_USUARIOS = "src/main/resources/usuarios.txt";

    public static Ayudante autenticar(String matricula, String contrasena) {
        try(Scanner scanner = obtenerLectorUsuarios()) {
            while(scanner.hasNextLine()) {
                String linea = scanner.nextLine().trim();
                String[] partes = linea.split(";");

                if(partes.length !=2) continue;

                String matriculaGuardada = partes[0];
                String contrasenaGuardada = partes[1];

                if(matriculaGuardada.equals(matricula) && contrasenaGuardada.equals(contrasena)) {
                    Ayudante a = new Ayudante(matricula, contrasena);
                    HistorialTxt.cargarHistorial(a);
                    return a;
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo" + e.getMessage());
        }
        return null;
    }

    private static Scanner obtenerLectorUsuarios() throws IOException {
        Path archivo = Path.of(ARCHIVO_USUARIOS);
        if (!Files.exists(archivo)) {
            throw new FileNotFoundException("Archivo de usuarios no encontrado");
        }
        return new Scanner(Files.newBufferedReader(archivo));
    }
}

