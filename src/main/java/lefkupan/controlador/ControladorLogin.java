package lefkupan.controlador;

import lefkupan.modelo.usuario.Administrador;
import lefkupan.modelo.usuario.Ayudante;
import lefkupan.modelo.HistorialTxt;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class ControladorLogin { //encargada de autenticar a los usuarios a partir del archivo usuarios.txt
    private static final String ARCHIVO_USUARIOS = "src/main/resources/usuarios.txt";

    public static Object autenticar(String matricula, String contrasena) { //autentica a un usuario y retorna una instacia de ayudante o administrador
        try(Scanner scanner = obtenerLectorUsuarios()) {
            while(scanner.hasNextLine()) {
                String linea = scanner.nextLine().trim();
                String[] partes = linea.split(";");

                if(partes.length !=3) continue;

                String matriculaGuardada = partes[0];
                String contrasenaGuardada = partes[1];
                String rolGuardada = partes[2];

                if(matriculaGuardada.equals(matricula) && contrasenaGuardada.equals(contrasena)) {
                    if (rolGuardada.equalsIgnoreCase("ADMINISTRADOR")) {
                        return new Administrador(matricula);
                    } else {
                        Ayudante ayudantia = new Ayudante(matricula, contrasena);
                        HistorialTxt.cargarHistorial(ayudantia);
                        return ayudantia;
                    }
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

