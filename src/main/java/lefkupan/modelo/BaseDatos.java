package lefkupan.modelo;

public interface BaseDatos {
    void guardarAyudante(Ayudante ayudante);
    Ayudante obtenerAyudante(String matricula);
}
