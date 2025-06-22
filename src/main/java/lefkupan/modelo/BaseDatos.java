package lefkupan.modelo;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BaseDatos {
    private static final String ARCHIVO = "src/main/resources/usuarios.txt";

    public static List<Ayudante> cargarAyudantes() {
        List<Ayudante> ayudantes = new ArrayList<>();

        try (BufferedReader lector = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                String matricula = partes[0];
                String contrasena = partes[1];

                Ayudante ayudante = new Ayudante(matricula, contrasena);
                if (partes.length == 3){
                    String[] ayudantias = partes[2].split(";");
                    for (String a : ayudantias) {
                        String[] partesAyudantia = a.split(":");
                        String ramo = partesAyudantia[0];
                        Ayudantia ayudantia = new Ayudantia(ramo);

                        if (partesAyudantia.length == 2) {
                            String[] registros = partesAyudantia[1].split("\\|");
                            for (String r : registros) {
                                String[] datos = r.split("-");
                                if (datos.length == 2) {
                                    LocalDate fecha = LocalDate.parse(datos[0]);
                                    double cantidad = Double.parseDouble(datos[1]);
                                    RegistroHoras rh = new RegistroHoras(fecha, cantidad);
                                    ayudantia.getRegistrosHoras().add(rh);
                                    ayudante.registrarHoras(ramo, cantidad);
                                }
                            }
                        }
                        ayudante.agregarAyudantia(ayudantia);
                    }
                }
                ayudante.add(ayudante);
            }
        }
    }
}
