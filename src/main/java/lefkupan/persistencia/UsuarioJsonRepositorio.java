package lefkupan.persistencia;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lefkupan.modelo.usuario.RolUsuario;
import lefkupan.modelo.usuario.Ayudante;
import lefkupan.modelo.usuario.Administrador;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.*;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

public class UsuarioJsonRepositorio { //repositorio para cargar y guardar usuarios desde JSON

    private static final String ARCHIVO_USUARIOS = "src/main/resources/usuarios.json";
    private static final Gson gson = new Gson();

    public static List<Map<String, String>> cargarUsuarios() {
        try (Reader reader = Files.newBufferedReader(Paths.get(ARCHIVO_USUARIOS))) {
            Type listType = new TypeToken<List<Map<String, String>>>() {}.getType();
            return gson.fromJson(reader, listType);
        } catch (IOException e) {
            System.err.println("Error al leer usuarios.json: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public static void guardarUsuario(String matricula, String contrasena, RolUsuario rol) {
        List<Map<String, String>> usuarios = cargarUsuarios();

        for (Map<String, String> u : usuarios) { // verifica duplicados
            if (u.get("matricula").equals(matricula)) {
                System.out.println("El usuario ya existe");
                return;
            }
        }

        Map<String, String> nuevo = new HashMap<>(); //agrega el nuevo usuario
        nuevo.put("matricula", matricula);
        nuevo.put("contrasena", contrasena);
        nuevo.put("rol", rol.name());

        usuarios.add(nuevo);

        try (Writer writer = Files.newBufferedWriter(Paths.get(ARCHIVO_USUARIOS))) {
            gson.toJson(usuarios, writer);
            System.out.println("Usuario registrado con exito");
        } catch (IOException e) {
            System.err.println("Error al guardar usuario: " + e.getMessage());
        }
    }

    public static Object autenticar(String matricula, String contrasena) { //autentica un usuario desde usuario.json
        List<Map<String, String>> usuarios = cargarUsuarios();

        for (Map<String, String> u : usuarios) {
            if (u.get("matricula").equals(matricula) && u.get("contrasena").equals(contrasena)) {
                String rol = u.get("rol");
                if ("ADMINISTRADOR".equalsIgnoreCase(rol)) {
                    return new Administrador(matricula);
                } else {
                    return new Ayudante(matricula, contrasena);
                }
            }
        }
        return null;
    }
}