package lefkupan.modelo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.Path;
import java.io.File;
import static org.junit.jupiter.api.Assertions.*;

public class HistorialTxtTest {

    @Test
    public void guardarYCargarHistorial(@TempDir Path temp) {
        String originalDir = System.getProperty("user.dir");
        System.setProperty("user.dir", temp.toFile().getAbsolutePath());
        try {
            Ayudante a = new Ayudante("999","p");
            HistorialTxt.guardarRegistro(a, "Prog", 2);
            File f = temp.resolve("historial_999.txt").toFile();
            assertTrue(f.exists());

            Ayudante nuevo = new Ayudante("999","p");
            HistorialTxt.cargarHistorial(nuevo);
            assertEquals(2, nuevo.getHorasTrabajadas(), 0.0001);
            assertEquals(1, nuevo.getAyudantias().size());
        } finally {
            System.setProperty("user.dir", originalDir);
        }

    }

    // usamos un directorio temporal o ficticio para asegurar que los archivos se guardan, recargarlos cargan las horas del ayudante de forma correcta y la lista de cursos tambien
}