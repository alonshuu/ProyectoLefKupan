package lefkupan.modelo;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class RegistroHorasTest {

    @Test
    public void constructorConHorasInvalidasLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> new RegistroHoras(LocalDate.now(), 0));
    } // test que al crear un registro de horas con cero horas, lanza una exepccion

    @Test
    public void toStringFormatoCorrecto() {
        LocalDate date = LocalDate.of(2024,1,1);
        RegistroHoras r = new RegistroHoras(date, 2.5);
        assertEquals("2024-01-01: 2.5 hrs", r.toString());
    } // verifica el metodo to string y que los outputs esten en los formatos esperados
}