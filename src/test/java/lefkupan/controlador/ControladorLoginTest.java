package lefkupan.controlador;

import lefkupan.modelo.Ayudante;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ControladorLoginTest {

    @Test
    public void autenticarCredencialesValidas() {
        Ayudante a = ControladorLogin.autenticar("21645082523", "1234");
        assertNotNull(a);
        assertEquals("21645082523", a.getMatricula());
        assertEquals("1234", a.getContrasena());
    }

    // chequea que las credenciales sean validas en el archivo usuarios.txt

    @Test
    public void autenticarCredencialesInvalidasRetornaNull() {
        Ayudante a = ControladorLogin.autenticar("21247650122", "alonso123");
        assertNull(a);
    } // asegura autenticacion falla cuando no se encuentran en el archivo o formateados de forma correcta en este
}