package lefkupan.controlador;

import lefkupan.modelo.usuario.Administrador;
import lefkupan.modelo.usuario.Ayudante;
import lefkupan.persistencia.HistorialTxt;
import lefkupan.persistencia.UsuarioJsonRepositorio;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class ControladorLogin { //encargada de autenticar a los usuarios a partir del archivo usuarios.txt
    private static final String ARCHIVO_USUARIOS = "src/main/resources/usuarios.txt";

    public static Object autenticar(String matricula, String contrasena) { //autentica a un usuario y retorna una instacia de ayudante o administrador
        return UsuarioJsonRepositorio.autenticar(matricula, contrasena);
    }

    private static Scanner obtenerLectorUsuarios() throws IOException {
        Path archivo = Path.of(ARCHIVO_USUARIOS);
        if (!Files.exists(archivo)) {
            throw new FileNotFoundException("Archivo de usuarios no encontrado");
        }
        return new Scanner(Files.newBufferedReader(archivo));
    }
}

