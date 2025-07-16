package lefkupan.modelo;

import lefkupan.modelo.usuario.Ayudante;
import org.junit.jupiter.api.Test;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;
import static org.junit.jupiter.api.Assertions.*;

public class HistorialTxtTest {

    @Test
    public void guardarYCargarHistorial() throws Exception {
        Path filePath = Paths.get("historial_999.txt");

        try {
            Ayudante a = new Ayudante("999", "p");
            HistorialTxt.guardarRegistro(a, "prog", 2);
            File f = filePath.toFile();

            assertTrue(f.exists());
            assertTrue(Files.exists(filePath));

            Ayudante nuevo = new Ayudante("999", "p");
            HistorialTxt.cargarHistorial(nuevo);
            assertEquals(2, nuevo.getHorasTrabajadas(), 0.0001);
            assertEquals(1, nuevo.getAyudantias().size());

        } finally {
            Files.deleteIfExists(filePath);
        }
    }
}
