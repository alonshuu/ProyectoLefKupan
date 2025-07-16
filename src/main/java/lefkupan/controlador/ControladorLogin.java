package lefkupan.controlador;

import lefkupan.persistencia.UsuarioJsonRepositorio;

public class ControladorLogin { //encargada de autenticar a los usuarios a partir del archivo usuarios.txt

    public static Object autenticar(String matricula, String contrasena) { //autentica a un usuario y retorna una instacia de ayudante o administrador
        return UsuarioJsonRepositorio.autenticar(matricula, contrasena);
    }

    //metodo obtenerLectorUsuarios ya no se usa ya que se migro a JSON
}

