package TPAnual;

import java.util.ArrayList;
import java.util.List;

public class Registro {

    private static List<Usuario> usuariosRegistrados = new ArrayList<>();
    public static void main(String[] args) {
        String usuario = "GonzaloFernandez20";
        String contrasenia = "Hola";
        
        String usuario1 = "Juan";
        String contrasenia1 = "AdiosHola";

        String usuario4 = "Juan";
        String contrasenia4 = "1234 5678";

        String usuario2 = "Esteban";
        String contrasenia2 = "rgkndf";

        //String usuario3 = "Andres";
        //String contrasenia3 = "hola";
        
        registrarUsuario(usuario1, contrasenia1); 
        registrarUsuario(usuario4, contrasenia4);
        registrarUsuario(usuario, contrasenia);
        registrarUsuario(usuario2, contrasenia2); 
        //registrarUsuario(usuario3, contrasenia3);


        listaDeUsuarios(usuariosRegistrados);
    }

    public static void listaDeUsuarios(List<Usuario> lista){
        
        for (Usuario usuario : lista) {
            System.out.println(usuario.nombreDeUsuario());
        } 
    }

    private static void registrarUsuario(String usuario, String contrasenia){
        final Boolean validacionCorrecta = Validador.validarRegistro(contrasenia);

        if (validacionCorrecta) {
            Usuario usuarioNuevo = new Usuario(usuario, contrasenia);
            usuariosRegistrados.add(usuarioNuevo);
            
            System.out.println("\nUsuario creado correctamente.\n");

            System.out.println("Nombre de Usuario: " + usuarioNuevo.nombreDeUsuario() );
            System.out.println("Contrasenia: " + usuarioNuevo.contrasenia());

        }else{
            throw new IllegalAccessError("No se pudo crear el usuario.");
        }
    }

}

class Usuario{
    private String userName;
    private String password;

    public Usuario(String userName, String password){
        if (userName == null || password == null) {
            throw new IllegalArgumentException("Datos incorrectos");
        }
        this.userName = userName;
        this.password = password;
    }

    public String contrasenia(){ return password; }
    public String nombreDeUsuario(){return userName; }
}

