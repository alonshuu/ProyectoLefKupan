package lefkupan.persistencia;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lefkupan.modelo.dominio.RegistroHoras;
import lefkupan.modelo.dominio.Ayudantia;
import lefkupan.modelo.usuario.Administrador;
import lefkupan.modelo.usuario.Ayudante;
import lefkupan.modelo.dominio.TipoActividad;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;

public class HistorialJson { //encargada de leer y guardar los historiales de horas de los ayudantes desde archivos JSON

    private static final String CARPETA = "data";
    private static final Gson gson = new Gson();

    public static void cargarHistorial(Ayudante ayudante) { //carga el historial desde data/{matricula}.json y lo asigna al ayudante
        Path archivo = Paths.get(CARPETA, ayudante.getMatricula() + ".json");

        if (!Files.exists(archivo)) return;

        try (Reader reader = Files.newBufferedReader(archivo)) {
            Type mapType = new TypeToken<Map<String, List<RegistroSerializable>>>() {
            }.getType();
            Map<String, List<RegistroSerializable>> data = gson.fromJson(reader, mapType);

            for (Map.Entry<String, List<RegistroSerializable>> entry : data.entrySet()) {
                String ramo = entry.getKey();
                Ayudantia ayudantia = new Ayudantia(ramo);
                for (RegistroSerializable reg : entry.getValue()) {
                    ayudantia.agregarHoras(reg.fecha, reg.cantidad, reg.tipoActividad);
                }
                ayudante.agregarAyudantia(ayudantia);
            }

        } catch (IOException e) {
            System.out.println("Error al leer historial JSON: " + e.getMessage());
        }
    }

    public static void guardarHistorial(Ayudante ayudante) {
        Map<String, List<RegistroSerializable>> data = new HashMap<>();

        for (Ayudantia ayudantia : ayudante.getAyudantias()) {
            List<RegistroSerializable> registros = new ArrayList<>();
            for (RegistroHoras r : ayudantia.getRegistrosHoras()) {
                registros.add(new RegistroSerializable(r.getFecha(), r.getCantidad(), r.getTipoActividad()));
            }
            data.put(ayudantia.getNombreRamo(), registros);
        }

        Path archivo = Paths.get("data", ayudante.getMatricula() + ".json");

        try {
            Files.createDirectories(archivo.getParent());
            try (Writer writer = Files.newBufferedWriter(archivo, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
                gson.toJson(data, writer);
                System.out.println("Historial guardado con exito");
            }
        } catch (IOException e) {
            System.err.println("Error al guardar historial JSON: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static class RegistroSerializable {
        LocalDate fecha;
        double cantidad;
        TipoActividad tipoActividad;

        RegistroSerializable(LocalDate fecha, double cantidad, TipoActividad tipoActividad) {
            this.fecha = fecha;
            this.cantidad = cantidad;
            this.tipoActividad = tipoActividad;
        }
    }
}
