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
                String[] partes = linea.split(",", 3);
                String matricula = partes[0];
                String contrasena = partes[1];

                Ayudante ayudante = new Ayudante(matricula, contrasena);

                if (partes.length == 3) {
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
                                    ayudante.sumarHoras(cantidad);
                                }
                            }
                        }

                        ayudante.agregarAyudantia(ayudantia);
                    }
                }

                ayudantes.add(ayudante);
            }
        } catch (IOException e) {
            System.out.println("No se pudo cargar la información. Asegúrate de tener usuarios registrados.");
        }

        return ayudantes;
    }

    public static void guardarAyudantes(List<Ayudante> ayudantes) {
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(ARCHIVO))) {
            for (Ayudante a : ayudantes) {
                StringBuilder sb = new StringBuilder();
                sb.append(a.getMatricula()).append(",").append(a.getContrasena());

                if (!a.getAyudantias().isEmpty()) {
                    sb.append(",");
                    List<String> ayudantiaStrings = new ArrayList<>();
                    for (Ayudantia ay : a.getAyudantias()) {
                        List<String> registros = new ArrayList<>();
                        for (RegistroHoras rh : ay.getRegistrosHoras()) {
                            registros.add(rh.getFecha().toString() + "-" + rh.getCantidad());
                        }
                        String linea = ay.getNombreRamo() + ":" + String.join("|", registros);
                        ayudantiaStrings.add(linea);
                    }
                    sb.append(String.join(";", ayudantiaStrings));
                }

                escritor.write(sb.toString());
                escritor.newLine();
            }

            System.out.println("Usuarios guardados correctamente.");
        } catch (IOException e) {
            System.out.println("Error al guardar usuarios.txt.");
        }
    }
}
