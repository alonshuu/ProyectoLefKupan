package lefkupan.controlador;

import lefkupan.modelo.Ayudante;
import lefkupan.modelo.BaseDatos;
import java.util.List;

public class ControladorLogin {
    private List<Ayudante> listaAyudantes;
    public ControladorLogin() {
        this.listaAyudantes = BaseDatos.cargarAyudantes();
    }
    public Ayudante autenticar(String matricula, String contrasena){

    }
}